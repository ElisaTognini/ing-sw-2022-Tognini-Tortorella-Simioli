package it.polimi.ingsw.View.GUI;

import it.polimi.ingsw.View.GUI.Components.Island;
import it.polimi.ingsw.View.GUI.Components.Student;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.util.ArrayList;

public class MainGUIController {

    @FXML private AnchorPane anchorPane;

    public void drawIslands(ArrayList<String> islands){

        int distance = 330;

        for(int i = 0; i < islands.size(); i++){
            double angle = 2 * i * Math.PI / islands.size();
            double xOffset = distance * Math.cos(angle);
            double yOffset = distance * Math.sin(angle);
            double x = xOffset + 250 + anchorPane.getWidth()/2;
            double y = yOffset + anchorPane.getHeight()/2;
            Island island = new Island();
            island.setLayoutX(x);
            island.setLayoutY(y);
            island.resize(200, 200);
            anchorPane.getChildren().add(island);
        }
    }

    public void addStudents(String c, Island island){
        String[] colors = c.split(" ");
        for(int i = 1; i < 6; i++){
            for(int j = 0; j < Integer.valueOf(colors[i]); j++){
                // island.getChildren().add(new Student())
            }
        }
    }

}
