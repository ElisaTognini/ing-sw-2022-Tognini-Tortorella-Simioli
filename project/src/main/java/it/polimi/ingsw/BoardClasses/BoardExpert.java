package it.polimi.ingsw.BoardClasses;

import it.polimi.ingsw.BasicElements.Professor;
import it.polimi.ingsw.Expert.CardManager;
import it.polimi.ingsw.Expert.CharacterCardTemplate;
import it.polimi.ingsw.Expert.CoinCounter;
import it.polimi.ingsw.Enums.GameMode;
import it.polimi.ingsw.Enums.PawnDiscColor;
import it.polimi.ingsw.Player;
import it.polimi.ingsw.SchoolBoardClasses.SchoolBoard;

import java.util.ArrayList;
import java.util.Random;

public class BoardExpert extends Board{

    private CoinCounter[] coins;
    private CharacterCardTemplate[] extractedCards;
    private CardManager cardManager;
    private Professor[] influenceModifier;
    private int noEntryTiles;
    private int[] cardIDs;
    private final int numberOfCharacterCards = 12;
    private final int cardsToInstantiate = 3;
    private int additional_moves;

    public BoardExpert(ArrayList<Player> players, int numberOfClouds, int numberOfTowers, int studentsOnClouds,
                       int studentsInEntrance, GameMode mode){
        super(players, numberOfClouds, numberOfTowers, studentsOnClouds, studentsInEntrance, mode);
        coins = new CoinCounter[players.size()];
        extractedCards = new CharacterCardTemplate[cardsToInstantiate];
        cardManager = new CardManager(this);
        influenceModifier = new Professor[5];
        noEntryTiles = 4;
        cardIDs = new int[cardsToInstantiate];
    }


    /* board class setup is overridden here to add the instantiation of all the expert game
    * elements (cards, coins)
    * the extracted card ids are stored in an array which is then iterated to
    * instantiate the character cards, which will be stored in the extractedCards array*/
    @Override
    public void setup(){
        super.setup();
        /* initialization for character cards */
        chooseCardIndexes();
        for(int i = 0; i<cardsToInstantiate; i++){
            extractedCards[i] = cardManager.returnCard(cardIDs[i]);
        }

    }

    @Override
    public void moveStudent(PawnDiscColor color, String nickname){
        super.moveStudent(color, nickname);
        assignCoin(nickname, color);
    }

    @Override
    public void conquerIsland(){
        int[] sum = new int[players.size()];
        int i = 0, maxInfluence;
        Player conqueror = null;
        boolean deuce = false;


        if(islands.get(motherNature.getPosition()).hasANoEntryTile()){
            islands.get(motherNature.getPosition()).setNoEntryTileToFalse();
            putBackNoEntryTile();
            return;
        }
        else {
            for(SchoolBoard sb : schoolBoards){
                sum[i] = 0;
                for(PawnDiscColor color: PawnDiscColor.values()){
                    if(sb.getProfessorTable().hasProfessor(color)){
                        sum[i] = sum[i] + islands.get(motherNature.getPosition()).getInfluenceByColor(color);
                    }
                    if(islands.get(motherNature.getPosition()).checkIfConquered()){
                        if(islands.get(motherNature.getPosition()).getOwner().getNickname().equals(sb.getOwner().getNickname())) {
                           /*checks if player has played character card which cancels the influence of the tower(s) on a
                            * island */
                            if (islands.get(motherNature.getPosition()).getTowersOnHold() == 0)
                                sum[i] = sum[i] + islands.get(motherNature.getPosition()).getNumberOfTowers();
                            /* adds extra points to the influence of a player; extra is set to 2 only if player
                            * purchased character card 8, otherwise it is 0 */
                            sum[i] = sum[i] + islands.get(motherNature.getPosition()).getExtra();
                        }
                    }
                }
                /* only used if character card 2 has been played */
                if(sb.getModifiedTable()){
                    sb.getProfessorTable().resetPreviousProfessorTable();
                }
                i++;
            }

            /* sets variables modified by character card affecting influence back to default */
            islands.get(motherNature.getPosition()).setTowersOnHold(0);
            islands.get(motherNature.getPosition()).setExtra(0);

            /* decides who conquers the island */
            i = 0;
            maxInfluence = 0;
            for(SchoolBoard sb : schoolBoards){
                if(sum[i] > maxInfluence){
                    maxInfluence = sum[i];
                    conqueror = sb.getOwner();
                    deuce = false;
                }
                else if(sum[i] == maxInfluence && maxInfluence != 0 ){
                    deuce = true;
                }
                i++;
            }

            if(deuce || conqueror == null){
                return;
            }

            if(islands.get(motherNature.getPosition()).checkIfConquered()){
                if(islands.get(motherNature.getPosition()).getOwner().equals(conqueror)){
                    return;
                }
                //returning towers to old owner
                getPlayerSchoolBoard(islands.get(motherNature.getPosition()).getOwner().getNickname()).getTowerSection().returnTowers(
                        islands.get(motherNature.getPosition()).getNumberOfTowers());
                //retrieving towers from conqueror's schoolboard
                getPlayerSchoolBoard(conqueror.getNickname()).getTowerSection().towersToIsland(islands.get(motherNature.getPosition()).getNumberOfTowers());
            }

            if(!islands.get(motherNature.getPosition()).checkIfConquered()){
                getPlayerSchoolBoard(conqueror.getNickname()).getTowerSection().towersToIsland(1);
                islands.get(motherNature.getPosition()).increaseNumberOfTowers(1);
            }

            islands.get(motherNature.getPosition()).getsConquered(conqueror);

        }
    }

    @Override
    public void moveMotherNature(int movements){
        super.moveMotherNature(movements);
        motherNature.setPosition(motherNature.getPosition()+additional_moves);
        additional_moves = 0;
    }

    /* methods for the regular usages of noEntryTiles in the game
    * checks if valid action, sets and unsets noEntryTiles */

    public void putBackNoEntryTile(){
        noEntryTiles++;
    }

    public void useNoEntryTile(){
        noEntryTiles--;
    }

    /* called by the controller */
    public boolean checkIfEnoughNoEntryTiles(){
        if(noEntryTiles == 0)
            return false;
        else
            return true;
    }

    /* methods which allows to set the additional moves for the effect of card 4*/
    public void setAdditionalMoves(int moves){
        additional_moves = moves;
    }

    /* method retrieves the current player's coinCounter and decrements its number by retrieving the
    * desired character card's cost */
    public void purchaseCharacterCard(String nickname, int characterCardID){
        for(CharacterCardTemplate card : extractedCards){
            if(characterCardID == card.getCardID()){
                getPlayersCoinCounter(nickname).purchase(card.getCost());
            }
        }
    }

    public boolean checkIfCardPresent(int cardID){
        for(CharacterCardTemplate c : extractedCards){
            if(c.getCardID() == cardID)
                return true;
        }
        return false;
    }

    private CoinCounter getPlayersCoinCounter(String nickname){
        for(CoinCounter c : coins){
            if(c.getOwner().getNickname().equals(nickname)){
                return c;
            }
        }
        return null;
    }

    /* method that extracts the three random cards and stores them after calling the factory class */
    private void chooseCardIndexes(){
        Random rand = new Random();
        int randomIndex;
        randomIndex = rand.nextInt(numberOfCharacterCards) + 1;
        cardIDs[0] = randomIndex;
        for(int i = 1; i < cardIDs.length; i++){
            randomIndex = rand.nextInt(numberOfCharacterCards) + 1;
            for(int j = 0; j < i; j++){
                if(randomIndex == cardIDs[j]){
                    i--;
                }
                else{
                    cardIDs[i] = randomIndex;
                }
            }
        }
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

    /* this method activates the effect of the character card purchased by the player */
    public void useCard(Object o, String nickname, int cardID){
        for(CharacterCardTemplate c: extractedCards){
            if(c.getCardID() == cardID) c.useCard(o,nickname);
        }
    }

    public void setMotherNaturePosition(int pos){
        motherNature.setPosition(pos);
    }

}
