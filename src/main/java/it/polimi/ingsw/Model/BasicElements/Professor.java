package it.polimi.ingsw.Model.BasicElements;

import it.polimi.ingsw.Utils.Enums.PawnDiscColor;

public class Professor {

    private PawnDiscColor color;
    private boolean ownedByPlayer;


    public Professor(PawnDiscColor color) {
        this.color = color;
        this.ownedByPlayer = false;
    }

    public PawnDiscColor getColor() {
        return color;
    }

    public boolean isOwnedByPlayer(){
        return ownedByPlayer;
    }

    public void setOwnedToTrue(){
        ownedByPlayer = true;
    }

    public void setOwnedToFalse(){
        ownedByPlayer = false;
    }
}
