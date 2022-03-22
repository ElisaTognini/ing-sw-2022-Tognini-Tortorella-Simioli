package it.polimi.ingsw;

/* starts and ends game, keeps track of turn, chooses first player*/

import it.polimi.ingsw.Enums.GameMode;
import it.polimi.ingsw.Enums.PlayerNumber;
import java.util.*;

public abstract class Game {
    private Board board;
    private List<Player> playerList;
    private PlayerNumber numberOfPlayers;
    private GameMode mode;
    private int maxNumberOfTowers;

    /*where to retrieve the information about the gamemode?
    * using setter methods assuming the controller enters them*/
    public Game(PlayerNumber numberOfPlayers, GameMode mode){
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
        for(int i=0; i<computeSize(numberOfPlayers);i++){
            playerList.add(new Player(nickname)); //player's nickname gotten in input
        }
    }

    private int computeSize(PlayerNumber numberOfPlayers){
        if(numberOfPlayers.equals(PlayerNumber.TWO)) return 2;
        else if(numberOfPlayers.equals(PlayerNumber.THREE)) return 3;
        else return 4;
    }
    //information retrieving
    public GameMode getMode() {
        return mode;
    }

    /*this method will be implemented when we are sure on how to communicate with
    * the controller/view*/
    public void startGame(){

    }

    /*chooses number of towers to have based on the number of players*/
    private void computeNumbOfTowers(){
        if(this.numberOfPlayers.equals(PlayerNumber.THREE)){
            this.maxNumberOfTowers = 6;
        }
        else
            this.maxNumberOfTowers = 8;
    }
}
