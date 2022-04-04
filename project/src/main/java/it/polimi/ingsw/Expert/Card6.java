package it.polimi.ingsw.Expert;

import it.polimi.ingsw.BoardClasses.Board;

public class Card6 extends CharacterCardTemplate{

    public Card6(Board board){
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
