package it.polimi.ingsw.Model.Expert;
import it.polimi.ingsw.Model.BoardClasses.BoardExpert;

/** Class Card4 represents one of the twelve character cards: it contains a brief description of the effect of the card.
 * */

public class Card4 extends CharacterCardTemplate{

    private final String description = "mother nature can be moved up to two additional islands";

    /** Constructor Card4 creates a new instance of this character card, assigning its ID and cost.
     *
     * @param board of type BoardExpert - board */
    public Card4(BoardExpert board) {
        super(board);
        cardID = 4;
        cost = 1;
    }


    /** Method useCard takes the parameter passed by the player inside the parameter o, the number of additional moves,
     *  and sets it as value of attribute additionalMoves in board.
     *
     *
     * @param o of type Object - the parameters passed by the player
     * @param nickname of type String - the nickname of the player.
     * */
    public void useCard(Object o, String nickname) {
        Parameter parameters;

        if (o instanceof Parameter) {
            parameters = (Parameter) o;
        } else throw new IllegalArgumentException();
        board.setAdditionalMoves(parameters.getMoves());
    }


    /** Method checkIfActionIsForbidden checks if the value provided by the player is a valid one.
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
            parameters = (Parameter) o;
        }
        else throw  new IllegalArgumentException();
        return parameters.getMoves() > 2 || parameters.getMoves() < 0;
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
