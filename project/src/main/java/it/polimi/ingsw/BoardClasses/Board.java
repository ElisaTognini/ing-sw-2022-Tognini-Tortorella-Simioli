package it.polimi.ingsw.BoardClasses;
import it.polimi.ingsw.BasicElements.*;
import it.polimi.ingsw.Enums.PawnDiscColor;
import it.polimi.ingsw.Enums.PlayerNumber;
import it.polimi.ingsw.Enums.*;
import it.polimi.ingsw.Game;
import it.polimi.ingsw.Player;
import it.polimi.ingsw.SchoolBoardClasses.SchoolBoard;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

public abstract class Board {

    private GameMode mode;
    private PlayerNumber numberOfPlayers;
    private BoardInterface boardInterface;
    protected static TurnManager turnManager;

    public Board(){
        /*board interface implementation changes based on gamemode*/
        mode = Game.getMode();
        numberOfPlayers = Game.getNumberOfPlayers();

        if(mode.equals(GameMode.SIMPLE)){
            boardInterface = new BoardInterface();
        }
        else if(mode.equals(GameMode.EXPERT)){
            boardInterface = new BoardInterfaceExpert();
        }
    }
/* for each game mode, setup includes:
* - placing mother nature on a random island tile
* - add one student on each island except for the sixth island from mother nature */
    public void setup(){

    }

    public void roundSetup(){

    }

    public void choosePlayerOrder(){   // modifies the list of players, putting them in the correct order to start the turn

    }

    protected void placeIslands(){

    }

    /*chooses the very first player in the game*/
    protected Player chooseFirstPlayer(){

    }

    protected int randomPlaceMotherNature(){

    }

}
