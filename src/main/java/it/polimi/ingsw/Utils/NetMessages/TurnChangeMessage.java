package it.polimi.ingsw.Utils.NetMessages;

import java.io.Serializable;

public class TurnChangeMessage implements Serializable, TurnMessages {

    private String currentPlayer;

    public String getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(String currentPlayer) {
        this.currentPlayer = currentPlayer;
    }
}