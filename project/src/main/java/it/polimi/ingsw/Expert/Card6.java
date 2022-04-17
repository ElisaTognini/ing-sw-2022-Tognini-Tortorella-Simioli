package it.polimi.ingsw.Expert;

import it.polimi.ingsw.BoardClasses.BoardExpert;

/* when resolving Conquering on an island, Towers do not count towards influence */

public class Card6 extends CharacterCardTemplate{

    public Card6(BoardExpert board){
        super(board);
        cardID = 6;
        cost = 3;
    }

    public void useCard(int islandID){
        int temp = 0;
        temp = board.getIslandList().get(islandID).getNumberOfTowers();
        board.getIslandList().get(islandID).setTowersOnHold(temp);
    }
}
