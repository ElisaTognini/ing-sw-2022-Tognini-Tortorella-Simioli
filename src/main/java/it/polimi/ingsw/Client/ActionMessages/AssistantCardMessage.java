package it.polimi.ingsw.Client.ActionMessages;

import java.io.Serializable;

/**
 * Class AssistantCardMessage is a message sent from the client to the server,
 * informing it of the chosen assistant card.
 *
 * @see java.io.Serializable
 * */

public class AssistantCardMessage implements Serializable {

    int pickedCardID;

    /**
     * Constructor AssistantCardMessage creates a new AssistantCardMessage instance.
     *
     * @param cardID - of type int - assistant card ID reference.
     * */

    public AssistantCardMessage(int cardID){
        this.pickedCardID = cardID;
    }

    /**
     * Getter method getPickedCardID returns the card ID of this AssistantCardMessage object.
     *
     * @return the card ID (type int) of this AssistantCardMessage object.
     * */

    public int getPickedCardID(){
        return pickedCardID;
    }
}
