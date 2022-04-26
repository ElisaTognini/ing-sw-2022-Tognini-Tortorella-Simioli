package it.polimi.ingsw.Expert;
import it.polimi.ingsw.BoardClasses.BoardExpert;
import it.polimi.ingsw.Enums.PawnDiscColor;
import it.polimi.ingsw.Player;

import java.util.ArrayList;

public abstract class CharacterCardTemplate {
    protected int cost;
    protected int cardID;
    protected BoardExpert board;

    public CharacterCardTemplate(BoardExpert board){
        this.board = board;
    }

    public void increaseCost(){
        cost++;
    }

    public int getCost(){
        return cost;
    }

    public int getCardID(){
        return cardID;
    }

    //method overridden in cards that need parameters
    public void useCard(Object o, String nickname){}

}
