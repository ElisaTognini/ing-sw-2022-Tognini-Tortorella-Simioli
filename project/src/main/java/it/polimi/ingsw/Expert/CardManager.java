package it.polimi.ingsw.Expert;

import it.polimi.ingsw.BoardClasses.Board;
import it.polimi.ingsw.BoardClasses.BoardExpert;
import it.polimi.ingsw.TailoredExceptions.EmptyException;
import it.polimi.ingsw.TailoredExceptions.InvalidCardActionException;

/* factory class */

public class CardManager {

    private BoardExpert board;
    public CardManager(BoardExpert board){
        this.board = board;
    }

    public CharacterCardTemplate returnCard(int cardID) throws EmptyException, InvalidCardActionException {
        switch(cardID){
            case 1:
                return new Card1(board);
            case 2:
                return new Card2(board);
            case 3:
                //return new Card3(board);
            case 4:
                //return new Card4(board);
            case 5:
                return new Card5(board);
            case 6:
                return new Card6(board);
            case 7:
                return new Card7(board);
            case 8:
                return new Card8(board);
            case 9:
                return new Card9(board);
            case 10:
                return new Card10(board);
            case 11:
                return new Card11(board);
            case 12:
                return new Card12(board);
            default:
                throw new InvalidCardActionException();
        }
    }
}
