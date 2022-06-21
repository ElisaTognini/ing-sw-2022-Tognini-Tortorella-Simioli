package it.polimi.ingsw.Model.Expert;
import it.polimi.ingsw.Model.BoardClasses.BoardExpert;
import it.polimi.ingsw.Utils.Enums.PawnDiscColor;
import it.polimi.ingsw.Model.SchoolBoardClasses.SchoolBoard;

/** Class Card2 represents one of the twelve character cards: it contains a brief description of the effect of the card.
 * */

public class Card2 extends CharacterCardTemplate{

    private final String description = "take control of any number of professors for the turn";

    /** Constructor Card2 creates a new instance of this character card, assigning its ID and cost.
     *
     * @param board of type BoardExpert - board */
    public Card2(BoardExpert board){
        super(board);
        cardID = 2;
        cost = 2;
    }

    /** Method useCard takes the parameter passed by the player inside the parameter o, the color of the professor(s)
     * the player wants to get control of, saves the previous state of the player's professor table; finally modifies
     * the professor table according to the player's choices and sets true as value of attribute modifiedTable in
     * ProfessorTable.
     *
     *
     * @param o of type Object - the parameters passed by the player
     * @param nickname of type String - the nickname of the player */
    public void useCard(Object o, String nickname){
        Parameter parameters;
        SchoolBoard sb;

        if(o instanceof Parameter){
            parameters = (Parameter)o;
        }
        else throw new IllegalArgumentException();

        sb = board.getPlayerSchoolBoard(nickname);
        sb.getProfessorTable().saveProfessors();

        for(PawnDiscColor c: parameters.getColorArrayList()){
                sb.getProfessorTable().addProfessor(c);
        }
        sb.setModifiedTable();
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
        return cardID + "-" + cost + "-" + this.getDescription() ;
    }


    /**getter method - Method getDescription returns the description of the effect of the card
     *
     * @return String - the description of the character card */
    @Override
    public String getDescription(){
        return description;
    }

}
