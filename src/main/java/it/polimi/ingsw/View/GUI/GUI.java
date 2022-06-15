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

public class GUI extends Application implements Observer{

    Stage primaryStage;
    GUIControllerInitialPhase initialController;
    MainGUIController mainController;
    Client client;
    static GameMode mode;
    static String nick;
    ViewUpdateMessage baseView;
    ExpertViewUpdateMessage expertView;

    private class InternalGUI extends View {

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

        @Override
        public void updateGameBoardExpert(ExpertViewUpdateMessage message) {
            if(expertView == null){
                expertView = message;
                updateGameBoard(message.getViewUpdate_base());
                //showing additional components for expert mode
                Platform.runLater(() -> mainController.drawExpertCards(message.getExtractedCharCards()));
            }
            Platform.runLater(() -> mainController.refreshCoins(message.getCoinCounters()));
        }

        @Override
        public void setMode(GameModeMessage gameMode){
            mode = gameMode.getMode();
        }

        @Override
        public void setNickname(NicknameMessage nickname){
            nick = nickname.getNickname();
        }

        @Override
        public void setPlayedCard(PlayedCardMessage message){
            Platform.runLater(() -> mainController.showOpponentplayedCard(message));
        }

        @Override
        public void displayTurnChange(TurnChangeMessage message){
            if(mainController != null) {
                Platform.runLater(() -> mainController.showTurnChange(message));
            }
        }

    }

    private InternalGUI internalGUI;

    public static void main(String[] args) {
        launch(args);
    }

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
        primaryStage.show();
    }

    public void setupMainGameScene(ViewUpdateMessage message) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mainGameScene.fxml"));
        Parent root = loader.load();
        Scene gameScene = new Scene(root);
        gameScene.getStylesheets().add(getClass().getResource("/stylesheetMainScene.css").toExternalForm());
        primaryStage.setScene(gameScene);
        mainController = (MainGUIController)loader.getController();
        mainController.addObserver(client.getNetworkHandler());
        primaryStage.setFullScreen(true);
    }

    public void initClient(String ip){
        client = new Client(ip, 12345);
        client.addObserver(internalGUI);
        initialController.addObserver(client.getNetworkHandler());
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                client.run();
            }
        });
        t.start();
    }

    @Override
    public void update(Observable o, Object arg) {
            initClient((String) arg);
            initialController.deleteObserver(this);
    }

    public static String getNickname(){
        return nick;
    }

    public static GameMode getMode(){
        return mode;
    }

}
