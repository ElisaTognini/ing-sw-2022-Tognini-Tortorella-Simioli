package it.polimi.ingsw.Expert;

/* choose a type of student: every player (including yourself) must return 3 students of that type
*  from their dining room to the bag. If any player has fewer than 3 students of that type,
*  return as many students as they have */

import it.polimi.ingsw.BasicElements.Student;
import it.polimi.ingsw.BoardClasses.Board;
import it.polimi.ingsw.BoardClasses.BoardExpert;
import it.polimi.ingsw.Enums.PawnDiscColor;
import it.polimi.ingsw.SchoolBoardClasses.SchoolBoard;

import java.util.ArrayList;

public class Card12 extends CharacterCardTemplate{

    public Card12(BoardExpert board){
        super(board);
        cardID = 12;
        cost = 3;
    }

    public void useCard(Object o, String nickname){
        Parameter parameters;

        if(o instanceof Parameter){
            parameters = (Parameter)o;
        }
        else throw new IllegalArgumentException();

        for(SchoolBoard sb : board.getSchoolBoards()){
            for(int i = 0; i < sb.getDiningRoom().influenceForProf(parameters.getColor()) || i < 3; i++){
                board.getStudentBag().addStudentBack(sb.getDiningRoom().getContainer().retrieveStudent(parameters.getColor()));
            }
        }
    }

}
