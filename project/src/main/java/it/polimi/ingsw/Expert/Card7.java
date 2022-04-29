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

        if(o instanceof Parameter){
            parameters = (Parameter)o;
        }
        else throw new IllegalArgumentException();

        for(PawnDiscColor c : parameters.getColorArrayList()) {
            if (students.getInfluence(c) > 0) {
                if (students.size() > 0) {
                    return false;
                }
            }
        }
        for(PawnDiscColor c : parameters.getColorArrayList2()){
            if(board.getPlayerSchoolBoard(nickname).getEntrance().isColorAvailable(c)){
                return false;
            }
        }
        return true;
    }

}
