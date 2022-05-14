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
    private int movedStudents;
    private ArrayList<Player> sortedPlayerActions;

    /* playerList is based on the order in which each player enters the lobby */
    public RoundManager(ArrayList<Player> playerList){
        players = new ArrayList<>();
        sortedPlayers = new ArrayList<>();
        players.addAll(playerList);
        cards = new ArrayList<>();
        movedStudents = 0;
        sortedPlayerActions = new ArrayList<>();
    }

    /* returns the current state of the turn */
    public TurnFlow getCurrentState(){
        return currentState;
    }

    /* returns the player who is currently playing */
    public Player getCurrentPlayer(){
        return currentPlayer;
    }

    /* returns the number of students moved by the current player */
    public int getMovedStudents(){return movedStudents;}

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
            sortedPlayers.remove(0);
            sortedPlayers.add(players.get(i));
        }
        currentPlayer = sortedPlayers.get(0);
    }

    /* this method checks if two players have played the same card;
    * the corner case when the player has only one card left is already handled by sortActionPhase because
    * sort does not reorder equal elements */
    public boolean checkForDupe(int cardID){
        for(AssistantCard c: cards) {
            if (c.getAssistantCardID() == cardID) return true;
        }
        //true if dupe is present, false otherwise
        return false;
    }

    /*when this method is called, the round moves forward (if all players have picked a card) */
    public void storeCards(AssistantCard card){
        cards.add(card);
        currentPlayer.setCardPickedToTrue();

        if(cards.size() == players.size()){
            changeState(TurnFlow.CARD_PICKED);
            sortActionPhase();
        }

    }

    /* this method reorders the cards in "cards" ArrayList in ascending order */
    /* so it's possible to compute the order in which every player enters the Action Phase */
    /* and passes the first player of the next planning phase to the computeTurnOrder method */
    public void sortActionPhase(){
        Collections.sort(cards);
        computeTurnOrder(players.indexOf(cards.get(0).getOwner()));
        currentPlayer = cards.get(0).getOwner();
        for(AssistantCard c: cards){
            sortedPlayerActions.add(c.getOwner());
        }
    }

    /* this method changes the player based on the order of the assistant cards*/
    public void refreshCurrentPlayerAction(){
        if(sortedPlayerActions.indexOf(currentPlayer) < sortedPlayerActions.size()-1){
            currentPlayer = sortedPlayerActions.get(sortedPlayerActions.indexOf(currentPlayer) + 1);
        }
    }


    public void startRound(){
        for(Player p : players) p.setCardPickedToFalse();
        changeState(TurnFlow.BEGINS_TURN);
        movedStudents = 0;
    }

    public void refreshCurrentPlayer(){
        if(sortedPlayers.indexOf(currentPlayer) < sortedPlayers.size()-1){
            currentPlayer = sortedPlayers.get(sortedPlayers.indexOf(currentPlayer) + 1);
        }
    }

    public ArrayList<AssistantCard> getCards(){
        return cards;
    }

    public AssistantCard getCurrentPlayersCard(){
        for(AssistantCard c : cards){
            if(c.getOwner().getNickname().equals(currentPlayer.getNickname())){
                return c;
            }
        }
        return null;
    }

    /* the following two methods are needed in order to limit the number of students moved to three */
    public boolean threeStudentsMoved(String nickname){
        if(movedStudents == 3){
            movedStudents = 0;
            return true;
        }
        return false;
    }

    public void increaseMovedStudents(){
        movedStudents++;
    }

    public ArrayList<Player> getPlayerList(){ return players;}

}
