package it.polimi.ingsw.Expert;

/* take one student from this card and place on an island of your choice */

import it.polimi.ingsw.BoardClasses.Board;
import it.polimi.ingsw.BoardClasses.BoardExpert;
import it.polimi.ingsw.Enums.PawnDiscColor;
import it.polimi.ingsw.StudentContainer;
/* take one student from this card and place it on an island of your choice; then
* draw another student and place it on this card */
public class Card1 extends CharacterCardTemplate{

    private StudentContainer students;

    public Card1(BoardExpert board){
        super(board);
        cardID = 1;
        cost = 1;
        students = new StudentContainer();
        setupCard();
    }

    public void useCard(Object o, String nickname){
        Parameter parameters;

        if(o instanceof Parameter){
            parameters = (Parameter)o;
        }
        else throw new IllegalArgumentException();

        board.getIslandList().get(parameters.getIslandID()).addStudent(students.retrieveStudent(parameters.getColor()));
        students.addStudent(board.getStudentBag().drawStudent());
    }

    private void setupCard(){
        for(int i=0; i<4; i++){
            students.addStudent(board.getStudentBag().drawStudent());
        }
    }
}
