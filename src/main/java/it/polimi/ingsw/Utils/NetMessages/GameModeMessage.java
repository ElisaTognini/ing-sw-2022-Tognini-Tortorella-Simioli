package it.polimi.ingsw.Utils.NetMessages;

import it.polimi.ingsw.Utils.Enums.GameMode;

import java.io.Serializable;

public class GameModeMessage implements Serializable {

    GameMode mode;


    public GameModeMessage(GameMode mode){
        this.mode = mode;
    }

    public GameMode getMode() {
        return mode;
    }

}
