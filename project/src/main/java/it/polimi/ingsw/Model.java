package it.polimi.ingsw;

import it.polimi.ingsw.BasicElements.AssistantCardDeck;
import it.polimi.ingsw.BoardClasses.*;
import it.polimi.ingsw.Enums.*;
import it.polimi.ingsw.SchoolBoardClasses.SchoolBoard;

import java.util.*;

/* will implement Observable */
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
    public String getWinner(){
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
        return winner.getNickname();
    }

    public void useCard(Object o, String nickname, int cardID){
        board.useCard(o,nickname,cardID);
    }

}
