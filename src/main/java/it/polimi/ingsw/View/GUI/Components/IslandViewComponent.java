package it.polimi.ingsw.View.GUI.Components;

import javafx.scene.layout.*;

public class IslandViewComponent extends GridPane {

    private int islandID;

    public IslandViewComponent(int islandID){
        this.setVgap(2);
        this.setHgap(2);
        this.islandID = islandID;
    }

    public int getIslandID(){
        return islandID;
    }

}
