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
    public void returnTowers(int number){
        numberOfTowers += number;
    }

    // Decrements number of towers when the player conquers an island
    public void towersToIsland(int number){
        numberOfTowers -= number;
    }

    public boolean checkIfTowerSectionIsFull(){
        if(numberOfTowers+1>=maxNumberOfTowers) return true;
        else return false;
    }

    public boolean checkIfThereAreEnoughTowers(int number){
        if(numberOfTowers <= numberOfTowers - number) return true;
        else return false;
    }
}
