package it.polimi.ingsw.Utils.NetMessages;

import java.io.Serializable;

/** messages sent during the preliminary setup phase of the next match
 * from the client to the Server.
 * This message contains information about the player's nickname, and,
 * if the player is the first one to connect to the lobby, it also may contain
 * information about the game mode and the number of players.
 * @see Serializable*/

public class BaseUserMessage implements Serializable {
    private Integer numberOfPlayers;
    private String gameMode;
    private String nickname;

    /* SETTER METHODS */
    public void setNumberOfPlayers(Integer numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    public void setGameMode(String gameMode){
        this.gameMode = gameMode;
    }

    public void setNickname(String nick){
        this.nickname = nick;
    }

    /* GETTER METHODS */
    public Integer getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public String getGameMode(){
        return gameMode;
    }

    public String getNickname(){
        return nickname;
    }
}
