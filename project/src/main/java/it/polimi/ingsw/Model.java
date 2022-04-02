package it.polimi.ingsw;

/* starts and ends game, keeps track of turn, chooses first player*/

import it.polimi.ingsw.BoardClasses.*;
import it.polimi.ingsw.Enums.*;
import java.util.*;

public class Model {
    private Board board;
    private List<Player> playerList;
    private final PlayerNumber numberOfPlayers;
    private final GameMode mode;
    private int maxNumberOfTowers;

    /*where to retrieve the information about the gamemode?
    * using setter methods assuming the controller enters them*/
    public Model(PlayerNumber numberOfPlayers, GameMode mode){
        this.numberOfPlayers = numberOfPlayers;
        this.mode = mode;
        playerList = new ArrayList<Player>();
        if(!numberOfPlayers.equals(PlayerNumber.TEAMS) && mode.equals(GameMode.SIMPLE)) {
            board = new SimpleNotTeamsBoard();
        }
        else if(numberOfPlayers.equals(PlayerNumber.TEAMS) && mode.equals(GameMode.SIMPLE)){
            board = new SimpleTeamsBoard();
        }
        else if(!numberOfPlayers.equals(PlayerNumber.TEAMS) && mode.equals(GameMode.EXPERT)){
            board = new ExpertNotTeamsBoard();
        }
        else if(numberOfPlayers.equals(PlayerNumber.TEAMS) && mode.equals(GameMode.EXPERT)){
            board = new ExpertTeamsBoard();
        }
    }

    //information retrieving
    public static GameMode getMode() {
        return mode;
    }

    public static PlayerNumber getNumberOfPlayers(){
        return numberOfPlayers;
    }

    /*this method will be implemented when we are sure on how to communicate with
    * the controller/view*/
    public void startGame(){

    }

    /*chooses number of towers to have based on the number of players*/

    /*
    private void computeNumbOfTowers(){
        if(this.numberOfPlayers.equals(PlayerNumber.THREE)){
            this.maxNumberOfTowers = 6;
        }
        else
            this.maxNumberOfTowers = 8;
    }
     */
}
