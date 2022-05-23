package it.polimi.ingsw.Client.ActionMessages;

import it.polimi.ingsw.Utils.Enums.PawnDiscColor;

import java.io.Serializable;

public class MoveStudentToDRMessage implements Serializable {
    private PawnDiscColor color;

    public MoveStudentToDRMessage(PawnDiscColor color){
        this.color = color;
    }

    public PawnDiscColor getColor(){
        return color;
    }
}
