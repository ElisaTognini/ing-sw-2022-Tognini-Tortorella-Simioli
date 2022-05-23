package it.polimi.ingsw.Client.ActionMessages;

import it.polimi.ingsw.Utils.Enums.PawnDiscColor;

import java.io.Serializable;

public class MoveStudentToIslandMessage implements Serializable {

    int islandID;
    PawnDiscColor color;

    public MoveStudentToIslandMessage(int islandID, PawnDiscColor color){
        this.color = color;
        this.islandID = islandID;
    }

    public PawnDiscColor getColor() {
        return color;
    }

    public int getIslandID() {
        return islandID;
    }
}
