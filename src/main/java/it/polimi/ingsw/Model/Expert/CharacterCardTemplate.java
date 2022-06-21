package it.polimi.ingsw.Model.Expert;
import it.polimi.ingsw.Model.BoardClasses.BoardExpert;

/** Abstract class CharacterCardTemplate is the super class of all the character cards from which the actual card classes
 * inherit the common methods they override. */

public abstract class CharacterCardTemplate {
    protected int cost;
    protected int cardID;
    protected BoardExpert board;

    /** Constructor CharacterCardTemplate creates a new instance of character card template
     *
     * @param board of type BoardExpert - board */
    public CharacterCardTemplate(BoardExpert board){
        this.board = board;
    }


    /** Method increaseCost increase value of attribute cost by one unit*/
    public void increaseCost(){
        cost++;
    }


    /** getter method - Method getCost returns value of attribute cost
     *
     * @return int - cost */
    public int getCost(){
        return cost;
    }


    /** getter method - Method getCardID returns value of attribute cardID
     *
     * @return int - id of the character card */
    public int getCardID(){
        return cardID;
    }


    /** Method useCard allows the usage of the card, and it is overridden in cards that need parameters.
     *
     * @param o of type Object - the parameters passed by the player
     * @param nickname of type String - the nickname of the player */
    public abstract void useCard(Object o, String nickname) throws IllegalArgumentException;


    /** Method checkIfActionIsForbidden checks if the parameters passed by the player in input are valid and the
     * action can be pursued.
     *
     * @param o of type Object - the parameters passed by the player
     * @param nickname of type String - the player's nickname.
     *
     * @return boolean - true if action is forbidden, false otherwise
     * */
    public abstract boolean checkIfActionIsForbidden(Object o, String nickname) throws IllegalArgumentException;


    /** Method toStringCard builds a String containing all the info stored in this class
    *
    *  @return String */
    public abstract String toStringCard();


    /**getter method - Method getDescription returns the description of the effect of the card
     *
     * @return String - the description of the character card */
    public abstract String getDescription();
}
