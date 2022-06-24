package it.polimi.ingsw.View.GUI.Components;

import javafx.scene.layout.*;

/**
 * Class CharacterCardViewComponent is used to handle islands in GUI.
 *
 * @see GridPane
 * */
public class IslandViewComponent extends GridPane {

    private int islandID;

    /**
     * Constructor IslandViewComponent creates a new IslandViewComponent instance.
     *
     * @param islandID of type int - island id.
     * */
    public IslandViewComponent(int islandID){
        this.setVgap(2);
        this.setHgap(2);
        this.islandID = islandID;
    }

    /**
     * Getter method getIslandID returns the id of this island.
     *
     * @return int - island id.
     * */
    public int getIslandID(){
        return islandID;
    }

}
