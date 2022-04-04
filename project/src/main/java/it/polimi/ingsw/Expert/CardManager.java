package it.polimi.ingsw.Expert;

import it.polimi.ingsw.BoardClasses.Board;
import it.polimi.ingsw.TailoredExceptions.EmptyException;
import it.polimi.ingsw.TailoredExceptions.InvalidCardActionException;

/* factory class */

public class CardManager {

    private Board board;
    public CardManager(Board board){
        this.board = board;
    }

    public CharacterCardTemplate returnCard(int cardID) throws EmptyException, InvalidCardActionException {
        switch(cardID){
            case 1:
                return new Card1(board);
            default:
                throw new InvalidCardActionException();
        }
    }
}
