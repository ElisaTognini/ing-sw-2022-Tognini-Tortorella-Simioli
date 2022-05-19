package it.polimi.ingsw.Model.Expert;

/* take one student from this card and place on an island of your choice */

import it.polimi.ingsw.Model.BoardClasses.BoardExpert;
import it.polimi.ingsw.Utils.Enums.PawnDiscColor;
import it.polimi.ingsw.Model.StudentContainer;
/* take one student from this card and place it on an island of your choice; then
* draw another student and place it on this card */
public class Card1 extends CharacterCardTemplate{

    private StudentContainer students;

    public Card1(BoardExpert board){
        super(board);
        cardID = 1;
        cost = 1;
        students = new StudentContainer();
        setupCard();
    }

    public void useCard(Object o, String nickname){
        Parameter parameters;

        if(o instanceof Parameter){
            parameters = (Parameter)o;
        }
        else throw new IllegalArgumentException();

        board.getIslandList().get(parameters.getIslandID()).addStudent(students.retrieveStudent(parameters.getColor()));

        /* if bag is empty, a new student is not to be added and it means that it's the last round */

        if(board.getStudentBag().checkIfStudentBagEmpty()){
            board.setLastRound();
        }
        else
            students.addStudent(board.getStudentBag().drawStudent());
    }


    /*
    checks:
     - if chosen color is available on the card
     - if there is at least one student on the card (if StudentBag is empty)
     */
    @Override
    public boolean checkIfActionIsForbidden(Object o, String nickname) throws IllegalArgumentException {

        Parameter parameters;

        if(o instanceof Parameter){
            parameters = (Parameter)o;
        }
        else throw new IllegalArgumentException();

        if(students.getInfluence(parameters.getColor()) > 0){
            if(students.size() > 0){
                return false;
            }
        }
        return true;
    }

    private void setupCard(){
        for(int i=0; i<4; i++){
            students.addStudent(board.getStudentBag().drawStudent());
        }
    }

    @Override
    /* FORMAT: cardID followed by the color and number of the students placed on the card */
    public String toStringCard(){
        StringBuilder toRet = new StringBuilder(cardID + " " + cost + " ");

        for(PawnDiscColor c : PawnDiscColor.values()){
            toRet.append(c).append(" ").append(students.getInfluence(c)).append(" ");
        }
        return toRet.toString();
    }
}
