package it.polimi.ingsw.BasicElements;

import it.polimi.ingsw.Player;

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

    public Player getOwner(){
        return owner;
    }
}
