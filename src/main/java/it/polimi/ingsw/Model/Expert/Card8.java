package it.polimi.ingsw.Model.Expert;

import it.polimi.ingsw.Model.BoardClasses.BoardExpert;

/* adds 2 to the influence of the player who purchased this card */

public class Card8 extends CharacterCardTemplate{

    private final String description = "adds 2 to the influence of the owner of this card";

    public Card8(BoardExpert board){
        super(board);
        cardID = 8;
        cost = 2;
    }

    public void useCard(Object o, String nickname){
        Parameter parameters;

        if(o instanceof Parameter){
            parameters = (Parameter)o;
        }
        else throw new IllegalArgumentException();

        board.setExtra(nickname);
    }

    @Override
    public boolean checkIfActionIsForbidden(Object o, String nickname) throws IllegalArgumentException {
        return false;
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
