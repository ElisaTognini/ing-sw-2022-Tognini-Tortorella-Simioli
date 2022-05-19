package it.polimi.ingsw.Client.ActionMessages;

import java.io.Serializable;

public class AssistantCardMessage implements Serializable {

    int pickedCardID;

    public AssistantCardMessage(int cardID){
        this.pickedCardID = cardID;
    }

    public int getPickedCardID(){
        return pickedCardID;
    }
}
