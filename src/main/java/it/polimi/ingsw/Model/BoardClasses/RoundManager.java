package it.polimi.ingsw.Model.BoardClasses;

import it.polimi.ingsw.Model.BasicElements.AssistantCard;
import it.polimi.ingsw.Utils.Enums.ActionType;
import it.polimi.ingsw.Utils.Enums.TurnFlow;
import it.polimi.ingsw.Model.Player;
import java.util.*;

/** Class RoundManager manages the turn/round flow for both the Planning and Action Phases, based on the cards
 * played: it contains references to the array list containing the players currently playing the match, the array
 * list containing the players sorted based on the id of the assistant card they played, the current player,
 * the number of students played during action phase, the assistant cards played in each round and a boolean value
 * to determine whether if it's the planning phase or the action phase. */

public class RoundManager extends Observable{
    private TurnFlow currentState;
    private ArrayList<Player> players;
    private ArrayList<Player> sortedPlayers;
    private Player currentPlayer;
    private ArrayList<AssistantCard> cards;
    private int movedStudents;
    private boolean isPlanningPhase;
    private ArrayList<Player> sortedPlayerActions;

    /** Constructor RoundManager creates a new instance of round manager.
     *  @param playerList of type ArrayList - based on the order in which each player enters the lobby */
    public RoundManager(ArrayList<Player> playerList){
        players = new ArrayList<>();
        sortedPlayers = new ArrayList<>();
        players.addAll(playerList);
        cards = new ArrayList<>();
        movedStudents = 0;
        sortedPlayerActions = new ArrayList<>();
        isPlanningPhase = true;
    }


    /** getter method - Method getCurrentState returns the current state of the turn
     *
     * @return TurnFlow - current state
     * */
    public TurnFlow getCurrentState(){
        return currentState;
    }


    /** getter method - Method getCurrentPlayer returns the player who is currently playing
     *
     * @return Player - current player
     * */
    public Player getCurrentPlayer(){
        return currentPlayer;
    }


    /** getter method - Method getMovedStudents returns the number of students moved by the current player
     *
     * @return int - moved students
     * */
    public int getMovedStudents(){return movedStudents;}


    /** Method changeState makes the turn move forward
     *
     * @param state of type TurnFlow - the previous state of the turn
     *
     * @return TurnFlow - the new current state of the turn */
    public void changeState(TurnFlow state){
        currentState = state;
    }


    /** Method pickFirstPlayerIndex randomly picks the first player to start the game
     *
     * @return int - the id of the player */
    public int pickFirstPlayerIndex(){
        Random rand = new Random();
        int randomIndex = rand.nextInt(players.size());
        return randomIndex;
    }


    /** Method computeTurnOrder, places the players in clockwise order into the ArrayList sortedPlayers,
     * starting from the player with the lowest assistant card value.
     *
     * @param firstPlayer of type int - the id of the player randomly extracted */
    public void computeTurnOrder(int firstPlayer){
        sortedPlayers.clear();
        sortedPlayers.addAll(players);
        for(int i = 0; i < firstPlayer; i++){
            sortedPlayers.remove(0);
            sortedPlayers.add(players.get(i));
        }
        currentPlayer = sortedPlayers.get(0);
        setChanged();
    }


    /** Method checkForDupe checks if two players have played the same card, because two players cannot play the same
     * assistant card in the same turn except when they only have one card left.
     * When this happens, the order of action is still preserved because Collection sort in sortActionPhase does not
     * reorder equal elements.
     *
     * @param cardID of type int - the id of the assistant card played
     *
     * @return boolean - true is the same card (from a different deck) has already been played, false otherwise */
    public boolean checkForDupe(int cardID){

        if(cards.size() == players.size()){
            cards.clear();
        }

        for(AssistantCard c: cards) {
            if (c.getAssistantCardID() == cardID) return true;
        }

        return false;
    }


    /** Method storeCards stores all the cards played by the players during that round.
     * If all players have
     * picked a card, the round moves forward
     *
     * @param card of type AssistantCard - the assistant card played by the player */
    public void storeCards(AssistantCard card){

        cards.add(card);

        currentPlayer.setCardPickedToTrue();

        if(cards.size() == players.size()){
            changeState(TurnFlow.CARD_PICKED);
            sortActionPhase();
        }
    }

    /** Method sortActionPhase reorders the cards stored in the array list "cards" in ascending order in pursuance
     * of the computing of the order in which every player enters the Action Phase and then it passes the first
     * player of the next planning phase to the computeTurnOrder method */
    public void sortActionPhase(){
        isPlanningPhase = false;
        sortedPlayerActions.clear();
        Collections.sort(cards);
        computeTurnOrder(players.indexOf(cards.get(0).getOwner()));
        currentPlayer = cards.get(0).getOwner();
        setChanged();
        notifyObservers(ActionType.PLAYER_CHANGE);
        for(AssistantCard c: cards){
            sortedPlayerActions.add(c.getOwner());
        }
    }

    /** Method refreshCurrentPlayerAction changes the current player after the previous one has picked a card */
    public void refreshCurrentPlayerAction(){
        if(sortedPlayerActions.indexOf(currentPlayer) < sortedPlayerActions.size()-1){
            currentPlayer = sortedPlayerActions.get(sortedPlayerActions.indexOf(currentPlayer) + 1);
            setChanged();
            changeState(TurnFlow.CARD_PICKED);
            notifyObservers(ActionType.PLAYER_CHANGE);
        }
    }


    /** Method startRound sets value of boolean attribute isPlanningPhase to true, puts back to false the value of
     * boolean attribute cardPicked for each player, resets movedStudents back to zero and changes the state of the
     * turn back to BEGINS_TURN. */
    public void startRound(){
        isPlanningPhase = true;
        for(Player p : players) p.setCardPickedToFalse();
        changeState(TurnFlow.BEGINS_TURN);
        movedStudents = 0;
        setChanged();
        notifyObservers(ActionType.NEW_ROUND);
    }


    /** Method refreshCurrentPlayer changes the current player to the next one in array list sortedPlayers if
     * current player is not the last one in the list*/
    public void refreshCurrentPlayer(){
        if(sortedPlayers.indexOf(currentPlayer) < sortedPlayers.size()-1){
            currentPlayer = sortedPlayers.get(sortedPlayers.indexOf(currentPlayer) + 1);
            setChanged();
            notifyObservers(ActionType.PLAYER_CHANGE);
        }
    }


    /** getter method - Method getCards returns the assistant cards played by players in this round
     *
     * @return ArrayList - array list of assistant cards */
    public ArrayList<AssistantCard> getCards(){
        return cards;
    }

    /** getter method - Method getCurrentPlayersCard returns the assistant card of the current player
     *
     * @return AssistantCard - current player's assistant card */
    public AssistantCard getCurrentPlayersCard(){
        for(AssistantCard c : cards){
            if(c.getOwner().getNickname().equals(currentPlayer.getNickname())){
                return c;
            }
        }
        return null;
    }

    /** Method threeStudentsMoved checks if the current player has moved exactly three students from their entrance
     * and, if so, sets value of attribute movedStudents back to 0.
     *
     * @return boolean - true if threeStudentsMoved == 3, false otherwise */
    public boolean threeStudentsMoved(){
        if(movedStudents == 3){
            movedStudents = 0;
            return true;
        }
        return false;
    }


    /** Method increaseMovedStudents increases value of attribute movedStudents by one unit */
    public void increaseMovedStudents(){
        movedStudents++;
    }

    /** Method change signals that this Observable object has changed */
    public void change(){
        setChanged();
    }

    /** getter method - Method isPlanningPhase returns the value of the boolean attribute isPlanningPhase
     *
     * @return boolean - value of isPlanningPhase
     * */
    public boolean isPlanningPhase() {
        return isPlanningPhase;
    }


    /** getter method - Method getFirstPlanningPhase returns the first player of the sortedPlayers array list
     *
     * @return Player - first player of the array list */
    public Player getFirstPlanningPhase(){
        return sortedPlayers.get(0);
    }


    /** setter method - Method setCurrentPlayer sets player as current player
     *
     * @param player of type Player - the next current player
     * */
    public void setCurrentPlayer(Player player){
        currentPlayer = player;
    }
}
