package it.polimi.ingsw.Utils.NetMessages;

import java.io.Serializable;

/** this class represents messages sent by the server to its clients when
 * one of the players plays an assistant card in game, so that the information
 * can be displayed and every other player is able to predict Mother Nature's path
 * As this message is sent through socket, this class implements Serializable.
 * @see Serializable*/

public class PlayedCardMessage implements Serializable, ServerMessage {
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
