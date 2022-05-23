package it.polimi.ingsw.Client.ActionMessages;

import java.io.Serializable;

public class PickCloudMessage implements Serializable {

    private int cloudID;
    public PickCloudMessage(int cloudID){
        this.cloudID = cloudID;
    }

    public int getCloudID(){
        return cloudID;
    }
}
