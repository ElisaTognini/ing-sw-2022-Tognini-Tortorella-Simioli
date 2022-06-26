package it.polimi.ingsw.Model.BasicElements;
import it.polimi.ingsw.Model.Player;

import java.util.*;

/** Class AssistantCardDeck contains all the ten assistant card needed to play the game and a reference to the player
* who owns said cards. */

public class AssistantCardDeck {
    private Player owner;
    private ArrayList<AssistantCard> cards;

    /** Constructor AssistantCardDeck creates a new instance of a deck
     *
     * @param owner - of type Player - the player who owns the deck */
    public AssistantCardDeck(Player owner){
        this.owner = owner;
        cards = new ArrayList<>();
        this.instantiateCards();
    }


    /** Private method instantiateCards sets up the deck for each player: it creates one new Assistant Card
     * for each of card ID, from 1 to 10, for each player.
     * The pattern for IDs and power factor is portrayed by the two loops : mother nature movements increase
     * by one unit every two cards */
    private void instantiateCards(){
        int k = 1;
        int g = 0;
        for(int i=1; i<=5; i++){
            for(int j=k; j<k+2; j++){
                cards.add(new AssistantCard(j, i, owner));
                g = j;
            }
            k = g+1;
        }
    }


    /** Method drawCard, called when the player in turn picks an assistant card, checks if the card given in input
     * is inside the deck and, if so, returns a reference to the selected card
     *
     * @param cardID - of type int - the card ID of the card chosen by the player
     * @return AssistantCard - the reference to the actual card with corresponding power factor; otherwise
     *      it returns null if the card was not found or the given ID was invalid
     * @see AssistantCard
     */
    public AssistantCard drawCard(int cardID){
        AssistantCard toRet;
        for(AssistantCard c : cards){
            if (c.getAssistantCardID() == cardID){
                toRet = cards.get(cards.indexOf(c));
                return toRet;
            }
        }
        return null;
    }


    /** Method removeCard removes the reference to the assistant card with the same power factor as the one given
     * in input from the deck
     *
     * @param cardID - of type int the card ID of the card picked by the player*/
    public void removeCard(int cardID){
        for(AssistantCard c : cards){
            if (c.getAssistantCardID() == cardID) {
                cards.remove(c);
                break;
            }
        }
    }


    /** getter method - getOwner returns the Player who owns the deck
     *
     * @return Player - the owner of the deck */
    public Player getOwner(){
        return owner;
    }


    /** Method checkIfCardIsPresent checks if the card picked by a player is inside their deck
    *
    * @param cardID - of type int - the card ID of the card selected by the player
    * @return boolean - the result of the check, true if the card is indeed present, otherwise false */
    public boolean checkIfCardIsPresent(int cardID) {
        for (AssistantCard c : cards) {
            if (c.getAssistantCardID() == cardID) return true;
        }
        return false;
    }


    /** Method checkIfDeckIsEmpty checks if the ArrayList containing the assistant cards is empty
    *
    * @return boolean - the result of this check: true if the size is 0, false otherwise */

    public boolean checkIfDeckIsEmpty(){
        if(cards.size() == 0) return true;
        else return false;
    }

    /** Method size returns the size of the deck
     *
     * @return int - size of cards ArrayList
     */

    public int size(){
        return cards.size();
    }

    /** Method toString builds a String containing all the info stored in this class
     *
     * @return String - FORMAT owner and list of present cardIDs with their power factor,
     * each separated by a blank space */
    @Override
    public String toString(){
        StringBuilder stringbuilder = new StringBuilder();

        stringbuilder.append(owner.getNickname()).append(" ");

        for(AssistantCard card : cards){
            stringbuilder.append(card.getAssistantCardID()).append(" ");
            stringbuilder.append(card.getMotherNatureMovements()).append(" ");
        }
        stringbuilder.deleteCharAt(stringbuilder.toString().length() - 1);
        return stringbuilder.toString();
    }
}
