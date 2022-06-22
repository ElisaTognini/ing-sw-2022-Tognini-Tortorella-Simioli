package it.polimi.ingsw.Model.Expert;
import it.polimi.ingsw.Model.BoardClasses.BoardExpert;
import it.polimi.ingsw.Model.SchoolBoardClasses.SchoolBoard;
import it.polimi.ingsw.Model.StudentContainer;
import it.polimi.ingsw.Utils.Enums.PawnDiscColor;

/** Class Card11 represents one of the twelve character cards: it contains a brief description of the effect of the card
 * as well as a reference to a student container to store the students which are actually on this card. */

public class Card11 extends CharacterCardTemplate{

    private final String description = "take 1 student from this card and place it in your dining room.";
    private StudentContainer students;


    /** Constructor Card11 creates a new instance of this character card, assigning its ID and cost,
     * initializing a new student container and sets up the card.
     *
     * @param board of type BoardExpert - board */
    public Card11(BoardExpert board){
        super(board);
        cardID = 11;
        cost = 2;
        students = new StudentContainer();
        setupCard();
    }


    /** Method use card takes the parameter passed by the player inside the parameter o, the student chosen from the card,
     * retrieves it from the card and the adds it to the dining room, then refills the student on the card. If the
     * bag is empty, a new student is not to be added to the card because it means it is the last round.
     *
     * @param o of type Object - the parameters passed by the player
     * @param nickname of type String - the nickname of the player */
    public void useCard(Object o, String nickname){
        Parameter parameters;
        SchoolBoard sb = board.getPlayerSchoolBoard(nickname);


        if(o instanceof Parameter){
            parameters = (Parameter)o;
        }
        else throw new IllegalArgumentException();

        sb.getDiningRoom().addStudent(students.retrieveStudent(parameters.getColor()));
        if(board.getStudentBag().checkIfStudentBagEmpty()){
            board.setLastRound();
        }
        else
            students.addStudent(board.getStudentBag().drawStudent());
    }


    /** Private method setupCard adds four student to the card as per rules of the setup of the card */
    private void setupCard() {
        for(int i=0; i<4; i++){
            students.addStudent(board.getStudentBag().drawStudent());
        }
    }


    /** Method checkIfActionIsForbidden checks if at least a student of the chosen color is available on the card and
     * if there's room available in the dining room for the student of the selected color.
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
                return board.getPlayerSchoolBoard(nickname).getDiningRoom().checkIfDiningRoomIsFull(parameters.getColor());
            }
        }
        return true;
    }


    /** Method toStringCard builds a String containing all the info stored in this class
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
