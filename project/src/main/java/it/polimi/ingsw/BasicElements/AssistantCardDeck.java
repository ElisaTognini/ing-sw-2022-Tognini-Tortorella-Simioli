package it.polimi.ingsw.BasicElements;
import it.polimi.ingsw.*;
import it.polimi.ingsw.TailoredExceptions.*;

import java.util.*;

public class AssistantCardDeck {
    private Player owner;
    private ArrayList<AssistantCard> cards;

    public AssistantCardDeck(){
        cards = new ArrayList<AssistantCard>();
    }

    /*the card IDs go from 1 to 10 for each wizard:
    * This class uses a JSON file to instantiate the assistant cards in an automated way*/
    private void instantiateCards(){

    }

    /*this method is called when player in turn picks an assistant card
    * it removes the card from the deck and returns a reference to the chosen card;
    * it throws an InvalidCardActionException if the loop ever ends, which means either the card was not found or the
    * given ID is invalid*/
    public AssistantCard drawCard(int cardID) throws InvalidCardActionException{
        AssistantCard toRet;
        for(AssistantCard c : cards){
            if (c.getAssistantCardID() == cardID){
                toRet = cards.get(cards.indexOf(c));
                cards.remove(cards.indexOf(c));
                return toRet;
            }
        }
        throw new InvalidCardActionException();
    }
}
