package it.polimi.ingsw.Expert;

import it.polimi.ingsw.BasicElements.Student;
import it.polimi.ingsw.BoardClasses.BoardExpert;
import it.polimi.ingsw.Enums.PawnDiscColor;
import it.polimi.ingsw.SchoolBoardClasses.SchoolBoard;
import it.polimi.ingsw.StudentContainer;

import java.util.ArrayList;

/* the player can choose up to three students on this card to switch with three in their entrance*/

public class Card7 extends CharacterCardTemplate{

    private StudentContainer students;

    public Card7(BoardExpert board){
        super(board);
        cardID = 7;
        cost = 1;
        students = new StudentContainer();
        setupCard();
    }

    private void setupCard(){
        for(int i=0; i<6; i++){
            students.addStudent(board.getStudentBag().drawStudent());
        }
    }

    public void useCard(Object o, String nickname){
        Parameter parameters;
        SchoolBoard sb = board.getPlayerSchoolBoard(nickname);
        Student student1;

        if(o instanceof Parameter){
            parameters = (Parameter)o;
        }
        else throw new IllegalArgumentException();

        ArrayList<PawnDiscColor> studentsToEntrance = parameters.getColorArrayList();
        ArrayList<PawnDiscColor> studentsToChange = parameters.getColorArrayList2();

        for(int i = 0; i < studentsToEntrance.size(); i++){
            student1 = sb.getEntrance().removeStudent(studentsToChange.get(i));
            students.addStudent(student1);
            student1 = students.retrieveStudent(studentsToEntrance.get(i));
            sb.getEntrance().addStudent(student1);
        }
    }

    @Override
    public boolean checkIfActionIsForbidden(Object o, String nickname) throws IllegalArgumentException {

        Parameter parameters;
        int sum1 = 0;
        int sum2 = 0;

        if(o instanceof Parameter){
            parameters = (Parameter)o;
        }
        else throw new IllegalArgumentException();

        for(PawnDiscColor c : PawnDiscColor.values()){
            sum1 = 0;
            sum2 = 0;
            for(PawnDiscColor cParam  : parameters.getColorArrayList()){
                if(c.equals(cParam))
                    sum1++;
            }

            for(PawnDiscColor cParam  : parameters.getColorArrayList2()){
                if(c.equals(cParam))
                    sum2++;
            }

            if(students.getInfluence(c) < sum1)
                return true;
            if(board.getPlayerSchoolBoard(nickname).getEntrance().getColorAvailability(c) < sum2)
                return true;
        }

        return false;
    }

    @Override
    public String toString(){
        return "on the card\n" + "PINK " + students.getInfluence(PawnDiscColor.PINK) +
                "\nYELLOW " + students.getInfluence(PawnDiscColor.YELLOW) +
                "\nGREEN " + students.getInfluence(PawnDiscColor.GREEN) +
                "\nBLUE " + students.getInfluence(PawnDiscColor.BLUE) +
                "\nRED " + students.getInfluence(PawnDiscColor.RED) + "\n\n";
    }

}
