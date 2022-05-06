package it.polimi.ingsw.Expert;

/* this card allows to choose a color of student that will add no influence
*  during the influence calculation of the turn in which the card is played */

import it.polimi.ingsw.BoardClasses.Board;
import it.polimi.ingsw.BoardClasses.BoardExpert;
import it.polimi.ingsw.Enums.PawnDiscColor;

public class Card9 extends CharacterCardTemplate{

    public Card9(BoardExpert board){
        super(board);
        cardID = 9;
        cost = 3;
    }
    public void useCard(Object o, String nickname){
        Parameter parameters;

        if(o instanceof Parameter){
            parameters = (Parameter)o;
        }
        else throw new IllegalArgumentException();

        board.getIslandList().get(board.getMotherNaturePosition()).ignoreInfluence(parameters.getColor());
    }

    @Override
    public boolean checkIfActionIsForbidden(Object o, String nickname) throws IllegalArgumentException {
        return false;
    }

}