package it.polimi.ingsw.BoardClasses;

import it.polimi.ingsw.Expert.CoinCounter;
import it.polimi.ingsw.Enums.GameMode;
import it.polimi.ingsw.Enums.PawnDiscColor;
import it.polimi.ingsw.Player;
import it.polimi.ingsw.SchoolBoardClasses.SchoolBoard;
import it.polimi.ingsw.TailoredExceptions.EmptyException;
import it.polimi.ingsw.TailoredExceptions.FullCloudException;

import java.util.ArrayList;

public class BoardExpert extends Board{

    private CoinCounter[] coins;

    public BoardExpert(ArrayList<Player> players, int numberOfClouds, int numberOfTowers, int studentsOnClouds,
                       int studentsInEntrance, GameMode mode){
        super(players, numberOfClouds, numberOfTowers, studentsOnClouds, studentsInEntrance, mode);
        coins = new CoinCounter[players.size()];
    }

    @Override
    public void setup() throws EmptyException, FullCloudException {
        super.setup();
        /* initialization for character cards */
    }

    public void assignCoin(String nickname, PawnDiscColor color){
        for(SchoolBoard sb : schoolBoards){
            if(sb.getOwner().getNickname().equals(nickname)){
                if(sb.getDiningRoom().checkIfMod3(color)){
                    for(CoinCounter c : coins){
                        if(c.getOwner().getNickname().equals(nickname)){
                            c.addCoin();
                        }
                    }
                }
            }
        }
    }

    /*methods to implement
    * - purchase character card
    * - extract three random character cards
    * - initialize character cards
    * CARD CLASS INTERFACE WHICH HOLDS COST AND OVERLOADED EFFECT METHODS WHICH IMPLEMENT DIFFERENT FUNCTIONALITIES
    * DEPENDING ON THE CARD - FACTORY METHOD TO INSTANTIATE ONLY THE NEEDED/EXTRACTED CARDS
    * card effects will partially use already written methods to act out their effects*/

}
