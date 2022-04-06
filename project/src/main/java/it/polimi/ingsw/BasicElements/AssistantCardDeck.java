package it.polimi.ingsw.BasicElements;
import it.polimi.ingsw.*;

import java.util.*;

public class AssistantCardDeck {
    private Player owner;
    private ArrayList<AssistantCard> cards;
    private final int cardNumber = 10;

    public AssistantCardDeck(Player owner){
        this.owner = owner;
        cards = new ArrayList<AssistantCard>();
        this.instantiateCards();
    }

    /*the card IDs go from 1 to 10 for each wizard:
    * the pattern for IDs and power factor is portrayed by the two loops*/
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

    /*this method is called when player in turn picks an assistant card
    * it removes the card from the deck and returns a reference to the chosen card;
    * it throws an InvalidCardActionException if the loop ever ends, which means either the card was not found or the
    * given ID is invalid*/
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

    public void removeCard(int cardID){
        for(AssistantCard c : cards){
            if (c.getAssistantCardID() == cardID) {
                cards.remove(cards.indexOf(c));
            }
        }
    }

    /*returns owner*/
    public Player getOwner(){
        return owner;
    }

    /* checks if a card the player wants to play is inside their deck*/
    public boolean checkIfCardIsPresent(int cardID) {
        for (AssistantCard c : cards) {
            if (c.getAssistantCardID() == cardID) return true;
        }
        return false;
    }

    public boolean checkIfDeckIsEmpty(){
        if(cards.size() == 0) return true;
        else return false;
    }
}
