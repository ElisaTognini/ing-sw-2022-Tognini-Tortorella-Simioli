package it.polimi.ingsw.View.GUI;
import it.polimi.ingsw.Client.Client;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.input.*;

import java.util.Observable;


public class GUIControllerInitialPhase{

    String ipAddress;
    Storage storage = new Storage();
    GUI gui;

    @FXML private BorderPane outsidePane = new BorderPane();
    @FXML private TextField ipTextField = new TextField();


   /* public GUIControllerInitialPhase(GUI gui){
        this.gui = gui;
        storage.addObserver(gui);
    }*/

    public void getIPAddress(KeyEvent ke){
        if(ke.getCode().equals(KeyCode.ENTER)){
            ipAddress = ipTextField.getText();
            storage.setIpAddress(ipAddress);
        }
    }
}
