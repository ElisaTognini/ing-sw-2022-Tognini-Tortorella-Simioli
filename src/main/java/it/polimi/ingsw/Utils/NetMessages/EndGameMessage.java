package it.polimi.ingsw.Utils.NetMessages;

import java.io.Serializable;

/** this message is sent from the server to the users playing a match when said
 * match is over. This message notifies all players of the end of the game, and it
 * contains information about the winner.
 * As this message is sent through sockets, this class implements Serializable
 * @see Serializable*/

public class EndGameMessage implements Serializable, ServerMessage {

    private String winner;

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }
}
