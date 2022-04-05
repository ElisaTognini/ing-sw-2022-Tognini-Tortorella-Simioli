package it.polimi.ingsw.Expert;

import it.polimi.ingsw.BoardClasses.Board;
import it.polimi.ingsw.BoardClasses.BoardExpert;
import it.polimi.ingsw.TailoredExceptions.EmptyException;

/* this character card places no entry tiles on islands of choice in order not to block
* influence calculation if mother nature ends there */
public class Card5 extends CharacterCardTemplate{

    public Card5(BoardExpert board){
        super(board);
        cardID = 5;
        cost = 2;
    }

    public void useCard(int islandID) throws EmptyException {
        board.getIslandList().get(islandID).setNoEntryTileToTrue();
        board.useNoEntryTile();
    }

}
