package it.polimi.ingsw.Expert;

import it.polimi.ingsw.BoardClasses.BoardExpert;

/* this character card places no entry tiles on islands of choice in order to block
* influence calculation if mother nature ends there */

public class Card5 extends CharacterCardTemplate{

    public Card5(BoardExpert board){
        super(board);
        cardID = 5;
        cost = 2;
    }

    public void useCard(Object o, String nickname){
        Parameter parameters;


        if(o instanceof Parameter){
            parameters = (Parameter)o;
        }
        else throw new IllegalArgumentException();

        board.getIslandList().get(parameters.getIslandID()).addNoEntryTile();
        board.useNoEntryTile();
    }

    @Override
    public boolean checkIfActionIsForbidden(Object o, String nickname) throws IllegalArgumentException {
        return !board.checkIfEnoughNoEntryTiles();
    }

}
