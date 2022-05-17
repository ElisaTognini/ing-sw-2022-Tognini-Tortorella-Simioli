package it.polimi.ingsw.Model.BasicElements;

import it.polimi.ingsw.Model.Player;

public final class AssistantCard implements Comparable{

    int cardValue; //card's power factor
    int motherNatureMovements;
    private Player owner;

    public AssistantCard(int cardValue, int motherNatureMovements, Player owner){
        this.cardValue = cardValue;
        this.motherNatureMovements = motherNatureMovements;
        this.owner = owner;
    }

    public int getAssistantCardID(){
        return cardValue;
    }

    public int getMotherNatureMovements(){
        return motherNatureMovements;
    }

    /*two assistant cards are to be considered equal if their power factor is equal*/
    @Override
    public boolean equals (Object other){
        if(other == null || other.getClass() != this.getClass()) return false;
        else return ((AssistantCard) other).cardValue == this.cardValue;
    }

    @Override
    public int compareTo(Object o) {
        AssistantCard o1 = (AssistantCard) o;
        return this.cardValue - o1.cardValue;
    }

    @Override
    /* FORMAT owner id MNmovements */
    public String toString(){
        return owner.getNickname() + " " + cardValue + " " + motherNatureMovements;
    }

    public Player getOwner(){
        return owner;
    }

}
