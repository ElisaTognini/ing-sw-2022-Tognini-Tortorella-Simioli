package it.polimi.ingsw.Client.ActionMessages;

public class PickCloudMessage {

    private int cloudID;
    public PickCloudMessage(int cloudID){
        this.cloudID = cloudID;
    }

    public int getCloudID() {
        return cloudID;
    }
}
