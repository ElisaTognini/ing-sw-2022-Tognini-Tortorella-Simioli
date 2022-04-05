package it.polimi.ingsw.BoardClasses;

import it.polimi.ingsw.BasicElements.AssistantCard;
import it.polimi.ingsw.Enums.TurnFlow;
import it.polimi.ingsw.Player;
import java.util.*;

/* this class manages the turn/round flow for both the Planning and Action Phases, based on the cards played */

public class RoundManager {
    private TurnFlow currentState;
    private ArrayList<Player> players;
    private ArrayList<Player> sortedPlayers;
    private Player currentPlayer;
    private ArrayList<AssistantCard> cards;

    /* playerList is based on the order in which each player enters the lobby */
    public RoundManager(ArrayList<Player> playerList){
        players = new ArrayList<>();
        sortedPlayers = new ArrayList<>();
        players.addAll(playerList);
        cards = new ArrayList<>();
    }

    /* returns the current state of the turn */
    public TurnFlow getCurrentState(){
        return currentState;
    }

    /* returns the player who is currently playing */
    public Player getCurrentPlayer(){
        return currentPlayer;
    }

    /* a step forwards in the turn */
    public void changeState(TurnFlow state){
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


    public boolean checkForDupe(AssistantCard card){
        if(!cards.contains(card)) return false;
        return true;
        //true if dupe is present, false otherwise
    }

    /*when this method is called, the round moves forward (if all players have picked a card) */
    public void storeCards(AssistantCard card){
        cards.add(card);
        currentPlayer.setCardPickedToTrue();

        if(cards.size() == players.size()){
            changeState(TurnFlow.CARD_PICKED);
            sortActionPhase();
        }

        else
            currentPlayer = sortedPlayers.get(sortedPlayers.indexOf(currentPlayer) + 1);

    }

    /* this method reorders the cards in "cards" ArrayList in ascending order */
    /* so it's possible to compute the order in which every player enters the Action Phase */
    /* and passes the first player of the next planning phase to the computeTurnOrder method */
    private void sortActionPhase(){
        Collections.sort(cards);
        computeTurnOrder(players.indexOf(cards.get(0).getOwner()));
    }


    public void startRound(){
        for(Player p : players) p.setCardPickedToFalse();
        changeState(TurnFlow.BEGINS_TURN);
    }
}
