package it.polimi.ingsw.Expert;

import it.polimi.ingsw.BasicElements.Student;
import it.polimi.ingsw.BoardClasses.BoardExpert;
import it.polimi.ingsw.Enums.PawnDiscColor;
import it.polimi.ingsw.SchoolBoardClasses.SchoolBoard;
import it.polimi.ingsw.StudentContainer;
import it.polimi.ingsw.TailoredExceptions.ActionNotAuthorizedException;
import it.polimi.ingsw.TailoredExceptions.EmptyException;

import java.util.ArrayList;

/* the player can choose up to three students on this card to switch with three in their dining room*/

public class Card7 extends CharacterCardTemplate{

    private StudentContainer students;

    public Card7(BoardExpert board) throws EmptyException {
        super(board);
        cardID = 7;
        cost = 1;
        students = new StudentContainer();
        setupCard();
    }

    private void setupCard() throws EmptyException {
        for(int i=0; i<6; i++){
            students.addStudent(board.getStudentBag().drawStudent());
        }
    }

    public void useCard(ArrayList<PawnDiscColor> studentsToEntrance, ArrayList<PawnDiscColor> studentsToChange,
                        String nickname)
            throws ActionNotAuthorizedException, EmptyException {
        SchoolBoard sb = board.getPlayerSchoolBoard(nickname);
        Student student1;

        if(studentsToEntrance.size()!=studentsToChange.size() || studentsToChange.size()>3) {
            throw new ActionNotAuthorizedException();
        }
        else {
            for(int i = 0; i < studentsToEntrance.size(); i++){
                student1 = sb.getEntrance().removeStudent(studentsToChange.get(i));
                students.addStudent(student1);
                student1 = students.retrieveStudent(studentsToEntrance.get(i));
                sb.getEntrance().addStudent(student1);
            }

        }
    }
}
