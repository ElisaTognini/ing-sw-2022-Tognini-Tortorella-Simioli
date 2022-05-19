package it.polimi.ingsw.Client.ActionMessages;

import it.polimi.ingsw.Utils.Enums.PawnDiscColor;

public class MoveStudentToDRMessage {
    private PawnDiscColor color;

    public MoveStudentToDRMessage(PawnDiscColor color){
        this.color = color;
    }

    public PawnDiscColor getColor(){
        return color;
    }
}
