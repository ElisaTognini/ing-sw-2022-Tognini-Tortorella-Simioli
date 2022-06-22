package it.polimi.ingsw.Utils.NetMessages;

import java.io.Serializable;
/** this message is sent by the server to the user when there is a change in turn
 * during the game.
 * As this message is sent through socket, this class implements Serializable.
 * @see Serializable*/
public class TurnChangeMessage implements Serializable{

    private String currentPlayer;

    public String getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(String currentPlayer) {
        this.currentPlayer = currentPlayer;
    }
}
