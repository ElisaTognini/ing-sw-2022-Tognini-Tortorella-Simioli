package it.polimi.ingsw.BasicElements;

//import it.polimi.ingsw.Enums.Wizards;

public final class AssistantCard {

    int cardValue; //card's power factor
    int motherNatureMovements;
    //Wizards wizard;

    public AssistantCard(int cardValue, int motherNatureMovements /*, Wizards wizard*/){
        this.cardValue = cardValue;
        this.motherNatureMovements = motherNatureMovements;
        //this.wizard = wizard;
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

}
