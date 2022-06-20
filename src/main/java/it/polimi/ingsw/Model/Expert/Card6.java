package it.polimi.ingsw.Model.Expert;

import it.polimi.ingsw.Model.BoardClasses.BoardExpert;

/** Class Card6 represents one of the twelve character cards: it contains a brief description of the effect of the card.
 * */

public class Card6 extends CharacterCardTemplate{

    private final String description = "when resolving Conquering on an island, towers do not count towards influence";


    /** Constructor Card5 creates a new instance of this character card, assigning its ID and cost.
     *
     * @param board of type BoardExpert - board */
    public Card6(BoardExpert board){
        super(board);
        cardID = 6;
        cost = 3;
    }

    /** Method useCard takes the parameter passed by the player inside the parameter o, the island ID, gets the number
     * of towers present on that island and sets it as value of attribute towersOnHold in board.
     *
     *
     * @param o of type Object - the parameters passed by the player
     * @param nickname of type String - the nickname of the player.
     * */
    public void useCard(Object o, String nickname){
        Parameter parameters;
        int temp = 0;

        if(o instanceof Parameter){
            parameters = (Parameter)o;
        }
        else throw new IllegalArgumentException();

        temp = board.getIslandList().get(parameters.getIslandID()).getNumberOfTowers();
        board.getIslandList().get(parameters.getIslandID()).setTowersOnHold(temp);
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


    /** Method toString builds a String containing all the info stored in this class
     *
     *  @return String - FORMAT: cardID, cost and description of the effect of the card, each separated by a "-" .
     *  */
    @Override
    public String toStringCard(){
        return String.valueOf(cardID) + "-" + String.valueOf(cost) + "-" + this.getDescription();
    }


    /**getter method - Method getDescription returns the description of the effect of the card
     *
     * @return String - the description of the character card */
    @Override
    public String getDescription(){
        return description;
    }

}
