package it.polimi.ingsw.Model.SchoolBoardClasses;
import it.polimi.ingsw.Utils.Enums.TowerColor;

public class TowerSection {

    private final int maxNumberOfTowers;
    private int numberOfTowers;
    private TowerColor color;

    public TowerSection(int maxNumberOfTowers, TowerColor color) {
        this.maxNumberOfTowers = maxNumberOfTowers;
        this.numberOfTowers = maxNumberOfTowers;
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

    public boolean isTowerSectionEmpty(){
        if(numberOfTowers == 0) return true;
        return false;
    }

    public int getNumberOfTowers(){
        return numberOfTowers;
    }

    @Override
    /*FORMAT numberoftowers towercolor*/
    public String toString(){
        return color.toString() + " " + numberOfTowers;
    }
}
