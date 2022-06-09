package it.polimi.ingsw.View.GUI;

import it.polimi.ingsw.Utils.Enums.PawnDiscColor;
import it.polimi.ingsw.View.GUI.Components.IslandViewComponent;
import it.polimi.ingsw.View.GUI.Components.StudentViewComponent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.layout.*;

import java.util.ArrayList;

public class MainGUIController {

    @FXML private AnchorPane anchorPane;

    public void drawIslands(ArrayList<String> islands){

        int distance = 300;

        for(int i = 0; i < islands.size(); i++){
            double angle = 2 * i * Math.PI / islands.size();
            double xOffset = distance * Math.cos(angle);
            double yOffset = distance * Math.sin(angle);
            double x = xOffset + 120 + anchorPane.getWidth()/2;
            double y = yOffset - 80 + anchorPane.getHeight()/2;
            IslandViewComponent island = new IslandViewComponent();
            island.setLayoutX(x);
            island.setLayoutY(y);
            island.setMaxWidth(190);
            island.setMaxHeight(190);
            island.setAlignment(Pos.CENTER);
            //ImageView img = new ImageView(new Image("/island2.png"));
            //img.setFitHeight(190);
            //img.setFitWidth(190);
            //island.getChildren().add(img);
            addStudents(islands.get(i), island);
            anchorPane.getChildren().add(island);
        }
    }

    public void addStudents(String c, IslandViewComponent island){

        String[] colors = c.split(" ");
        for(int j = 0; j < Integer.valueOf(colors[1]); j++){
            island.getChildren().add(new StudentViewComponent(PawnDiscColor.BLUE));
        }
        for(int j = 0; j < Integer.valueOf(colors[2]); j++){
            island.getChildren().add(new StudentViewComponent(PawnDiscColor.GREEN));
        }
        for(int j = 0; j < Integer.valueOf(colors[3]); j++){
            island.getChildren().add(new StudentViewComponent(PawnDiscColor.YELLOW));
        }
        for(int j = 0; j < Integer.valueOf(colors[4]); j++){
            island.getChildren().add(new StudentViewComponent(PawnDiscColor.PINK));
        }
        for(int j = 0; j < Integer.valueOf(colors[5]); j++){
            island.getChildren().add(new StudentViewComponent(PawnDiscColor.RED));
        }
    }

}
