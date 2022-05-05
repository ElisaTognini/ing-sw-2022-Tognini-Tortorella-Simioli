package it.polimi.ingsw.Server;

import javax.sql.rowset.BaseRowSet;
import java.io.Serializable;
import java.util.ArrayList;

public class BaseServerMessage implements ServerMessage, Serializable {
    private String message;

    public BaseServerMessage(String message){
        this.message = message;
    }

}
