package it.polimi.ingsw.View.GUI;

import it.polimi.ingsw.Utils.NetMessages.BaseServerMessage;
import it.polimi.ingsw.Utils.NetMessages.BaseUserMessage;
import it.polimi.ingsw.Utils.NetMessages.SetupServerMessage;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.input.*;
import javafx.scene.layout.VBox;

import java.util.Observable;

/**
 * Class GUIControllerInitialPhase is the controller class for the first scenes in GUI, used for initialization
 * (where ip, number of players, mode and nicknames are requested).
 *
 * @see java.util.Observable
 * */
public class GUIControllerInitialPhase extends Observable {

    String ipAddress;

    @FXML private BorderPane outsidePane = new BorderPane();
    @FXML private TextField ipTextField = new TextField();
    @FXML private VBox vBox = new VBox();
    @FXML private Label initialSceneLabel = new Label();

    private Button twoPlayers = new Button("2");
    private Button threePlayers = new Button("3");
    private Button simpleMode = new Button("Simple");
    private Button expertMode = new Button("Expert");
    private TextField nicknameTextField = new TextField();
    private Label errorLabel = new Label();

    /**
     * Method getIPAddress shows a TextField to get from user input the ip of the server to which
     * the client has to connect.
     *
     * @param ke of type KeyEvent - key event that detects when enter key gets pressed.
     * */
    public void getIPAddress(KeyEvent ke){
        if(ke.getCode().equals(KeyCode.ENTER)){
            ipAddress = ipTextField.getText();
            vBox.getChildren().remove(ipTextField);
            setChanged();
            notifyObservers(ipAddress);
        }
    }

    /**
     * Method displayWelcome displays a welcome message to the player who just connected.
     *
     * @param welcome of type SetupServerMessage - welcome message.
     * */
    public void displayWelcome(SetupServerMessage welcome){
        initialSceneLabel.setText(welcome.getSetupServerMessage());
    }

    /**
     * Method requestPlayerNumber shows a label indicating that the player has to select the desired number
     * of players (either two or three) of the match they are initiating, by clicking one of the two buttons
     * indicating the numbers 2 and 3.
     *
     * @param message of type SetupServerMessage - setup message requesting number of players.
     * */
    public void requestPlayerNumber(SetupServerMessage message) {
        initialSceneLabel.setText(message.getSetupServerMessage());
        twoPlayers.setOnAction(actionEvent -> {
            makeNumberOfPlayersMessage(2);
            vBox.getChildren().removeAll(twoPlayers, threePlayers);
        });
        threePlayers.setOnAction(actionEvent -> {
            makeNumberOfPlayersMessage(3);
            vBox.getChildren().removeAll(twoPlayers, threePlayers);
        });
        vBox.getChildren().addAll(twoPlayers, threePlayers);
    }

    /**
     * Method requestPlayerNumber shows a label indicating that the player has to select the desired number
     * of players (either two or three) of the match they are initiating, by clicking one of the two buttons
     * indicating the numbers 2 and 3.
     * */
    public void requestGameMode() {
        initialSceneLabel.setText("Choose the gamemode:");
        simpleMode.setOnAction(actionEvent -> {
            makeModeMessage("SIMPLE");
            vBox.getChildren().removeAll(simpleMode, expertMode);
        });
        expertMode.setOnAction(actionEvent -> {
            makeModeMessage("EXPERT");
            vBox.getChildren().removeAll(simpleMode, expertMode);
        });
        vBox.getChildren().addAll(simpleMode, expertMode);
    }

    /**
     * Method requestNickname shows a label asking the player for their desired nickname.
     * The player writes the nickname in a TextField, and it is sent to the server when enter key gets pressed.
     *
     * @param message of type SetupServerMessage - setup message requesting the nickname.
     * */
    public void requestNickname(SetupServerMessage message) {
        vBox.getChildren().add(nicknameTextField);
        initialSceneLabel.setText(message.getSetupServerMessage());
        nicknameTextField.setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode().equals(KeyCode.ENTER)){
                makeNicknameMessage(nicknameTextField.getText());
                vBox.getChildren().remove(nicknameTextField);
            }
        });
    }

    /**
     * Method displayDuplicatedNickname displays an error when a player tries to choose
     * a nickname that was already chosen by another player.
     *
     * @param message of type BaseServerMessage - server message indicating error.
     * */
    public void displayDuplicatedNickname(BaseServerMessage message) {
        errorLabel.setId("errorLabel");
        errorLabel.setText(message.getMessage());
        vBox.getChildren().add(errorLabel);
    }

    /**
     * Method makeNumberOfPlayersMessage sets the number of players of the match to the number
     * that was passed as parameter.
     *
     * @param number of type int - number of players.
     * */
    private void makeNumberOfPlayersMessage(int number){
        BaseUserMessage playerNumber = new BaseUserMessage();
        playerNumber.setNumberOfPlayers(number);
        setChanged();
        notifyObservers(playerNumber);
    }

    /**
     * Method makeModeMessage sets the mode of the match to the one that was passed as parameter.
     *
     * @param mode of type String - game mode.
     * */
    private void makeModeMessage(String mode){
        BaseUserMessage gameMode = new BaseUserMessage();
        gameMode.setGameMode(mode);
        setChanged();
        notifyObservers(gameMode);
    }

    /**
     * Method makeNicknameMessage sets the nickname of a player to the one that was passed as parameter.
     *
     * @param nickname of type String - player nickname.
     * */
    private void makeNicknameMessage(String nickname){
        BaseUserMessage nicknameMessage = new BaseUserMessage();
        nicknameMessage.setNickname(nickname);
        setChanged();
        notifyObservers(nicknameMessage);
    }

}
