package it.polimi.ingsw.Model.Expert;
import it.polimi.ingsw.Model.BoardClasses.BoardExpert;
import it.polimi.ingsw.Model.SchoolBoardClasses.SchoolBoard;

/** Class Card12 represents one of the twelve character cards: it contains a brief description of the effect of the card.
 *  */

public class Card12 extends CharacterCardTemplate{

    private final String description = "choose a type of student: every player (including yourself) must return 3 students of that type" +
            "  from their dining room to the bag.\n If any player has fewer than 3 students of that type," +
            "  return as many students as they have";


    /** Constructor Card12 creates a new instance of this character card, assigning its ID and cost.
     *
     * @param board of type BoardExpert - board */
    public Card12(BoardExpert board){
        super(board);
        cardID = 12;
        cost = 3;
    }


    /** Method use card takes the parameter passed by the player inside the parameter o, the color of the students,
     * and, for each player, gets the minimum between three and the actual number of students of that color
     * inside the dining room. Finally, it retrieves as many students of said color as bound says from the dining room
     * and puts them back inside the dining room.
     *
     * @param o of type Object - the parameters passed by the player
     * @param nickname of type String - the nickname of the player */
    public void useCard(Object o, String nickname){
        Parameter parameters;
        int bound;

        if(o instanceof Parameter){
            parameters = (Parameter)o;
        }
        else throw new IllegalArgumentException();

        for(SchoolBoard sb : board.getSchoolBoards()){

            bound = Math.min(sb.getDiningRoom().influenceForProf(parameters.getColor()), 3);

            for(int i = 0; i < bound; i++){
                board.getStudentBag().addStudentBack(sb.getDiningRoom().getContainer().retrieveStudent(parameters.getColor()));
            }
        }
    }


    /** Method checkIfActionIsForbidden always returns false because action is never forbidden
     *
     * @param o of type Object - the parameters passed by the player
     * @param nickname of type String - the player's nickname.
     *
     * @return boolean
     */
    @Override
    public boolean checkIfActionIsForbidden(Object o, String nickname) throws IllegalArgumentException {
        return false;
    }


    /** Method toStringCard builds a String containing all the info stored in this class
     *
     *  @return String - FORMAT: cardID, cost and description of the effect of the card, each separated by a "-" .
     *  */
    @Override
    public String toStringCard(){
        return cardID + "-" + cost + "-" + this.getDescription();
    }


    /**getter method - Method getDescription returns the description of the effect of the card
     *
     * @return String - the description of the character card */
    @Override
    public String getDescription(){
        return description;
    }

}
