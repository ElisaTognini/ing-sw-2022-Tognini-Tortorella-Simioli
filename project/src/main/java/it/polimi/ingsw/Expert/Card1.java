package it.polimi.ingsw.Expert;

/* take one student from this card and place on an island of your choice */

import it.polimi.ingsw.BoardClasses.Board;
import it.polimi.ingsw.Enums.PawnDiscColor;
import it.polimi.ingsw.StudentContainer;
import it.polimi.ingsw.TailoredExceptions.EmptyException;

public class Card1 extends CharacterCardTemplate{

    private StudentContainer students;

    public Card1(Board board) throws EmptyException {
        super(board);
        cardID = 1;
        cost = 1;
        students = new StudentContainer();
        setupCard();
    }

    public void useCard(PawnDiscColor color, int islandID) throws EmptyException{
        board.getIslandList().get(islandID).addStudent(students.retrieveStudent(color));
    }

    private void setupCard() throws EmptyException {
        for(int i=0; i<4; i++){
            students.addStudent(board.getStudentBag().drawStudent());
        }
    }
}
