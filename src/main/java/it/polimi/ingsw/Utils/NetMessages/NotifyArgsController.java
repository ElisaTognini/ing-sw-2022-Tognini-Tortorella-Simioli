package it.polimi.ingsw.Utils.NetMessages;

import it.polimi.ingsw.Utils.Enums.NotifyType;

import java.io.Serializable;

public class NotifyArgsController implements Serializable {

    private String nickname;
    private BaseServerMessage message;
    private NotifyType type;

    public NotifyArgsController(String nickname, BaseServerMessage message, NotifyType type){
        this.nickname = nickname;
        this.message = message;
        this.type = type;
    }

    public String getNickname(){return nickname;}

    public BaseServerMessage getMessage(){return message;}

    public NotifyType getType() {
        return type;
    }
}
