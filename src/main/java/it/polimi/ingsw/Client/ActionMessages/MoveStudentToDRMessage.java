package it.polimi.ingsw.Client.ActionMessages;

import it.polimi.ingsw.Utils.Enums.PawnDiscColor;

import java.io.Serializable;

/**
 * Class MoveStudentToDRMessage is a message sent from the client to the server,
 * informing it of the color of the student to move to the dining room.
 *
 * @see java.io.Serializable
 * */

public class MoveStudentToDRMessage implements Serializable {
    private PawnDiscColor color;

    /**
     * Constructor MoveStudentToDRMessage creates a new MoveStudentToDRMessage instance.
     *
     * @param color - of type PawnDiscColor - color of moved student.
     * */
    public MoveStudentToDRMessage(PawnDiscColor color){
        this.color = color;
    }

    /**
     * getter method getColor returns the color of the moved student.
     *
     * @return PawnDiscColor - color.
     * */
    public PawnDiscColor getColor(){
        return color;
    }
}
