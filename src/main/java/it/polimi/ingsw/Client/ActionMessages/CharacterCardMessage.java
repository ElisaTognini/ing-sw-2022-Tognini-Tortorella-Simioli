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

    /** setter method - setCardID sets the ID of the picked card.
     *
     * @param cardID - of type int - ID that identifies the card. */
    public void setCardID(int cardID) {
        this.cardID = cardID;
    }

    /**
     * setter method - setParam sets the parameter needed to use the card.
     *
     * @param param - of type Param - parameter.
     * @see Parameter
     * */
    public void setParam(Parameter param) {
        this.param = param;
    }

    /**
     * getter method - getParam gets the parameter that was set to this card.
     *
     * @return Parameter - parameter.
     * @see Parameter
     * */
    public Parameter getParam() {
        return param;
    }

    /**
     * getter method - getCardID returns the ID of this card.
     *
     * @return int - cardID.
     * */
    public int getCardID() {
        return cardID;
    }
}
