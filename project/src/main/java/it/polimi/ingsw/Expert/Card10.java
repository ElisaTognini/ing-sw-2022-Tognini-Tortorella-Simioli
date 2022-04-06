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

    public Card10(BoardExpert board){
        super(board);
        cardID = 10;
        cost = 1;
    }

    /* insert null for students you don't want to switch
    *  or use 2 different methods: useCard1 and useCard2 for one or two switches */

    public void useCard(ArrayList<PawnDiscColor> studentsInEntrance, ArrayList<PawnDiscColor> studentsInDiningRoom,
                        String nickname){
        SchoolBoard sb = board.getPlayerSchoolBoard(nickname);

        Student student1;

        for(int i = 0; i < studentsInEntrance.size(); i++){
            student1 = sb.getEntrance().removeStudent(studentsInEntrance.get(i));
            sb.getEntrance().addStudent(sb.getDiningRoom().getContainer().retrieveStudent(studentsInDiningRoom.get(i)));
            sb.getDiningRoom().getContainer().addStudent(student1);
        }

    }

}
