package it.polimi.ingsw.Expert;

/* take one student from this card and place on an island of your choice */

import it.polimi.ingsw.BoardClasses.Board;
import it.polimi.ingsw.BoardClasses.BoardExpert;
import it.polimi.ingsw.Enums.PawnDiscColor;
import it.polimi.ingsw.StudentContainer;

public class Card1 extends CharacterCardTemplate{

    private StudentContainer students;

    public Card1(BoardExpert board){
        super(board);
        cardID = 1;
        cost = 1;
        students = new StudentContainer();
        setupCard();
    }

    public void useCard(PawnDiscColor color, int islandID){
        board.getIslandList().get(islandID).addStudent(students.retrieveStudent(color));
        setupCard();
    }

    private void setupCard(){
        for(int i=0; i<4; i++){
            students.addStudent(board.getStudentBag().drawStudent());
        }
    }
}
