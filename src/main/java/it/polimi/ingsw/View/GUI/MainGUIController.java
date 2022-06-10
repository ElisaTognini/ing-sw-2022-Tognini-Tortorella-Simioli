package it.polimi.ingsw.View.GUI;

import it.polimi.ingsw.Utils.Enums.PawnDiscColor;
import it.polimi.ingsw.Utils.Enums.TowerColor;
import it.polimi.ingsw.View.GUI.Components.IslandViewComponent;
import it.polimi.ingsw.View.GUI.Components.MNViewComponent;
import it.polimi.ingsw.View.GUI.Components.StudentViewComponent;
import it.polimi.ingsw.View.GUI.Components.TowerViewComponent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.HashMap;

public class MainGUIController {

    @FXML private AnchorPane anchorPane;
    @FXML private HBox cloudHBox;
    @FXML private ImageView mainPlayerSB;

    private GridPane deckHolder = new GridPane();

    ArrayList<IslandViewComponent> islandList = new ArrayList<>();
    ArrayList<GridPane> cloudGrids = new ArrayList<>();
    HashMap<String, TowerColor> towerColors = new HashMap<>();

    public void drawIslands(ArrayList<String> islands, int mnPosition){

        int distance = 300;

        for(int i = 0; i < islands.size(); i++){
            double angle = 2 * i * Math.PI / islands.size();
            double xOffset = distance * Math.cos(angle);
            double yOffset = distance * Math.sin(angle);
            double x = xOffset + 120 + anchorPane.getWidth()/2;
            double y = yOffset - 80 + anchorPane.getHeight()/2;
            IslandViewComponent island = new IslandViewComponent();
            island.setId(String.valueOf(i));
            islandList.add(island);
            island.setLayoutX(x + 50);
            island.setLayoutY(y + 40);
            island.setMaxWidth(100);
            island.setMaxHeight(100);
            island.setAlignment(Pos.BOTTOM_CENTER);
            ImageView img = new ImageView(new Image("/island2.png"));
            img.setLayoutX(x);
            img.setLayoutY(y);
            img.setFitHeight(190);
            img.setFitWidth(190);
            addStudents(islands.get(i), island);
            addTowers(island, islands.get(i));
            anchorPane.getChildren().add(img);
            anchorPane.getChildren().add(island);
        }
        addMotherNature(mnPosition);
    }

    public void drawSchoolBoards(ArrayList<String> schoolBoards){

        mainPlayerSB.setLayoutY(550);
        mainPlayerSB.setLayoutX(13);
        mainPlayerSB.setFitWidth(500);
        mainPlayerSB.setFitHeight(230);


    }

    public void drawClouds(ArrayList<String> clouds){

        cloudHBox.setLayoutX(anchorPane.getWidth()/2 + 120);
        cloudHBox.setLayoutY(anchorPane.getHeight()/2 - 120);

        if(clouds.size() == 3)
            cloudHBox.setLayoutX(anchorPane.getWidth()/2 + 60);

        for(String c : clouds){
            ImageView cloud = new ImageView(new Image("/cloud_card.png"));
            StackPane sp = new StackPane();
            cloud.setFitWidth(120);
            cloud.setFitHeight(120);
            GridPane cloudGrid = new GridPane();
            cloudHBox.setAlignment(Pos.TOP_CENTER);
            cloudGrid.setHgap(5);
            cloudGrid.setVgap(5);
            cloudGrids.add(cloudGrid);
            sp.getChildren().addAll(cloud,cloudGrid);
            cloudHBox.getChildren().add(sp);
        }
    }

    public void drawDeck(ArrayList<String> decks){
        deckHolder.setLayoutX(13);
        deckHolder.setLayoutY(20);

        deckHolder.setHgap(5);
        deckHolder.setVgap(5);
    }

    public void addStudents(String c, IslandViewComponent island){
        int i = 0;
        String[] colors = c.split(" ");
        for(int j = 0; j < Integer.valueOf(colors[1]); j++){
            island.add(new StudentViewComponent(PawnDiscColor.BLUE) ,j, 1);
        }
        for(int j = 0; j < Integer.valueOf(colors[2]); j++){
            island.add(new StudentViewComponent(PawnDiscColor.GREEN), j,2);
            i++;
        }
        for(int j = 0; j < Integer.valueOf(colors[3]); j++){
            island.add(new StudentViewComponent(PawnDiscColor.YELLOW), j,3);
            i++;
        }
        for(int j = 0; j < Integer.valueOf(colors[4]); j++){
            island.add(new StudentViewComponent(PawnDiscColor.PINK), j,4);
            i++;
        }
        for(int j = 0; j < Integer.valueOf(colors[5]); j++){
            island.add(new StudentViewComponent(PawnDiscColor.RED), j,5);
            i++;
        }
    }

    private void addMotherNature(int i){
        islandList.get(i).add(new MNViewComponent(), 0, 0);
    }

    private void addTowers(IslandViewComponent island, String s){
        String[] data = s.split(" ");

        if(data.length < 7)
            return;

       TowerColor color = towerColors.get(6);

        for(int i = 0; i < Integer.valueOf(data[7]); i++){
            island.add(new TowerViewComponent(color), i, 0);
        }
    }

    public void studentsOnClouds(ArrayList<String> clouds){
        for(int i = 0; i < clouds.size(); i++){
            cloudGrids.get(i).getChildren().clear();
            String[] students = clouds.get(i).split(" ");

            if(students.length == 1)
                continue;

            for(int j = 1; j < students.length ; j++){
                cloudGrids.get(i).add(new StudentViewComponent(PawnDiscColor.valueOf(students[j])), j, 0);
            }
        }
    }

}
