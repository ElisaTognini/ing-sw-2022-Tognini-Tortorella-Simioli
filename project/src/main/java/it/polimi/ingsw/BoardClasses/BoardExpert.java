package it.polimi.ingsw.BoardClasses;

import it.polimi.ingsw.BasicElements.Professor;
import it.polimi.ingsw.Expert.CardManager;
import it.polimi.ingsw.Expert.CharacterCardTemplate;
import it.polimi.ingsw.Expert.CoinCounter;
import it.polimi.ingsw.Enums.GameMode;
import it.polimi.ingsw.Enums.PawnDiscColor;
import it.polimi.ingsw.Player;
import it.polimi.ingsw.SchoolBoardClasses.SchoolBoard;
import it.polimi.ingsw.TailoredExceptions.*;

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

    @Override
    public void setup() throws EmptyException, FullCloudException, InvalidCardActionException {
        super.setup();
        /* initialization for character cards */
        chooseCardIndexes();
        for(int i = 0; i<cardsToInstantiate; i++){
            extractedCards[i] = cardManager.returnCard(cardIDs[i]);
        }

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

    @Override
    public void moveStudent(PawnDiscColor color, String nickname) throws EmptyException, ActionNotAuthorizedException {
        super.moveStudent(color, nickname);
        assignCoin(nickname, color);
    }

    @Override
    public void conquerIsland(String nickname) throws TooManyTowersException, EmptyException{
        int sum;
        int maxSum = 0;
        Player conqueror = null;
        if(islands.get(motherNature.getPosition()).hasANoEntryTile()){
            islands.get(motherNature.getPosition()).setNoEntryTileToFalse();
            putBackNoEntryTile();
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

    public void putBackNoEntryTile(){
        noEntryTiles++;
    }

    public void useNoEntryTile() throws EmptyException{
        if(noEntryTiles == 0) throw new EmptyException();
    }

/*    public void purchaseCharacterCard(String nickname, int characterCardID){
        for(CharacterCardTemplate card : extractedCards){
            if(characterCardID == card.getCardID()){
                for()
            }
        }
    }

 */

    public void useCard(){} /*???*/

    //public void

    /*methods to implement
    * - purchase character card
    * - extract three random character cards
    * - initialize character cards
    * CARD CLASS INTERFACE WHICH HOLDS COST AND OVERLOADED EFFECT METHODS WHICH IMPLEMENT DIFFERENT FUNCTIONALITIES
    * DEPENDING ON THE CARD - FACTORY METHOD TO INSTANTIATE ONLY THE NEEDED/EXTRACTED CARDS
    * card effects will partially use already written methods to act out their effects*/

}
