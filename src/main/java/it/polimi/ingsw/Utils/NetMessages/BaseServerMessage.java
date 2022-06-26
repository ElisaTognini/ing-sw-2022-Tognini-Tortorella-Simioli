package it.polimi.ingsw.Utils.NetMessages;

import java.io.Serializable;
/** instances of this class contain a base message which is used
 * for notifying players of errors in game (not your turn, action is forbidden...) and for
 * notifying players last round or game are starting.
 * As this message is sent through socket, this class implements Serializable.
 * @see Serializable
 * @see ServerMessage
 * */
public class BaseServerMessage implements ServerMessage, Serializable {
    private String message;

    public BaseServerMessage(String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString(){
        return message;
    }
}
