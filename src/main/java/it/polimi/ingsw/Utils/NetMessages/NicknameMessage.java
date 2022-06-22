package it.polimi.ingsw.Utils.NetMessages;

import java.io.Serializable;

/** this message is sent from server to each client once the client's nickname
 * is deemed as valid. It contains the user's nickname so that it can be stored client-side
 * As this message is sent through sockets, this class implements Serializable
 * @see Serializable*/

public class NicknameMessage implements Serializable, ServerMessage {

    String nickname;

    public NicknameMessage(String nick){
        this.nickname = nick;
    }

    public String getNickname() {
        return nickname;
    }
}
