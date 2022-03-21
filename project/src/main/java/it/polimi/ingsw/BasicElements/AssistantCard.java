package it.polimi.ingsw.BasicElements;

import it.polimi.ingsw.Enums.Wizards;

public final class AssistantCard {

    int cardValue;
    int motherNatureMovements;
    Wizards wizard;

    public AssistantCard(int cardValue, int motherNatureMovements, Wizards wizard){
        this.cardValue = cardValue;
        this.motherNatureMovements = motherNatureMovements;
        this.wizard = wizard;
    }

}
