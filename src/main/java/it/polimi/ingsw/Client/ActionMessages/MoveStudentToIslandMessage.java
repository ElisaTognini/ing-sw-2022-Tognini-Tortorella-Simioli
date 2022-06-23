package it.polimi.ingsw.Client.ActionMessages;

import it.polimi.ingsw.Utils.Enums.PawnDiscColor;

import java.io.Serializable;

/**
 * Class MoveStudentToIslandMessage is a message sent from the client to the server,
 * informing it of the color of the student to move to an island
 * and the id of the chosen island.
 *
 * @see java.io.Serializable
 * */
public class MoveStudentToIslandMessage implements Serializable {

    int islandID;
    PawnDiscColor color;

    /**
     * Constructor MoveStudentToIslandMessage creates a new MoveStudentToIslandMessage instance.
     *
     * @param color - of type PawnDiscColor - color of moved student.
     * @param islandID - of type int - id of the island on which the student has to be moved.
     * */
    public MoveStudentToIslandMessage(int islandID, PawnDiscColor color){
        this.color = color;
        this.islandID = islandID;
    }

    /**
     * getter method getColor returns the color of the moved student.
     *
     * @return PawnDiscColor - color.
     * */
    public PawnDiscColor getColor() {
        return color;
    }

    /**
     * getter method getIslandID returns the id of the island on which the student has to be moved.
     *
     * @return int - id of the island.
     * */
    public int getIslandID() {
        return islandID;
    }
}
