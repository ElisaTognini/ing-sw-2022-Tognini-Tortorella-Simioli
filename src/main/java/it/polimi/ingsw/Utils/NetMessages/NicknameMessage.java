package it.polimi.ingsw.Utils.NetMessages;

import java.io.Serializable;

public class NicknameMessage implements Serializable {

    String nickname;

    public NicknameMessage(String nick){
        this.nickname = nick;
    }

    public String getNickname() {
        return nickname;
    }
}
