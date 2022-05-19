package it.polimi.ingsw.Model.Expert;

import it.polimi.ingsw.Model.BoardClasses.BoardExpert;

/* when resolving Conquering on an island, Towers do not count towards influence */

public class Card6 extends CharacterCardTemplate{

    public Card6(BoardExpert board){
        super(board);
        cardID = 6;
        cost = 3;
    }

    public void useCard(Object o, String nickname){
        Parameter parameters;
        int temp = 0;

        if(o instanceof Parameter){
            parameters = (Parameter)o;
        }
        else throw new IllegalArgumentException();

        temp = board.getIslandList().get(parameters.getIslandID()).getNumberOfTowers();
        board.getIslandList().get(parameters.getIslandID()).setTowersOnHold(temp);
    }

    @Override
    public boolean checkIfActionIsForbidden(Object o, String nickname) throws IllegalArgumentException {
        return false;
    }

    @Override
    public String toStringCard(){
        String toRet = String.valueOf(cardID) + " " + String.valueOf(cost);
        return toRet;
    }


}
