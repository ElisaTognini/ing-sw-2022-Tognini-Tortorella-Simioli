package it.polimi.ingsw.Expert;

import it.polimi.ingsw.BoardClasses.Board;

/* this character card places no entry tiles on islands of choice in order not to block
* influence calculation if mother nature ends there */
public class Card5 extends CharacterCardTemplate{

    private int noEntryTiles;

    public Card5(Board board){
        super(board);
        cardID = 5;
        cost = 2;
        noEntryTiles = 4;
    }

    public void useCard(int islandID){
        board.getIslandList().get(islandID).setNoEntryTileToTrue();
        noEntryTiles --;
    }

    public void retrieveNoEntryTile(){
        noEntryTiles++;
    }
}
