package it.polimi.ingsw.Client.ActionMessages;

import it.polimi.ingsw.Model.Expert.Parameter;

import java.io.Serializable;

/**
 * Class CharacterCardMessage is a message sent from the client to the server,
 * informing it of the chosen character card.
 *
 * @see java.io.Serializable
 * */

public class CharacterCardMessage implements Serializable {

    Parameter param;
    int cardID;

    public void setCardID(int cardID) {
        this.cardID = cardID;
    }

    public void setParam(Parameter param) {
        this.param = param;
    }

    public Parameter getParam() {
        return param;
    }

    public int getCardID() {
        return cardID;
    }
}
