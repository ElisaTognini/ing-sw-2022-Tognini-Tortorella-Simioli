package it.polimi.ingsw.View.GUI;

import it.polimi.ingsw.View.GUI.Components.IslandViewComponent;
import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;

public class IslandSelectionController {

    @FXML AnchorPane anchorPane;

    @FXML Button doneButton;

    // dimensions to be checked and fixed
    public void drawIslands(ArrayList<IslandViewComponent> islandList){

        int distance = 100;
        doneButton.setVisible(false);

        for(int i = 0; i < islandList.size(); i++) {
            double angle = 2 * i * Math.PI / islandList.size();
            double xOffset = distance * Math.cos(angle);
            double yOffset = distance * Math.sin(angle);
            double x = xOffset + anchorPane.getWidth()/2;
            double y = yOffset - 65 + anchorPane.getHeight()/2; // to fix ?

            ImageView img = new ImageView(new Image("/island2.png"));

            img.setFitHeight(60);
            img.setFitWidth(60);

            img.setId(islandList.get(i).getId());
            img.setLayoutX(x);
            img.setLayoutY(y + 40);

            img.setOnMouseEntered(mouseEvent -> {
                img.setFitHeight(63);
                img.setFitWidth(63);
            });
            img.setOnMouseExited(mouseEvent -> {
                img.setFitHeight(60);
                img.setFitWidth(60);
            });
            img.setOnMouseClicked(mouseEvent -> {
                MainGUIController.getParameter().setIslandID(Integer.valueOf(img.getId()));
                doneButton.setVisible(true);
            });
            anchorPane.getChildren().add(img);
        }

    }

    public void doneClicked(){
        MainGUIController.closeCardStage();
    }

}
