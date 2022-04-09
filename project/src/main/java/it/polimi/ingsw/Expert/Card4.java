package it.polimi.ingsw.Expert;

import it.polimi.ingsw.BoardClasses.BoardExpert;

/* this card allows the current player to move mn up to two additional islands */
public class Card4 extends CharacterCardTemplate{

    public Card4(BoardExpert board) {
        super(board);
        cardID = 4;
        cost = 1;
    }

    public void useCard(int moves){
        board.setAdditionalMoves(moves);
    }

}
