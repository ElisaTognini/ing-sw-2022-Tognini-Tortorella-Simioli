package it.polimi.ingsw.SchoolBoardClasses;

import it.polimi.ingsw.TailoredExceptions.EmptyException;
import it.polimi.ingsw.TailoredExceptions.TooManyTowersException;
import it.polimi.ingsw.Enums.TowerColor;

public class TowerSection {

    private final int maxNumberOfTowers;
    private int numberOfTowers;
    private TowerColor color;

    public TowerSection(int maxNumberOfTowers, TowerColor color) {
        this.maxNumberOfTowers = maxNumberOfTowers;
        this.color = color;
    }

    //Puts Tower back when an island get conquered by another player
    public void returnTower() throws TooManyTowersException {
        if(numberOfTowers+1>=maxNumberOfTowers) throw new TooManyTowersException();
        else numberOfTowers++;
    }

    // Decrements number of towers when the player conquers an island
    public void towerToIsland() throws EmptyException {
        if(numberOfTowers==0) throw new EmptyException();
        numberOfTowers--;
    }
}
