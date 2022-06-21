package it.polimi.ingsw.Model.Expert;

import it.polimi.ingsw.Model.BoardClasses.BoardExpert;

/** Class cardManager is a factory class which creates a new instance of the corresponding character card, based on
 * the card ID provided */

public class CardManager {

    private BoardExpert board;

    /** Constructor CardManager creates a new instance of card manager
     *
     * @param board of type BoardExpert - board*/
    public CardManager(BoardExpert board){
        this.board = board;
    }

    /** Method returnCard returns new instance of the character card associated to the ID given as parameter
     *
     * @param cardID of type int - character card ID
     *
     * @return CharacterCardTemplate - new character card */
    public CharacterCardTemplate returnCard(int cardID){
        switch(cardID){
            case 1:
                return new Card1(board);
            case 2:
                return new Card2(board);
            case 3:
                return new Card3(board);
            case 4:
                return new Card4(board);
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
        }

        return null;
    }
}
