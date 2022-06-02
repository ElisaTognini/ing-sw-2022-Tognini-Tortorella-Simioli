package it.polimi.ingsw.View.GUI;

import it.polimi.ingsw.Client.Client;
import javafx.application.Application;
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
    Storage storage = new Storage();
    GUIControllerInitialPhase initialController;
    Client client;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage){

        storage = new Storage();
        initialController = new GUIControllerInitialPhase();
        primaryStage = stage;

        try {
            setupStartingScene();
        }catch(IOException e){
            System.err.println("error in launching GUI");
            e.printStackTrace();
            e.getCause();
        }

    }

    private void setupStartingScene() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/GameStartScene.fxml"));
        Scene startingScene = new Scene(root);
        startingScene.getStylesheets().add(getClass().getResource("/stylesheetScene1.css").toExternalForm());
        primaryStage.setScene(startingScene);
        primaryStage.getIcons().add(new Image("/icon.png"));
        primaryStage.setTitle("Eriantys");
        primaryStage.show();
    }

    public void initClient(String ip){
        client = new Client(ip, 12345);
        client.run();
    }

    @Override
    public void update(Observable o, Object arg) {
        initClient((String)arg);
    }
}
