package it.polimi.ingsw.BoardClasses;

import it.polimi.ingsw.Enums.TurnFlow;
import it.polimi.ingsw.Player;
import java.util.*;

public class TurnManager {
    public static TurnFlow currentState;
    private ArrayList<Player> players;
    private ArrayList<Player> sortedPlayers;
    private static Player currentPlayer;
    private SortedMap<Player, Integer> cardToPlayer;

    /* playerList is based on the order in which each player enters the lobby */
    public TurnManager(ArrayList<Player> playerList){
        players = new ArrayList<>();
        sortedPlayers = new ArrayList<>();
        players.addAll(playerList);
        cardToPlayer = new TreeMap<>();
    }

    /* returns the current state of the turn */
    public static TurnFlow getCurrentState(){
        return currentState;
    }

    /* returns the player who is currently playing */
    public static Player getCurrentPlayer(){
        return currentPlayer;
    }

    /* a step forwards in the turn */
    public static void changeState(TurnFlow state){
        currentState = state;
    }

    /* picks the first player of the game */
    public int pickFirstPlayerIndex(){
        Random rand = new Random();
        int randomIndex = rand.nextInt(players.size());
        return randomIndex;
    }

    /* starting from the player with the lowest assistant card value, places the players
    * in clockwise order into the ArrayList sortedPlayers */
    public void computeTurnOrder(int firstPlayer){
        sortedPlayers.clear();
        sortedPlayers.addAll(players);
        for(int i = 0; i < firstPlayer; i++){
            sortedPlayers.remove(i);
            sortedPlayers.add(players.get(i));
        }
    }


    /*ALL CHECKS AND EXCEPTION FOR INVALID CARD ACTIONS ARE HANDLED
    * BY THE BOARD CLASS - A CARD IS NOT STORED IF IT IS NOT VALID, AND THE TURN ONLY MOVES FORWARD
    * WHEN CARD IS STORED*/
    public boolean checkForDupe(int cardID){
        if(!cardToPlayer.containsValue(cardID)) return false;
        return true;
        //true if dupe is present, false otherwise
    }

    /*when this method is called, the round moves forward (if all players have picked a card) */
    public void storeCards(int cardID){
        cardToPlayer.put(currentPlayer, cardID);
        currentPlayer.setCardPickedToTrue();

        if(cardToPlayer.size() == players.size()){
            changeState(TurnFlow.CARD_PICKED);
        }

        else
            currentPlayer = sortedPlayers.get(sortedPlayers.indexOf(currentPlayer) + 1);

    }

    public void startRound(){
        for(Player p : players) p.setCardPickedToFalse();
        changeState(TurnFlow.BEGINS_TURN);
    }
}
