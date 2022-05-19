package it.polimi.ingsw.Model.Expert;

import it.polimi.ingsw.Model.BoardClasses.BoardExpert;

/* this card allows the current player to move mn up to two additional islands */

public class Card4 extends CharacterCardTemplate{

    public Card4(BoardExpert board) {
        super(board);
        cardID = 4;
        cost = 1;
    }

    public void useCard(Object o, String nickname) {
        Parameter parameters;

        if (o instanceof Parameter) {
            parameters = (Parameter) o;
        } else throw new IllegalArgumentException();
        board.setAdditionalMoves(parameters.getMoves());
    }

    @Override
    public boolean checkIfActionIsForbidden(Object o, String nickname) throws IllegalArgumentException {
        Parameter parameters;
        if(o instanceof Parameter){
            parameters = (Parameter) o;
        }
        else throw  new IllegalArgumentException();
        if(parameters.getMoves() > 2 || parameters.getMoves() < 0){
            return true;
        }
        return false;
    }

    @Override
    public String toStringCard(){
        String toRet = String.valueOf(cardID) + " " + String.valueOf(cost);
        return toRet;
    }


}
