package it.polimi.ingsw.Expert;

/*  */

import it.polimi.ingsw.BasicElements.Student;
import it.polimi.ingsw.BoardClasses.Board;
import it.polimi.ingsw.BoardClasses.BoardExpert;
import it.polimi.ingsw.Enums.PawnDiscColor;
import it.polimi.ingsw.SchoolBoardClasses.SchoolBoard;

import java.util.ArrayList;

/* you may switch up to 2 students between your entrance and your dining room */

public class Card10 extends CharacterCardTemplate{

    /**/

    public Card10(BoardExpert board){
        super(board);
        cardID = 10;
        cost = 1;
    }

    public void useCard(Object o, String nickname){
        Parameter parameters;
        SchoolBoard sb = board.getPlayerSchoolBoard(nickname);
        Student student1;

        if(o instanceof Parameter){
            parameters = (Parameter)o;
        }
        else throw new IllegalArgumentException();

        ArrayList<PawnDiscColor> studentsInEntrance = parameters.getColorArrayList();
        ArrayList<PawnDiscColor> studentsInDiningRoom = parameters.getColorArrayList2();

        for(int i = 0; i < studentsInEntrance.size(); i++){
            student1 = sb.getEntrance().removeStudent(studentsInEntrance.get(i));
            sb.getEntrance().addStudent(sb.getDiningRoom().getContainer().retrieveStudent(studentsInDiningRoom.get(i)));
            sb.getDiningRoom().getContainer().addStudent(student1);
        }

    }

    public boolean checkIfActionIsForbidden(ArrayList<PawnDiscColor> studentsInEntrance, ArrayList<PawnDiscColor> studentsInDiningRoom,
                                            String nickname){

        if(studentsInEntrance.size()!=studentsInDiningRoom.size() ||
                studentsInDiningRoom.size() > 2 || studentsInEntrance.size() > 2) return true;
        else return false;

    }


}
