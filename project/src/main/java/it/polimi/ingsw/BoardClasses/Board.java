package it.polimi.ingsw.BoardClasses;

import it.polimi.ingsw.BasicElements.*;
import it.polimi.ingsw.Player;
import it.polimi.ingsw.SchoolBoardClasses.SchoolBoard;

import java.util.ArrayList;

public class Board {

    private ArrayList<Player> players;
    private ArrayList<SchoolBoard> schoolBoards;
    private ArrayList<Island> islands;
    private StudentBag studentBag;
    private ArrayList<CloudTile> clouds;
    private ArrayList<Professor> professors;
    private MotherNature motherNature;
    private ArrayList<AssistantCardDeck> decks;
    private TurnManager turnManager;

    public Board(ArrayList<Player> players){
        turnManager = new TurnManager(players);
    }

}
