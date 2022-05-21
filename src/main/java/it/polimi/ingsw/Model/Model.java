package it.polimi.ingsw.Model;

import it.polimi.ingsw.Model.BasicElements.AssistantCardDeck;
import it.polimi.ingsw.Model.BoardClasses.Board;
import it.polimi.ingsw.Model.BoardClasses.BoardExpert;
import it.polimi.ingsw.Model.BoardClasses.RoundManager;
import it.polimi.ingsw.Model.SchoolBoardClasses.SchoolBoard;
import it.polimi.ingsw.Server.ViewUpdateMessageWrapper;
import it.polimi.ingsw.Utils.Enums.ActionType;
import it.polimi.ingsw.Utils.Enums.GameMode;
import it.polimi.ingsw.Utils.NetMessages.TurnChangeMessage;

import java.util.*;

public class Model extends Observable implements Observer{
    private Board board;
    private RoundManager roundManager;
    private ArrayList<Player> playerList;
    private final GameMode mode;
    private final int numberOfPlayers;
    private int numberOfTowers;
    private int studentsOnCloud;
    private int numberOfClouds;
    private int studentsInEntrance;
    private String[] playerNames;
    private ViewUpdateMessageWrapper messageWrapper;
    private TurnUpdates turnUpdates;

    /* internal class which processes the messages sent by the notify() of the RoundManager */
    public class TurnUpdates extends Observable implements Observer{

        @Override
        public void update(Observable o, Object arg) {
            /* in base ai messaggi che gli arrivano, TurnUpdates invia una diversa notify() al match */
            ActionType argument = (ActionType)arg;
            switch (argument){
                case PLAYER_CHANGE:
                    TurnChangeMessage message = messageWrapper.turnChangeMessage(roundManager.getCurrentPlayer().getNickname());
                    setChanged();
                    notifyObservers(message);
                    break;
                case NEW_ROUND:
                    setChanged();
                    notifyObservers(messageWrapper.newRoundMessage());
                    break;
                case END_GAME:
                    setChanged();
                    notifyObservers(messageWrapper.endGameMessage(getWinner().getNickname()));
            }
        }
    }

    public Model(GameMode mode, String[] nicknames, int numberOfPlayers){
        this.mode = mode;
        this.numberOfPlayers = numberOfPlayers;
        playerNames = nicknames;
        playerList = new ArrayList<>();
        setupBoard();
        roundManager = new RoundManager(playerList);
        turnUpdates = new TurnUpdates();
        roundManager.addObserver(turnUpdates);
        board.addObserver(this);
        messageWrapper = new ViewUpdateMessageWrapper();
    }

    @Override
    public void update(Observable o, Object arg) {
        switch (mode){
            case SIMPLE:
                setChanged();
                notifyObservers(messageWrapper.boardUpdateSimple(this));
                break;
            case EXPERT:
                setChanged();
                notifyObservers(messageWrapper.boardUpdateExpert(this));
                break;
        }
    }

    public Board getBoard(){
            setChanged();
            return board;
    }

    public RoundManager getRoundManager() {
        return roundManager;
    }

    private void setupBoard(){
        setupPlayers();
        computeSettings();
        if(mode.equals(GameMode.SIMPLE)) {
            board = new Board(playerList, numberOfClouds, numberOfTowers, studentsOnCloud,
                    studentsInEntrance, mode);
        }
        else if (mode.equals(GameMode.EXPERT)){
            board = new BoardExpert(playerList, numberOfClouds, numberOfTowers, studentsOnCloud,
                    studentsInEntrance, mode);
        }
    }
/* according to the choices of mode and number of players, this method computes those values for the board*/
    private void computeSettings(){
        studentsOnCloud = numberOfPlayers + 1; //to change if teams
        numberOfClouds = numberOfPlayers;

        switch (numberOfPlayers){
            case 2:
                numberOfTowers = 8;
                studentsInEntrance = 7;
                break;
            case 3:
                numberOfTowers = 6;
                studentsInEntrance = 9;
        }
    }

    private void setupPlayers(){
        for(String s : playerNames){
            playerList.add(new Player(s));
        }
    }


    /* this method will be called by the controller each time a round is over; if it
    * does return true, the controller will proceed to call the method which determines the winner */
    public boolean isGameOver(){
        /*the game ends if a player runs out of towers*/
        for(SchoolBoard sb: board.getSchoolBoards()){
            if(sb.getTowerSection().isTowerSectionEmpty()){
                return true;
            }
        }

        /*or when only three islands are left after the merging */
        if(board.getIslandList().size() == 3)
            return true;

        /* or when there are no more students in the bag */
        if(board.getStudentBag().checkIfStudentBagEmpty())
            return true;

        /* or if any player runs out of assistant cards */
        for(AssistantCardDeck deck : board.getDecks()){
            if(deck.checkIfDeckIsEmpty()) return true;
        }

        return false;
    }

    /* the winner is the player with the least towers in their tower section. In case of a tie regarding the
    * number of towers, the winner is whoever controls the most professors. Method
    * returns a reference to the winner  */
    public Player getWinner(){
        int minTowers = board.getPlayerSchoolBoard(playerList.get(0).getNickname()).getTowerSection().getNumberOfTowers();
        Player winner = playerList.get(0);
        ArrayList<Player> tiedPlayers = new ArrayList<>();
        boolean tie = false;

        for(int i = 1; i < numberOfPlayers; i++){
            if(board.getPlayerSchoolBoard(playerList.get(i).getNickname()).getTowerSection().getNumberOfTowers() < minTowers){
                minTowers = board.getPlayerSchoolBoard(playerList.get(i).getNickname()).getTowerSection().getNumberOfTowers();
                winner = playerList.get(i);
                tie = false;
                tiedPlayers.clear();
            }
            else if(board.getPlayerSchoolBoard(playerList.get(i).getNickname()).getTowerSection().getNumberOfTowers() == minTowers){
                tie = true;
                tiedPlayers.add(playerList.get(i));
            }
        }

        /* in case of a tie, one of the possible winners is stored in the winner local variable.
        * To determine the actual winner we need to find the player we are tying with and confront the
        * number of professors that is controlled */
        if(tie){
            int count = 0;
            int max = -1;
            int temp;
            for(Player p : tiedPlayers){
                temp = board.getPlayerSchoolBoard(p.getNickname()).getProfessorTable().getNumberOfProfessors();
                if(temp > max){
                    max = temp;
                    winner = p;
                }
            }
        }
        return winner;
    }

    public int getNumberOfClouds (){
        return numberOfClouds;
    }

    public GameMode getMode(){return mode;}

    public TurnUpdates getTurnUpdates(){
        return turnUpdates;
    }
}
