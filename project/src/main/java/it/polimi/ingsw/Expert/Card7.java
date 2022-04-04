package it.polimi.ingsw.Expert;

import it.polimi.ingsw.BoardClasses.Board;
import it.polimi.ingsw.Enums.PawnDiscColor;
import it.polimi.ingsw.StudentContainer;
import it.polimi.ingsw.TailoredExceptions.ActionNotAuthorizedException;
import it.polimi.ingsw.TailoredExceptions.EmptyException;
import it.polimi.ingsw.TailoredExceptions.InvalidCardActionException;

import java.util.ArrayList;

public class Card7 extends CharacterCardTemplate{

    private StudentContainer students;

    public Card7(Board board) throws EmptyException {
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

    public void useCard(ArrayList<PawnDiscColor> studentsToEntrance, ArrayList<PawnDiscColor> studentsToChange)
            throws ActionNotAuthorizedException{
        if(studentsToChange.size()>3) throw new ActionNotAuthorizedException();
        else {
            for(PawnDiscColor c: studentsToChange ){
                //students.addStudent();
            }
        }

    }
}
