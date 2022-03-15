package it.polimi.ingsw;

import java.util.LinkedList;

public class Board {

    private static Board instance; // Board is a Singleton

//    private LinkedList<Island> IslandPlacement

    private Board(){

    }

    public static Board instance(){
        if (instance == null) instance = new Board();
        return instance;
    }

}
