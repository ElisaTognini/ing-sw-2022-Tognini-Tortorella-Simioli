package it.polimi.ingsw.Model.Expert;
import it.polimi.ingsw.Model.BoardClasses.BoardExpert;

/** Class Card5 represents one of the twelve character cards: it contains a brief description of the effect of the card.
 * */

public class Card5 extends CharacterCardTemplate{

    private final String description = "place no entry tiles on island of choice in order to block" +
            " influence calculation if mother nature ends there";


    /** Constructor Card5 creates a new instance of this character card, assigning its ID and cost.
     *
     * @param board of type BoardExpert - board */
    public Card5(BoardExpert board){
        super(board);
        cardID = 5;
        cost = 2;
    }


    /** Method useCard takes the parameter passed by the player inside the parameter o, the island ID, and uses a no
     * entry tile on that island.
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

        board.getIslandList().get(parameters.getIslandID()).addNoEntryTile();
        board.useNoEntryTile();
    }


    /** Method checkIfActionIsForbidden checks if the island ID provided by the player is a valid one and if there are
     * enough no entry tiles on the board to use.
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

        if(parameters.getIslandID() < 0 || parameters.getIslandID() > (board.getIslandList().size() - 1))
            return true;

        return !board.checkIfEnoughNoEntryTiles();
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
