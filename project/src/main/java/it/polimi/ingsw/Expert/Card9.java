package it.polimi.ingsw.Expert;

/* this card allows to choose a color of student that will add no influence
*  during the influence calculation of the turn in which the card is played */

import it.polimi.ingsw.BoardClasses.Board;
import it.polimi.ingsw.Enums.PawnDiscColor;

public class Card9 extends CharacterCardTemplate{

    public Card9(Board board){
        super(board);
        cardID = 9;
        cost = 3;
    }

    public void useCard(PawnDiscColor color, int islandID){
        board.getIslandList().get(islandID).ignoreInfluence(color);
    }

}
