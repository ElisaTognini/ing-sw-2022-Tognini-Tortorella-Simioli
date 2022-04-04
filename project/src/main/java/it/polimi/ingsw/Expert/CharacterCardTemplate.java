package it.polimi.ingsw.Expert;

import it.polimi.ingsw.BoardClasses.Board;

public abstract class CharacterCardTemplate {
    protected int cost;
    protected int cardID;
    protected Board board;

    public CharacterCardTemplate(Board board){
        this.board = board;
    }

    protected void increaseCost(){
        cost++;
    }

    public int getCost(){
        return cost;
    }

}
