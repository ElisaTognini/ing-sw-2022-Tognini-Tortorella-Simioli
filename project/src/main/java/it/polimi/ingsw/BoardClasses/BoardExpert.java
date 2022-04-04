package it.polimi.ingsw.BoardClasses;

import it.polimi.ingsw.Expert.CardManager;
import it.polimi.ingsw.Expert.CharacterCardTemplate;
import it.polimi.ingsw.Expert.CoinCounter;
import it.polimi.ingsw.Enums.GameMode;
import it.polimi.ingsw.Enums.PawnDiscColor;
import it.polimi.ingsw.Player;
import it.polimi.ingsw.SchoolBoardClasses.SchoolBoard;
import it.polimi.ingsw.TailoredExceptions.EmptyException;
import it.polimi.ingsw.TailoredExceptions.FullCloudException;
import it.polimi.ingsw.TailoredExceptions.TooManyTowersException;

import java.util.ArrayList;

public class BoardExpert extends Board{

    private CoinCounter[] coins;
    private CharacterCardTemplate[] extractedCards;
    private CardManager cardManager;

    public BoardExpert(ArrayList<Player> players, int numberOfClouds, int numberOfTowers, int studentsOnClouds,
                       int studentsInEntrance, GameMode mode){
        super(players, numberOfClouds, numberOfTowers, studentsOnClouds, studentsInEntrance, mode);
        coins = new CoinCounter[players.size()];
        extractedCards = new CharacterCardTemplate[3];
        cardManager = new CardManager(this);
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

    @Override
    public void conquerIsland(String nickname) throws TooManyTowersException, EmptyException{
        int sum;
        int maxSum = 0;
        Player conqueror = null;
        if(islands.get(motherNature.getPosition()).hasANoEntryTile()){
            islands.get(motherNature.getPosition()).setNoEntryTileToFalse();
            //card5.retrieveNoEntryTile()
        }
        else {
            for(SchoolBoard sb : schoolBoards){
                sum = 0;
                for(PawnDiscColor color: PawnDiscColor.values()){
                    if(sb.getProfessorTable().hasProfessor(color)){
                        sum = sum + islands.get(motherNature.getPosition()).getInfluenceByColor(color);
                    }
                    if(islands.get(motherNature.getPosition()).checkIfConquered()){
                        if(islands.get(motherNature.getPosition()).getOwner().getNickname().equals(sb.getOwner().getNickname())) {
                            if (islands.get(motherNature.getPosition()).getTowersOnHold() == 0) {
                                sum = sum + islands.get(motherNature.getPosition()).getNumberOfTowers();
                                sum = sum + islands.get(motherNature.getPosition()).getExtra();
                            }
                        }
                    }
                    if(sum > maxSum){
                        maxSum = sum;
                        conqueror = sb.getOwner();
                    }
                }
            }
            islands.get(motherNature.getPosition()).setTowersOnHold(0);
            islands.get(motherNature.getPosition()).setExtra(0);
            if(conqueror.getNickname().equals(nickname)){
                fixTowers(nickname);
                islands.get(motherNature.getPosition()).getsConquered(conqueror);
                for(SchoolBoard sb: schoolBoards){
                    if(sb.getOwner().getNickname().equals(nickname)){
                        sb.getTowerSection().towersToIsland(islands.get(motherNature.getPosition()).getNumberOfTowers());
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
