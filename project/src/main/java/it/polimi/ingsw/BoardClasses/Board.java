package it.polimi.ingsw.BoardClasses;
import it.polimi.ingsw.BasicElements.*;
import java.util.LinkedList;

public class Board {

    private LinkedList<Island> islandPlacement;
    private StudentBag bag;
    private MotherNature motherNature;
    private final int islandNumber = 12;



    private Board(){

    }

    public void setup(){

    }

    public void roundSetup(){

    }

    public void choosePlayerOrder(){   // not void function

    }

    protected void placeIslands(){

        for (int i = 0; i < islandNumber; i++){
            islandPlacement.add(new Island(i));
        }

    }




}
