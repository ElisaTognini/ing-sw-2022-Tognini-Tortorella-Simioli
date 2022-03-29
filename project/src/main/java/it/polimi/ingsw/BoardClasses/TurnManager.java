package it.polimi.ingsw.BoardClasses;

import it.polimi.ingsw.BasicElements.AssistantCard;
import it.polimi.ingsw.Enums.TurnFlow;
import it.polimi.ingsw.Player;
import java.util.*;

public class TurnManager {
    public static TurnFlow currentState;
    private ArrayList<Player> players;
    private ArrayList<Player> sortedPlayers;
    private static Player currentPlayer;
    private SortedMap<Player, Integer> cardToPlayer;

    public TurnManager(ArrayList<Player> playerList){
        players = new ArrayList<>();
        sortedPlayers = new ArrayList<>();
        players.addAll(players);
        cardToPlayer = new TreeMap<>();
    }

    public static TurnFlow getCurrentState(){
        return currentState;
    }

    public static Player getCurrentPlayer(){
        return currentPlayer;
    }

    public static void changeState(TurnFlow state){
        currentState = state;
    }

    public void pickFirstRoundOrder(){
        Random rand = new Random();
        int randomIndex = rand.nextInt(players.size());
        Player firstPlayer = players.get(randomIndex);

    }


    /*ALL CHECKS AND EXCEPTION FOR INVALID CARD ACTIONS ARE HANDLED
    * BY THE BOARD CLASS - A CARD IS NOT STORED IF IT IS NOT VALID, AND THE TURN ONLY MOVES FORWARD
    * WHEN CARD IS STORED*/
    public boolean checkForDupe(int cardID){
        if(!cardToPlayer.containsValue(cardID)) return false;
        return true;
        //true if dupe is present, false otherwise
    }

    /*when this method is called, the round moves forward*/
    public void storeCards(int cardID){
        cardToPlayer.put(currentPlayer, cardID);
        players.
    }
}
