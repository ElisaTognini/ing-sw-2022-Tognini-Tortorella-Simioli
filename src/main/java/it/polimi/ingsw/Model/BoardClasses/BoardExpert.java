package it.polimi.ingsw.Model.BoardClasses;

import it.polimi.ingsw.Model.BasicElements.Island;
import it.polimi.ingsw.Model.Expert.CardManager;
import it.polimi.ingsw.Model.Expert.CharacterCardTemplate;
import it.polimi.ingsw.Model.Expert.CoinCounter;
import it.polimi.ingsw.Utils.Enums.GameMode;
import it.polimi.ingsw.Utils.Enums.PawnDiscColor;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Model.SchoolBoardClasses.SchoolBoard;

import java.util.ArrayList;
import java.util.Random;

/** Class BoardExpert inherits all the methods from Board and adds those needed to handle expert features.
 * To do so, BoardExpert contains new attributes for those features: coins keeeps track of the number of coins
 * owned by each player, extractedCards contains the three extracted cards for the game, a reference to the
 * factory class for the cards and a few parameters needed for the actions of the some character cards. */

public class BoardExpert extends Board {

    private CoinCounter[] coins;
    private CharacterCardTemplate[] extractedCards;
    private CardManager cardManager;
    private int noEntryTiles;
    private int[] cardIDs;
    private final int numberOfCharacterCards = 12;
    private final int cardsToInstantiate = 3;
    private int additional_moves;
    private String extra;

    /** Constructor BoardExpert creates a new instance of board expert
     *
     * @param players of type ArrayList - players how will play the game
     * @param numberOfClouds of type int - number of clouds
     * @param numberOfTowers of type int - number of towers in each player's tower section
     * @param studentsInEntrance of type int - the number of students in each player's entrance
     * @param mode of type GameMode - the mode chosen by the players to play the game */
    public BoardExpert(ArrayList<Player> players, int numberOfClouds, int numberOfTowers, int studentsOnClouds,
                       int studentsInEntrance, GameMode mode) {
        super(players, numberOfClouds, numberOfTowers, studentsOnClouds, studentsInEntrance, mode);
        coins = new CoinCounter[players.size()];
        extractedCards = new CharacterCardTemplate[cardsToInstantiate];
        cardManager = new CardManager(this);
        noEntryTiles = 4;
        cardIDs = new int[cardsToInstantiate];
        extra = null;
    }


    @Override
    /** Override of method roundSetup in Board: calls method from superclass and then sets value of attributes
     * additional_moves, towersOnHold, ignoredInfluence, extra to default values and resets player's professor
     * table to its previous values (only if character card 2 has been played).
     * additional_moves is set to 0 by default and modified only if character card 4 1has been played;
     * towersOnHold and ignoredInfluence are set to 0 by default and changed only if character card 6 has been played;
     * extra is set to null by default and modified only if character card 8 has been played */
    public void roundSetup(){
        super.roundSetup();
        /* setting extras back to default values */
        additional_moves = 0;
        extra = null;
        for(Island i : islands){
            i.setTowersOnHold(0);
            i.setIgnoredInfluencetoNull();
        }
        for(SchoolBoard sb : schoolBoards){
            if(sb.getModifiedTable()){
                sb.getProfessorTable().resetPreviousProfessorTable();
            }
            sb.resetModifiedTable();
        }
        setChanged();
        notifyObservers();
    }


    /** Override of method setup in Board: calls method from superclass and then randomly draws three character
     * cards indexes, stored in array then iterated by cardManager, which contains a reference to the factory class,
     * to instantiate the actual character cards.
     * Finally, it initializes coin counters for each player. */
    @Override
    public void setup() {
        super.setup();

        chooseCardIndexes();
        for (int i = 0; i < cardsToInstantiate; i++) {
            extractedCards[i] = cardManager.returnCard(cardIDs[i]);
        }

        int i = 0;
        for (Player p : players) {
            coins[i] = new CoinCounter(p);
            i++;
        }
        setChanged();
        notifyObservers();
    }


    /** Override o@f method moveStudent in Board: calls method from superclass and adds call to method
     * assignCoin to see if the player has earned a new coin.
     *
     * @param color of type PawnDiscColor - the color of the student moved to the dining room
     * @param nickname of type String - the nickname of the player who performed the action */
    @Override
    public void moveStudent(PawnDiscColor color, String nickname) {
        super.moveStudent(color, nickname);
        assignCoin(nickname, color);
    }



    /** Override of method conquerIsland in Board: firstly, it checks if the island has a no entry tile and
     * returns immediately if so, because it does not need to resolve the conquering. Then, while checking if
     * the island was already conquered, it also checks if player has played character card which cancels the
     * influence of the tower(s) on an island. Afterwards, if the player purchased card 8, his nickname has
     * been saved in extra, therefore two extra points are added to their influence or, if someone played character
     * card 2, it resets that player's professor table back to the previous state. Lastly, sets variables modified
     * by character card affecting influence back to default and finally resolves the conquering of the island. */
    @Override
    public void conquerIsland() {
        int[] sum = new int[players.size()];
        int i, maxInfluence = 0;
        Player conqueror = null;
        boolean deuce = false;


        if (islands.get(motherNature.getPosition()).hasANoEntryTile()) {
            islands.get(motherNature.getPosition()).removeNoEntryTile();
            putBackNoEntryTile();
            return;
        } else {
            for (i = 0; i < sum.length; i++) {
                sum[i] = 0;
                for (PawnDiscColor color : PawnDiscColor.values()) {
                    if (schoolBoards.get(i).getProfessorTable().hasProfessor(color)) {
                        sum[i] = sum[i] + islands.get(motherNature.getPosition()).getInfluenceByColor(color);
                    }
                }

                if (islands.get(motherNature.getPosition()).checkIfConquered()) {
                    if (islands.get(motherNature.getPosition()).getOwner().getNickname().equals(schoolBoards.get(i).getOwner().getNickname())){

                        if (islands.get(motherNature.getPosition()).getTowersOnHold() == 0)
                            sum[i] = sum[i] + islands.get(motherNature.getPosition()).getNumberOfTowers();

                        maxInfluence = sum[i];
                        conqueror = schoolBoards.get(i).getOwner();
                    }
                }

                if(extra != null) {
                    if (extra.equals(schoolBoards.get(i).getOwner().getNickname())) sum[i] = sum[i] + 2;
                }

                if (schoolBoards.get(i).getModifiedTable()) {
                    schoolBoards.get(i).getProfessorTable().resetPreviousProfessorTable();
                    schoolBoards.get(i).resetModifiedTable();
                }
            }

            islands.get(motherNature.getPosition()).setTowersOnHold(0);
            extra = null;

            for (i = 0; i < sum.length; i++) {
                if (sum[i] > maxInfluence) {
                    maxInfluence = sum[i];
                    conqueror = schoolBoards.get(i).getOwner();
                    deuce = false;
                } else if (sum[i] == maxInfluence && maxInfluence != 0) {
                    deuce = true;
                }
            }

            if (deuce || conqueror == null) {
                return;
            }

            if (islands.get(motherNature.getPosition()).checkIfConquered()) {
                if (islands.get(motherNature.getPosition()).getOwner().equals(conqueror)) {
                    return;
                }

                getPlayerSchoolBoard(islands.get(motherNature.getPosition()).getOwner().getNickname()).getTowerSection().returnTowers(
                        islands.get(motherNature.getPosition()).getNumberOfTowers());

                if(!(getPlayerSchoolBoard(conqueror.getNickname()).getTowerSection().getNumberOfTowers() <
                        islands.get(motherNature.getPosition()).getNumberOfTowers())) {
                    getPlayerSchoolBoard(conqueror.getNickname()).getTowerSection().towersToIsland(islands.get(motherNature.getPosition()).getNumberOfTowers());
                } else {
                    isGameOver = true;
                    return;
                }
            }

            if (!islands.get(motherNature.getPosition()).checkIfConquered()) {
                getPlayerSchoolBoard(conqueror.getNickname()).getTowerSection().towersToIsland(1);
                islands.get(motherNature.getPosition()).increaseNumberOfTowers(1);
            }

            islands.get(motherNature.getPosition()).getsConquered(conqueror);
            setChanged();
            notifyObservers();
        }
    }


    /** Override of method moveMotherNature in Board: calls the method of the superclass and adds value of attribute
     * additional_moves to mother nature's position.
     *
     * @param movements of type int - mother nature's movements */
    @Override
    public void moveMotherNature(int movements) {
        super.moveMotherNature(movements);
        motherNature.setPosition(motherNature.getPosition() + additional_moves);
        additional_moves = 0;
        setChanged();
        notifyObservers();
    }

    /** Methods putBackNoEntryTiles adds back a no entry tile to the board after it was used. */
    public void putBackNoEntryTile() {
        noEntryTiles++;
        setChanged();
        notifyObservers();
    }


    /** Method useNoEntryTile reduces value of attribute noEntryTiles when character card 4 is played. */
    public void useNoEntryTile() {
        noEntryTiles--;
        setChanged();
        notifyObservers();
    }

    /** Method checkIfEnoughNoEntryTiles checks if there are any no entry tiles left on the board to be used.
     *
     * @return boolean - true if there are, false otherwise */
    public boolean checkIfEnoughNoEntryTiles() {
        if (noEntryTiles == 0)
            return false;
        else
            return true;
    }

    /** Method setAdditionalMoves sets the additional moves to attribute additional_moves (effect of character
     * card 4)
     *
     * @param moves of type int - additional moves for mother nature*/
    public void setAdditionalMoves(int moves) {
        additional_moves = moves;
    }


    /** Method purchaseCharacterCard retrieves the current player's coinCounter and decrements its number of the same
     * value as the cost of the character card they want to purchase if the selected car is one of the ones extracted.
     *
     * @param nickname of type String - the nickname of the player who purchased the card
     * @param characterCardID of type int - the ID of the card purchased */
    public void purchaseCharacterCard(String nickname, int characterCardID) {
        for (CharacterCardTemplate card : extractedCards) {
            if (characterCardID == card.getCardID()) {
                getPlayersCoinCounter(nickname).purchase(card.getCost());
            }
        }
        setChanged();
        notifyObservers();
    }


    /** Method checkIfCardPresent if the card identified by the ID given in input is one of the card extracted
     * stored in extractedCards
     *
     * @param cardID - the ID of the character card
     *
     * @return boolean - true if present, false otherwise */
    public boolean checkIfCardPresent(int cardID) {
        for (CharacterCardTemplate c : extractedCards) {
            if (c.getCardID() == cardID)
                return true;
        }
        return false;
    }


    /** getter method - method getPlayersCoinCounter returns the coin counter of the player whose nickname is
     * given in input.
     *
     * @param nickname of type String - the player's nickname
     *
     * @return CoinCounter - the selected player's coin counter
     * */
    public CoinCounter getPlayersCoinCounter(String nickname) {
        for (CoinCounter c : coins) {
            if (c.getOwner().getNickname().equals(nickname)) {
                return c;
            }
        }
        return null;
    }


    /** getter method - method getCardsCost returns the cost of the character card identified by the ID
     * given in input, only if it is one of the extracted cards stored in extractedCards
     *
     * @param cardID of type int - the ID of the character card
     *
     * @return int - the cost of the card */
    public int getCardsCost(int cardID) {
        for (CharacterCardTemplate c : extractedCards) {
            if (c.getCardID() == cardID)
                return c.getCost();
        }
        return 0;
    }

    /** getter method - method getExtractedCards returns the three character cards extracted and stored in
     * attribute extractedCards.
     *
     * @return CharacterCardTemplate[] - the array the character cards are stored into */
    public CharacterCardTemplate[] getExtractedCards() { return extractedCards; }

    /** Private method chooseCardIndexes extracts three random card indexes and stores them in the array cardIDs */
    private void chooseCardIndexes() {
        Random rand = new Random();
        int randomIndex;
        randomIndex = rand.nextInt(numberOfCharacterCards) + 1;
        cardIDs[0] = randomIndex;
        for (int i = 1; i < cardIDs.length; i++) {
            randomIndex = rand.nextInt(numberOfCharacterCards) + 1;
            for (int j = 0; j < i; j++) {
                if (randomIndex == cardIDs[j]) {
                    i--;
                } else {
                    cardIDs[i] = randomIndex;
                }
            }
        }
    }


    /** Method assignCoin iterates through the player's school board and adds a coin to their coin counter if
     * they have three or multiple students of the selected color in their dining room
     *
     * @param nickname of type String - the player's nickname
     * @param color of type PawnDiscColor - color
     **/
    public void assignCoin(String nickname, PawnDiscColor color) {
        for (SchoolBoard sb : schoolBoards) {
            if (sb.getOwner().getNickname().equals(nickname)) {
                if (sb.getDiningRoom().checkIfMod3(color)) {
                    for (CoinCounter c : coins) {
                        if (c.getOwner().getNickname().equals(nickname)) {
                            c.addCoin();
                            setChanged();
                            notifyObservers();
                        }
                    }
                }
            }
        }
    }


    /** Method useCard activates the effect of the character card purchased by the player
     *
     * @param o of type Object - the parameters required by the character card purchased
     * @param nickname of type String - the nickname of the player who purchased the card
     * @param cardID of type int - the ID of the characted card purchased */
    public void useCard(Object o, String nickname, int cardID) {
        for (CharacterCardTemplate c : extractedCards) {
            if (c.getCardID() == cardID) {
                c.useCard(o, nickname);
                c.increaseCost();
                setChanged();
                notifyObservers();
            }
        }
    }


    /** Method isActionForbidden calls method checkIfActionIsForbidden to check if the requirements of the three
     * character card extracted are fulfilled by the action the player is trying to perform.
     *
     * @param cardID of type int - the ID of the card
     * @param o of type Object - the parameters passed by the player in input
     * @param nickname of type String - the nickname of the player performing the action */
    public boolean isActionForbidden(int cardID, Object o, String nickname){
        for(CharacterCardTemplate c : extractedCards){
            if(c.getCardID() == cardID){
                return c.checkIfActionIsForbidden(o, nickname);
            }
        }
        return true;
    }

    /** setter method - method setMotherNaturePosition sets mother nature position according to the value passed
     * in input
     *
     * @param pos of type int - mother nature's position */
    public void setMotherNaturePosition(int pos) {
        motherNature.setPosition(pos);
    }

    /** setter method - method setExtra sets value of attribute extra as the nickname of the player passed as input
     *
     * @param nickname of type String - the nickname of the player who purchased character card 2 */
    public void setExtra(String nickname) {extra = nickname;}


    /** setter method - method setExtractedCards set the cards passed in input as the extractedCard.
     * This method is only used for test purposes, players cannot access to this method because the extraction
     * of the card is completely random.
     *
     * @param cards of type CharacterCardTemplate[] - the character cards extracted */
    public void setExtractedCards(CharacterCardTemplate[] cards) {
        extractedCards = cards;
    }


    /** getter method - method getCoinCounters returns each players' coin counter
     *
     * @return CoinCounter[] - array of coin counters */
    public CoinCounter[] getCoinCounters(){
        return coins;
    }
}