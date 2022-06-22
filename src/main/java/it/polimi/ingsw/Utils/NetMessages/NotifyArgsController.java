package it.polimi.ingsw.Utils.NetMessages;

import it.polimi.ingsw.Utils.Enums.NotifyType;

import java.io.Serializable;

/** utility class which aids in the use of Observer pattern, allowing to store information to
 * send and its recipient when the controller needs to notify the users about a misuse
 * of the game, or a new phase within the match.
 * @see Serializable
 * @see java.util.Observer*/

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
