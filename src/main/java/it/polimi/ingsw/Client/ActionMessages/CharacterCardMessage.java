package it.polimi.ingsw.Client.ActionMessages;

import it.polimi.ingsw.Model.Expert.Parameter;

public class CharacterCardMessage {

    Parameter param;
    int cardID;

    public void setCardID(int cardID) {
        this.cardID = cardID;
    }

    public Parameter getParam() {
        return param;
    }

    public int getCardID() {
        return cardID;
    }
}
