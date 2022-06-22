package it.polimi.ingsw.Utils.NetMessages;

import it.polimi.ingsw.Utils.Enums.GameMode;

import java.io.Serializable;

/** this message is sent from the server to the users participating in a
 * match when said match starts. It only contains the game mode (simple or expert).
 * As this message is sent through sockets, this class implements Serializable
 * @see Serializable*/

public class GameModeMessage implements Serializable, ServerMessage {

    GameMode mode;


    public GameModeMessage(GameMode mode){
        this.mode = mode;
    }

    public GameMode getMode() {
        return mode;
    }

}
