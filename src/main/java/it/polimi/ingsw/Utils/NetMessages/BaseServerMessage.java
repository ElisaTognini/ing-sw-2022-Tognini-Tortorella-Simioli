package it.polimi.ingsw.Utils.NetMessages;

import java.io.Serializable;

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
