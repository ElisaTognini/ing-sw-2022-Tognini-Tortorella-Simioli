package it.polimi.ingsw.Model.Expert;
import it.polimi.ingsw.Model.BoardClasses.BoardExpert;
import it.polimi.ingsw.Utils.Enums.PawnDiscColor;
import it.polimi.ingsw.Model.StudentContainer;

/** Class Card1 represents one of the twelve character cards: it contains a brief description of the effect of the card
 * as well as a reference to a student container to store the students which are actually on this card. */

public class Card1 extends CharacterCardTemplate{

    private String description = "take one student from this card and place on an island of your choice ";
    private StudentContainer students;

    /** Constructor Card1 creates a new instance of this character card, assigning its ID and cost,
     * initializing a new student container and sets up the card.
     *
     * @param board of type BoardExpert - board */
    public Card1(BoardExpert board){
        super(board);
        cardID = 1;
        cost = 1;
        students = new StudentContainer();
        setupCard();
    }


    /** Method use card takes the parameter passed by the player inside the parameter o, the island ID and the student,
     * retrieves the latter from the card and the adds it to the island, then refills the student on the card. If the
     * bag is empty, a new student is not to be added to the card because it means it is the last round.
     *
     * @param o of type Object - the parameters passed by the player
     * @param nickname of type String - the nickname of the player */
    public void useCard(Object o, String nickname){
        Parameter parameters;

        if(o instanceof Parameter){
            parameters = (Parameter)o;
        }
        else throw new IllegalArgumentException();

        board.getIslandList().get(parameters.getIslandID()).addStudent(students.retrieveStudent(parameters.getColor()));

        if(board.getStudentBag().checkIfStudentBagEmpty()){
            board.setLastRound();
        }
        else
            students.addStudent(board.getStudentBag().drawStudent());
    }


    /** Method checkIfActionIsForbidden checks if chosen color is available on the card and if the island ID provided
     * by the player is a valid one.
     *
     * @param o of type Object - the parameters passed by the player
     * @param nickname of type String - the player's nickname.
     *
     * @return boolean - true if action is forbidden, false otherwise
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

            return parameters.getIslandID() >= 0 && parameters.getIslandID() <= board.getIslandList().size() - 1;
        }
        return true;
    }


    /** Private method setupCard adds four student to the card as per rules of the setup of the card */
    private void setupCard(){
        for(int i=0; i<4; i++){
            students.addStudent(board.getStudentBag().drawStudent());
        }
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
