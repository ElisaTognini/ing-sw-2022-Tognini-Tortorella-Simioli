package it.polimi.ingsw.Client.ActionMessages;

import java.io.Serializable;

/**
 * Class PickCloudMessage is a message sent from the client to the server,
 * informing it of the id of the cloud that the player has picked.
 *
 * @see java.io.Serializable
 * */
public class PickCloudMessage implements Serializable {

    private int cloudID;

    /**
     * Constructor PickCloudMessage creates a new PickCloudMessage instance.
     *
     * @param cloudID - of type int - id of picked cloud.
     * */
    public PickCloudMessage(int cloudID){
        this.cloudID = cloudID;
    }

    /**
     * getter method getCloudID returns the id of the picked cloud.
     *
     * @return int - id of the cloud.
     * */
    public int getCloudID(){
        return cloudID;
    }
}
