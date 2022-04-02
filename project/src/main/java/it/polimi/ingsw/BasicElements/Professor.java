package it.polimi.ingsw.BasicElements;

import it.polimi.ingsw.Enums.PawnDiscColor;

public class Professor {

    private PawnDiscColor color;
    private boolean ownedByPlayer;


    public Professor(PawnDiscColor color) {
        this.color = color;
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
