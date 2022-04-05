package it.polimi.ingsw;

import it.polimi.ingsw.BoardClasses.*;
import it.polimi.ingsw.Enums.*;
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

    //public boolean checkForWinner(){

    //}

}
