package it.polimi.ingsw;

/* starts and ends game, keeps track of turn, chooses first player*/

import it.polimi.ingsw.Enums.GameMode;
import it.polimi.ingsw.Enums.PlayerNumber;

public abstract class Game {
    private PlayerNumber numberOfPlayers;
    private GameMode mode;
    private int maxNumberOfTowers;

    /*where to retrieve the information about the gamemode?
    * using setter methods assuming the controller enters them*/
    public Game(){

    }

    //information retrieving
    public void setMode(GameMode mode) {
        this.mode = mode;
    }

    public GameMode getMode(){
        return mode;
    }

    public void setNumberOfPlayers(PlayerNumber numberOfPlayers){
        this.numberOfPlayers = numberOfPlayers;
    }

    /*this method will be implemented when we are sure on how to communicate with
    * the controller/view*/
    public void startGame(){}

    /*chooses number of towers to have based on the number of players*/
    private void computeNumbOfTowers(){
        if(this.numberOfPlayers.equals(PlayerNumber.THREE)){
            this.maxNumberOfTowers = 6;
        }
        else
            this.maxNumberOfTowers = 8;
    }
}
