package it.polimi.ingsw.View.GUI;

import it.polimi.ingsw.Client.Client;
import it.polimi.ingsw.Utils.Enums.GameMode;
import it.polimi.ingsw.Utils.NetMessages.*;
import it.polimi.ingsw.View.CLI.View;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

/**
 * Class GUI allows to play using the graphic interface.
 * In here is where the initial scenes and the main game scene are set.
 * It contains a private class InternalGUI with methods that take input regarding nickname,
 * gamemode and number of players, display messages and errors,
 * put in place the different elements of the game.
 *
 * @see javafx.application.Application
 * @see java.util.Observer
 * */
public class GUI extends Application implements Observer{

    Stage primaryStage;
    GUIControllerInitialPhase initialController;
    MainGUIController mainController;
    Client client;
    static GameMode mode;
    static String nick;
    ViewUpdateMessage baseView;
    ExpertViewUpdateMessage expertView;

    /**
     * Private class InternalGUI contains methods that take user input, display messages
     * and set up the elements of the game.
     *
     * @see View
     * */
    private class InternalGUI extends View {

        /**
         * Method displaySetupMessage is used to show server setup messages in GUI.
         *
         * @param message of type SetupServerMessage - server setup message.
         * */
        @Override
        public void displaySetupMessage(SetupServerMessage message) {
            if(message.getSetupServerMessage().equals(CustomMessage.welcomeMessage)){
                Platform.runLater(() -> initialController.displayWelcome(message));
            }
            else if(message.getSetupServerMessage().equals(CustomMessage.requestNumberOfPlayers)){
                Platform.runLater(() -> initialController.requestPlayerNumber(message));
            }
            else if(message.getSetupServerMessage().equals(CustomMessage.requestGameMode)){
                Platform.runLater(() -> initialController.requestGameMode());
            }
            else if(message.getSetupServerMessage().equals(CustomMessage.askNickname)){
                Platform.runLater(() -> initialController.requestNickname(message));
            }
        }

        /**
         * Method displayError is used to show an error message in GUI, coming from the server,
         * when a player tries to use a nickname that has already been chosen by another player.
         *
         * @param message of type BaseServerMessage - server error message.
         * */
        @Override
        public void displayError(BaseServerMessage message){
            if(message.getMessage().equals(CustomMessage.duplicatedNickname)){
                Platform.runLater(() -> initialController.displayDuplicatedNickname(message));
            }
            else {
                if(mainController != null)
                    Platform.runLater(() -> mainController.showError(message));
            }
        }

        /**
         * Method updateGameBoard refreshes the main game scene showing updates in board after each action
         * performed by a player.
         * It is called when playing in either simple or expert mode.
         *
         * @param message of type ViewUpdateMessage - message containing info on updated board.
         * */
        @Override
        public void updateGameBoard(ViewUpdateMessage message){
            if(baseView == null){
                baseView = message;
                Platform.runLater(() -> {
                    try {
                        setupMainGameScene(message);
                        Platform.runLater(() -> mainController.drawClouds(message.getClouds()));
                        Platform.runLater(() -> mainController.drawSchoolBoards(message.getSchoolboards()));
                        Platform.runLater(() -> mainController.drawDeck(message.getDecks()));
                        Platform.runLater(() -> mainController.drawIslands(message.getIslands(), message.getMnPosition()));
                        Platform.runLater(() -> mainController.refreshSchoolBoards(message.getSchoolboards()));
                        Platform.runLater(() -> mainController.setMyNickLabel(nick));
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                });
            }
            else {
                if(message.getIslands().size() < baseView.getIslands().size())
                    Platform.runLater(() -> mainController.drawIslands(message.getIslands(), message.getMnPosition()));
                else
                    Platform.runLater(() -> mainController.refreshIslands(message.getIslands(), message.getMnPosition()));

                Platform.runLater(() -> mainController.refreshSchoolBoards(message.getSchoolboards()));
                Platform.runLater(() -> mainController.studentsOnClouds(message.getClouds()));

                for(String deck : message.getDecks()){
                    if(deck.split(" ")[0].equals(nick))
                        Platform.runLater(() -> mainController.refreshDeck(deck));
                }

                baseView = message;
            }
        }

        /**
         * Method updateGameBoard refreshes the main game scene showing updates in board after each action
         * performed by a player, when playing in expert mode. It is called after updateGameBoard() to show
         * additional updates regarding elements that are not present in simple mode.
         *
         * @param message of type ExpertViewUpdateMessage - message containing info on updated elements
         *                that only belong to expert mode.
         * */
        @Override
        public void updateGameBoardExpert(ExpertViewUpdateMessage message) {
            if(expertView == null){
                expertView = message;
                updateGameBoard(message.getViewUpdate_base());
                //showing additional components for expert mode
                Platform.runLater(() -> mainController.drawExpertCards(message.getExtractedCharCards()));
            }
            else {
                Platform.runLater(() -> mainController.refreshCoins(message.getCoinCounters()));
                Platform.runLater(() -> mainController.refreshStudentsOnCard(message.getExtractedCharCards()));
                Platform.runLater(() -> mainController.refreshCardDescriptions(message.getExtractedCharCards()));
                updateGameBoard(message.getViewUpdate_base());
                Platform.runLater(() -> mainController.addNoEntryTile(message.getIslandsNETiles()));
                expertView = message;
            }
        }

        /**
         * Method setMode is used to set the mode in which a match is going to be played (simple or expert).
         *
         * @param gameMode of type GameModeMessage - message that requests the game mode.
         * */
        @Override
        public void setMode(GameModeMessage gameMode){
            mode = gameMode.getMode();
        }

        /**
         * Method setNickname is used to set a player's nickname when passed as input.
         *
         * @param nickname of type NicknameMessage - player's nickname.
         * */
        @Override
        public void setNickname(NicknameMessage nickname){
            nick = nickname.getNickname();
        }

        /**
         * Method setPlayedCard calls a method in main GUI controller that informs the player
         * of the assistant card chosen by the opponent(s).
         *
         * @param message of type PlayedCardMessage - message containing information on the assistant card
         *                chosen by another player.
         * */
        @Override
        public void setPlayedCard(PlayedCardMessage message){
            Platform.runLater(() -> mainController.showOpponentplayedCard(message));
        }

        /**
         * Method displayTurnChange informs the player when a turn change occurs, letting them know
         * whose turn it is at that point.
         *
         * @param message of type TurnChangeMessage - message containing information about the player in turn when
         *                a turn change occurs.
         * */
        @Override
        public void displayTurnChange(TurnChangeMessage message){
            if(mainController != null) {
                Platform.runLater(() -> mainController.showTurnChange(message));
            }
        }

        /**
         * Method displayEndOfGame informs the player when the game is over,
         * displaying the nickname of the winner as well.
         *
         * @param message of type EndGameMessage - end of game message containing winner nickname.
         * */
        @Override
        public void displayEndOfGame(EndGameMessage message){
            Platform.runLater(() -> mainController.winningScreen(message.getWinner()));
        }

        /**
         * Method displayNewRoundMessage is called at the beginning of a new turn.
         *
         * @param message of type NewRoundMessage - new round message.
         * */
        @Override
        public void displayNewRoundMessage(NewRoundMessage message){
            Platform.runLater(() -> mainController.clearPlayedCardLabels());
        }

    }

    private InternalGUI internalGUI;

    /**
     * Main method calls the start method that starts the game using the graphic interface.
     * */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Method start sets up the initial scene, shown before the main game scene.
     *
     * @param stage of type Stage - game stage.
     *
     * @see Application#start(Stage)
     * */
    @Override
    public void start(Stage stage){

        primaryStage = stage;
        internalGUI = new InternalGUI();

        try {
            setupStartingScene();
        }catch(IOException e){
            System.err.println("error in launching GUI");
            e.printStackTrace();
            e.getCause();
        }

    }

    /**
     * Method setupStartingScene sets up and shows the inital scenes requesting ip, nickname,
     * number of players and gamemode.
     *
     * @throws IOException when an I/O exception occurs.
     * */
    private void setupStartingScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GameStartScene.fxml"));
        Parent root = loader.load();
        Scene startingScene = new Scene(root);
        startingScene.getStylesheets().add(getClass().getResource("/stylesheetScene1.css").toExternalForm());
        primaryStage.setScene(startingScene);
        primaryStage.getIcons().add(new Image("/icon.png"));
        primaryStage.setTitle("Eriantys");
        initialController = (GUIControllerInitialPhase)loader.getController();
        initialController.addObserver(this);
        primaryStage.setOnCloseRequest(windowEvent ->{
            Platform.exit();
            System.exit(0);
        });
        primaryStage.show();
    }

    /**
     * Method setupMainGameScene sets up the scene that allows the user to interact with
     * the elements of the game.
     *
     * @throws IOException when an I/O exception occurs.
     * */
    public void setupMainGameScene(ViewUpdateMessage message) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mainGameScene.fxml"));
        Parent root = loader.load();
        Scene gameScene = new Scene(root);
        gameScene.getStylesheets().add(getClass().getResource("/stylesheetMainScene.css").toExternalForm());
        primaryStage.setScene(gameScene);
        mainController = (MainGUIController)loader.getController();
        mainController.addObserver(client.getNetworkHandler());
        primaryStage.setMaximized(true);
    }

    /**
     * Method initClient initializes a client and connects it to the server at the given ip.
     *
     * @param ip of type String - server ip.
     * */
    public void initClient(String ip){
        client = new Client(ip, 12345);
        client.addObserver(internalGUI);
        initialController.addObserver(client.getNetworkHandler());
        Thread t = new Thread(() -> client.run());
        t.start();
    }

    /**
     * Method update calls method initClient when ip address is received.
     *
     * @see Observer#update(Observable, Object)
     * */
    @Override
    public void update(Observable o, Object arg) {
            initClient((String) arg);
            initialController.deleteObserver(this);
    }

    /**
     * Getter method getNickname returns a player's nickname.
     *
     * @return String - nickname
     * */
    public static String getNickname(){
        return nick;
    }

    /**
     * Getter method getMode returns chosen gamemode.
     *
     * @return GameMode - gamemode.
     * */
    public static GameMode getMode(){
        return mode;
    }

}
