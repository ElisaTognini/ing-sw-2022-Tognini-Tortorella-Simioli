package it.polimi.ingsw.Model.Expert;

import it.polimi.ingsw.Model.BasicElements.Student;
import it.polimi.ingsw.Model.BoardClasses.BoardExpert;
import it.polimi.ingsw.Utils.Enums.PawnDiscColor;
import it.polimi.ingsw.Model.SchoolBoardClasses.SchoolBoard;
import it.polimi.ingsw.Model.StudentContainer;

import java.util.ArrayList;

/** Class Card7 represents one of the twelve character cards: it contains a brief description of the effect of the card
 * as well as a reference to a student container to store the students which are actually on this card. */

public class Card7 extends CharacterCardTemplate{

    private final String description = "can choose up to three students on this card to switch with three in their entrance";

    private StudentContainer students;


    /** Constructor Card1 creates a new instance of this character card, assigning its ID and cost,
     * initializing a new student container and sets up the card.
     *
     * @param board of type BoardExpert - board */
    public Card7(BoardExpert board){
        super(board);
        cardID = 7;
        cost = 1;
        students = new StudentContainer();
        setupCard();
    }


    /** Private method setupCard adds four student to the card as per rules of the setup of the card */
    private void setupCard(){
        for(int i=0; i<6; i++){
            students.addStudent(board.getStudentBag().drawStudent());
        }
    }


    /** Method use card takes the parameter passed by the player inside the parameter o, the students to switch from
     * the card to the card and vice versa: for each student listed in the array list of students to change, this
     * method takes one student from the entrance, adds it to the card, then takes one of the students from the card and
     * adds it to the player's entrance, according to their choices.
     *
     * @param o of type Object - the parameters passed by the player
     * @param nickname of type String - the nickname of the player */
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


    /** Method checkIfActionIsForbidden checks if there are enough students of chosen colors in the entrance
     *
     * @param o of type Object - the parameters passed by the player
     * @param nickname of type String - the player's nickname.
     *
     * @return boolean - true if action is forbidden, false otherwise
     */
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


    /** Method toString builds a String containing all the info stored in this class
     *
     *  @return String - FORMAT: cardID, cost and description of the effect of the card, followed by the color and
     *  number of the students placed on the card, each separated by a "-" .
     *  */
    @Override
    public String toStringCard(){
        StringBuilder toRet = new StringBuilder(cardID + "-" + cost + "-");

        toRet.append(this.getDescription()).append("-");
        for(PawnDiscColor c : PawnDiscColor.values()){
            toRet.append(c).append("-").append(students.getInfluence(c)).append("-");
        }
        return toRet.toString();
    }


    /**getter method - Method getDescription returns the description of the effect of the card
     *
     * @return String - the description of the character card */
    @Override
    public String getDescription(){
        return description;
    }
}
