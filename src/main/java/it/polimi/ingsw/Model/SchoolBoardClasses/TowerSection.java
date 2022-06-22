package it.polimi.ingsw.Model.SchoolBoardClasses;
import it.polimi.ingsw.Utils.Enums.TowerColor;

/** Class TowerSection is one of the classes which compose the player's school board: it contains an attribute for
 * the maximum towers to be added in the tower section, the actual number of towers inside the tower section and
 * a reference to their color.*/

public class TowerSection {

    private final int maxNumberOfTowers;
    private int numberOfTowers;
    private TowerColor color;


    /** Constructor TowerSection creates a new instance of tower section based on the maximum number of tower it
     * can contain and their color.
     *
     * @param maxNumberOfTowers of type int - the maximum number of towers, based on the number of players
     * @param color of type PawnDiscColor - the color of the towers */
    public TowerSection(int maxNumberOfTowers, TowerColor color) {
        this.maxNumberOfTowers = maxNumberOfTowers;
        this.numberOfTowers = maxNumberOfTowers;
        this.color = color;
    }


    /** Method returnTowers puts tower(s) back to previous owner's tower section when an island get conquered by
     * another player.
     *
     * @param number of type int - the number of towers to put back */
    public void returnTowers(int number){
        numberOfTowers += number;
    }

    /** Method towersToIsland decrements number of towers in tower section when the player conquers an island
     *
     * @param number of type int - the number of towers in the tower section */
    public void towersToIsland(int number){
        numberOfTowers -= number;
    }


    /** Method isTowerSectionEmpty checks if there are no more towers inside the towers section
     *
     * @return boolean - true if the tower section is empty, false otherwise */
    public boolean isTowerSectionEmpty(){
        if(numberOfTowers == 0) return true;
        return false;
    }


    /** getter method - Method getNumberOfTowers returns value of attribute numberOfTowers
     *
     * @return int - towers in the tower section */
    public int getNumberOfTowers(){
        return numberOfTowers;
    }


    /** Method toString builds a String containing all the info stored in this class
     *
     *  @return String - FORMAT: the color and the number of the towers inside the tower section, each separated
     *  by a blank space.
     *  */
    @Override
    public String toString(){
        return color.toString() + " " + numberOfTowers;
    }
}
