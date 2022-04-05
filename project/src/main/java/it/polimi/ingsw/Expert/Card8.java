package it.polimi.ingsw.Expert;

import it.polimi.ingsw.BoardClasses.Board;

/* adds 2 to the influence of the player who purchased this card */

public class Card8 extends CharacterCardTemplate{

    public Card8(Board board){
        super(board);
        cardID = 8;
        cost = 2;
    }

    public void useCard(int islandID){
        board.getIslandList().get(islandID).setExtra(2);
    }
}
