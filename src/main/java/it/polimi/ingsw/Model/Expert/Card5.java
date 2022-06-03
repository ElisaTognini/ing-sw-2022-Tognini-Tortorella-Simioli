package it.polimi.ingsw.Model.Expert;

import it.polimi.ingsw.Model.BoardClasses.BoardExpert;

/* this character card places no entry tiles on island of choice in order to block
* influence calculation if mother nature ends there */

public class Card5 extends CharacterCardTemplate{

    private final String description = "place no entry tiles on island of choice in order to block" +
            " influence calculation if mother nature ends there";

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
        Parameter parameters;

        if(o instanceof Parameter){
            parameters = (Parameter)o;
        }
        else throw new IllegalArgumentException();

        if(parameters.getIslandID() < 0 || parameters.getIslandID() > (board.getIslandList().size() - 1))
            return true;

        return !board.checkIfEnoughNoEntryTiles();
    }

    @Override
    public String toStringCard(){
        return String.valueOf(cardID) + "-" + String.valueOf(cost) + "-" + this.getDescription();
    }

    @Override
    public String getDescription(){
        return description;
    }


}
