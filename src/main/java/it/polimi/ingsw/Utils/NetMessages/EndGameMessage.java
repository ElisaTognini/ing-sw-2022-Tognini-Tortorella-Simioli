package it.polimi.ingsw.Utils.NetMessages;

import java.io.Serializable;

public class EndGameMessage implements Serializable, TurnMessages {

    private String winner;

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }
}
