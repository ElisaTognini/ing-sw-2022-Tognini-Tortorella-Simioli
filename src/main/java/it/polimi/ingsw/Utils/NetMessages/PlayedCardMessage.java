package it.polimi.ingsw.Utils.NetMessages;

import java.io.Serializable;

public class PlayedCardMessage implements Serializable {
    private String owner;
    private String cardID;
    private String powerFactor;

    public String getOwner() {
        return owner;
    }
    public String getCardID(){
        return cardID;
    }
    public String getPowerFactor() {
        return powerFactor;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
    public void setCardID(String cardID) {
        this.cardID = cardID;
    }
    public void setPowerFactor(String powerFactor) {
        this.powerFactor = powerFactor;
    }
}
