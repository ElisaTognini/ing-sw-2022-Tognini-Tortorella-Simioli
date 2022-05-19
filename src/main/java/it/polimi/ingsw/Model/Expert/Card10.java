package it.polimi.ingsw.Model.Expert;

import it.polimi.ingsw.Model.BasicElements.Student;
import it.polimi.ingsw.Model.BoardClasses.BoardExpert;
import it.polimi.ingsw.Utils.Enums.PawnDiscColor;
import it.polimi.ingsw.Model.SchoolBoardClasses.SchoolBoard;

import java.util.ArrayList;

/* you may switch up to 2 students between your entrance and your dining room */

public class Card10 extends CharacterCardTemplate{

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


    /* checks that player has got at least two students in entrance of the requested colors */
    @Override
    public boolean checkIfActionIsForbidden(Object o, String nickname){

        Parameter parameters;
        int sum1 = 0;
        int sum2 = 0;

        if(o instanceof Parameter){
            parameters = (Parameter)o;
        }
        else throw new IllegalArgumentException();

        ArrayList<PawnDiscColor> studentsInEntrance = parameters.getColorArrayList();
        ArrayList<PawnDiscColor> studentsInDiningRoom = parameters.getColorArrayList2();

        if(studentsInEntrance.size()!=studentsInDiningRoom.size() ||
                studentsInDiningRoom.size() > 2 || studentsInEntrance.size() > 2)
            return true;

        for(PawnDiscColor color : PawnDiscColor.values()){
            sum1 = 0;
            sum2 = 0;
            for(PawnDiscColor c : studentsInEntrance){
                if(c.equals(color))
                    sum1++;
            }

            for(PawnDiscColor c : studentsInDiningRoom){
                if(c.equals(color))
                    sum2++;
            }

            if(board.getPlayerSchoolBoard(nickname).getEntrance().getColorAvailability(color) < sum1)
                return true;

            if(board.getPlayerSchoolBoard(nickname).getDiningRoom().influenceForProf(color) < sum2)
                return true;
        }

        return false;

    }

    @Override
    public String toStringCard(){
        String toRet = String.valueOf(cardID) + " " + String.valueOf(cost);
        return toRet;
    }

}
