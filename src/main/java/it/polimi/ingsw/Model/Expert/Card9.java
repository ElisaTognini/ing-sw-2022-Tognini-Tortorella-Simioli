package it.polimi.ingsw.Model.Expert;
import it.polimi.ingsw.Model.BoardClasses.BoardExpert;

/** Class Card9 represents one of the twelve character cards: it contains a brief description of the effect of the card.
 * */

public class Card9 extends CharacterCardTemplate{

    private final String description = "choose a color of student that will add no influence" +
            " during the influence calculation of the turn in which the card is played";


    /** Constructor Card9 creates a new instance of this character card, assigning its ID and cost.
     *
     * @param board of type BoardExpert - board */
    public Card9(BoardExpert board){
        super(board);
        cardID = 9;
        cost = 3;
    }

    /** Method useCard takes the parameter passed by the player inside the parameter o, the color of the students to be
     * excluded from the calculation of the influence, and calls method setIgnoredInfluence of class board for that color.
     *
     *
     * @param o of type Object - the parameters passed by the player
     * @param nickname of type String - the nickname of the player.
     * */
    public void useCard(Object o, String nickname){
        Parameter parameters;

        if(o instanceof Parameter){
            parameters = (Parameter)o;
        }
        else throw new IllegalArgumentException();

        board.setIgnoredInfluence(parameters.getColor());
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
