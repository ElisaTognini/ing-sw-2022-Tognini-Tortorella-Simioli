package it.polimi.ingsw.Expert;

import it.polimi.ingsw.BasicElements.Student;
import it.polimi.ingsw.BoardClasses.Board;
import it.polimi.ingsw.BoardClasses.BoardExpert;
import it.polimi.ingsw.Enums.PawnDiscColor;
import it.polimi.ingsw.SchoolBoardClasses.SchoolBoard;
import it.polimi.ingsw.StudentContainer;

import java.util.ArrayList;

/* in setup, draw 4 students and place them on this card
*  take 1 student from this card and place it in your dining room.
*  then, take a new student from the bag and place it on this card */

public class Card11 extends CharacterCardTemplate{

    private StudentContainer students;

    public Card11(BoardExpert board){
        super(board);
        cardID = 11;
        cost = 2;
        students = new StudentContainer();
        setupCard();
    }

    public void useCard(Object o, String nickname){
        Parameter parameters;
        SchoolBoard sb = board.getPlayerSchoolBoard(nickname);


        if(o instanceof Parameter){
            parameters = (Parameter)o;
        }
        else throw new IllegalArgumentException();

        sb.getDiningRoom().addStudent(students.retrieveStudent(parameters.getColor()));
        students.addStudent(board.getStudentBag().drawStudent());
    }

    private void setupCard() {
        for(int i=0; i<4; i++){
            students.addStudent(board.getStudentBag().drawStudent());
        }
    }

}
