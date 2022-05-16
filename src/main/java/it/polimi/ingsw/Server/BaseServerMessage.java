package it.polimi.ingsw.Server;

import javax.sql.rowset.BaseRowSet;
import java.io.Serializable;
import java.util.ArrayList;

public class BaseServerMessage implements ServerMessage, Serializable {
    private String message;
    private String aimed_at;

    public BaseServerMessage(String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAimed_at() {
        return aimed_at;
    }

    public void setAimed_at(String aimed_at) {
        this.aimed_at = aimed_at;
    }

    @Override
    public String toString(){
        return message;
    }
}
