package it.polimi.ingsw;

import it.polimi.ingsw.BasicElements.AssistantCardDeck;
import it.polimi.ingsw.BoardClasses.*;
import it.polimi.ingsw.Enums.*;
import it.polimi.ingsw.SchoolBoardClasses.SchoolBoard;

import javax.swing.plaf.basic.BasicTreeUI;
import java.util.*;

public class Model {
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

    public Model(GameMode mode, String[] nicknames, int numberOfPlayers){
        this.mode = mode;
        this.numberOfPlayers = numberOfPlayers;
        playerNames = nicknames;
        playerList = new ArrayList<>();
        setupBoard();
        roundManager = new RoundManager(playerList);
    }

    public Board getBoard(){
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

        /*or when only three islands are left after the mergings */
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

    }

    /*still needs a method to use a character card*/

}
