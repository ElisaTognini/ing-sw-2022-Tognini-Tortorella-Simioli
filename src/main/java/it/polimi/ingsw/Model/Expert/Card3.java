package it.polimi.ingsw.Model.Expert;
import it.polimi.ingsw.Model.BoardClasses.BoardExpert;

/** Class Card3 represents one of the twelve character cards: it contains a brief description of the effect of the card.
 * */

public class Card3 extends CharacterCardTemplate{

    private final String description = "choose an island and resolve it as if mother nature had stopped there; " +
            "mother nature will still move as per normal game rules.";


    /** Constructor Card3 creates a new instance of this character card, assigning its ID and cost.
     *
     * @param board of type BoardExpert - board */
    public Card3(BoardExpert board) {
        super(board);
        cost = 3;
        cardID = 3;
    }


    /** Method useCard saves the previous position on mother nature, takes the parameter passed by the player inside
     * the parameter o, the island ID, changes mother nature position and forces the board to resolve the island.
     * After that, the position of mother nature goes back to its previous value (which ensures the turn proceeds as
     * normal).
     *
     *
     * @param o of type Object - the parameters passed by the player
     * @param nickname of type String - the nickname of the player.
     * */
    @Override
    public void useCard(Object o, String nickname){
        Parameter parameters;
        int oldPos;

        if(o instanceof Parameter){
            parameters = (Parameter)o;
        }
        else throw new IllegalArgumentException();

        oldPos = board.getMotherNaturePosition();
        board.setMotherNaturePosition(parameters.getIslandID());
        board.assignProfessors();
        board.conquerIsland();
        board.setMotherNaturePosition(oldPos);
    }


    /** Method checkIfActionIsForbidden checks if the island ID provided by the player is a valid one.
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
