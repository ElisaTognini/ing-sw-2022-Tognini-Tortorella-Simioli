package it.polimi.ingsw.Expert;

import it.polimi.ingsw.BasicElements.Student;
import it.polimi.ingsw.BoardClasses.Board;
import it.polimi.ingsw.BoardClasses.BoardExpert;
import it.polimi.ingsw.SchoolBoardClasses.SchoolBoard;

/* adds 2 to the influence of the player who purchased this card */

public class Card8 extends CharacterCardTemplate{

    public Card8(BoardExpert board){
        super(board);
        cardID = 8;
        cost = 2;
    }

    public void useCard(Object o, String nickname){
        Parameter parameters;

        if(o instanceof Parameter){
            parameters = (Parameter)o;
        }
        else throw new IllegalArgumentException();

        board.getIslandList().get(parameters.getIslandID()).setExtra(2);
    }
}
