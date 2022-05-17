package it.polimi.ingsw.Utils.NetMessages;

import java.io.Serializable;

public class BaseUserMessage implements UserMessage, Serializable {
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
