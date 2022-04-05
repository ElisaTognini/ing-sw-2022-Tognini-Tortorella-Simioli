package it.polimi.ingsw.Expert;
import it.polimi.ingsw.BoardClasses.BoardExpert;

public abstract class CharacterCardTemplate {
    protected int cost;
    protected int cardID;
    protected BoardExpert board;

    public CharacterCardTemplate(BoardExpert board){
        this.board = board;
    }

    protected void increaseCost(){
        cost++;
    }

    public int getCost(){
        return cost;
    }

    public int getCardID(){
        return cardID;
    }
}
