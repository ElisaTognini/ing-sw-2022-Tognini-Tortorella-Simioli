package it.polimi.ingsw.Model.BasicElements;

import it.polimi.ingsw.Model.Player;

/*AssistantCard contains the power factor of the card and the number of movements Mother Nature can perform
* when the card is played.*/

public final class AssistantCard implements Comparable{

    int cardValue;
    int motherNatureMovements;
    private Player owner;

    /** Constructor AssistantCard creates a new instance of an assistant card
     *
     * @param cardValue - of type int - the number identifying the card
     * @param motherNatureMovements - of type int - the power factor of the card
     * @param owner - of type Player - the player who owns the card */
    public AssistantCard(int cardValue, int motherNatureMovements, Player owner){
        this.cardValue = cardValue;
        this.motherNatureMovements = motherNatureMovements;
        this.owner = owner;
    }


    /** getter method - getAssistantCardID returns the power factor of the card played.
     *
    * @return Integer */
    public int getAssistantCardID(){
        return cardValue;
    }


    /** getter method - getMotherNatureMovements returns the movements Mother Nature can perform, based on
     * the card played.
     *
     * @return Integer */
    public int getMotherNatureMovements(){
        return motherNatureMovements;
    }


    /** getter method - getOwner returns the Player who played the Assistant Card
    *
    * @return Player - the owner of the card*/
    public Player getOwner(){
        return owner;
    }


    /** Method equals compares two cards in order to see if they have the same power factor
    *
    * @param other - of type Object - in this case, in order for the method to return correctly, it is an AssistantCard chosen
    * by the player, otherwise it returns false by default
    * @return boolean - true if the power factor match, false otherwise */
    @Override
    public boolean equals (Object other){
        if(other == null || other.getClass() != this.getClass()) return false;
        else return ((AssistantCard) other).cardValue == this.cardValue;
    }


    /** Method compareTo compares the power factor of two cards in order to determine which one is higher
    *
    * @param o - of type Object - the assistant card played (and stored) by the player
    * @return Integer - negative or positive, depending on the result of the comparison */
    @Override
    public int compareTo(Object o) {
        AssistantCard o1 = (AssistantCard) o;
        return this.cardValue - o1.cardValue;
    }


    /** Method toString builds a String containing all the info stored in this class
    *
    * @return String - FORMAT: owner, id and Mother Nature movements, each separated by a blank space */
    @Override
    public String toString(){
        return owner.getNickname() + " " + cardValue + " " + motherNatureMovements;
    }

}
