package it.polimi.ingsw.View.GUI;
import it.polimi.ingsw.Utils.Enums.GameMode;
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

    public void getIPAddress(KeyEvent ke){
        if(ke.getCode().equals(KeyCode.ENTER)){
            ipAddress = ipTextField.getText();
            vBox.getChildren().remove(ipTextField);
            setChanged();
            notifyObservers(ipAddress);
        }
    }

    public void displayWelcome(SetupServerMessage welcome){
        initialSceneLabel.setText(welcome.getSetupServerMessage());
    }

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

    public void displayDuplicatedNickname(BaseServerMessage message) {
        errorLabel.setId("errorLabel");
        errorLabel.setText(message.getMessage());
        vBox.getChildren().add(errorLabel);
    }

    private void makeNumberOfPlayersMessage(int number){
        BaseUserMessage playerNumber = new BaseUserMessage();
        playerNumber.setNumberOfPlayers(number);
        setChanged();
        notifyObservers(playerNumber);
    }

    private void makeModeMessage(String mode){
        BaseUserMessage gameMode = new BaseUserMessage();
        gameMode.setGameMode(mode);
        setChanged();
        notifyObservers(gameMode);
    }

    private void makeNicknameMessage(String nickname){
        BaseUserMessage nicknameMessage = new BaseUserMessage();
        nicknameMessage.setNickname(nickname);
        setChanged();
        notifyObservers(nicknameMessage);
    }

}
