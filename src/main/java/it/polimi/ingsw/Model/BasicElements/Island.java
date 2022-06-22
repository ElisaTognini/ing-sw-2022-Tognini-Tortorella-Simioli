package it.polimi.ingsw.Model.BasicElements;

import it.polimi.ingsw.Utils.Enums.PawnDiscColor;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Model.StudentContainer;

/** Class Island contains references to the player who conquered the island (null if it has not been conquered yet),
* whether if mother nature is present on the island or not, if the island is conquered or not, the number of towers
* present on the island and a reference to a custom data structure called Student Container, where the students
* are divided in five hash set, separated by color.
* Moreover, class Island has a few references to variables used by methods called in Expert Mode, which work with
* the character cards. */

public class Island {

    private Player owner;
    private int islandID;
    private boolean hostsMotherNature;
    private boolean conqueredIsland;
    private StudentContainer container;
    private int noEntryTile;
    private int numberOfTowers;
    private int towersOnHold;
    private int influence;
    private PawnDiscColor ignoredInfluence;

    /** Constructor Island creates a new instance of an island.
     *
     * @param islandID - of type int - the number identifying the island */
    public Island(int islandID) {
        this.islandID = islandID;
        hostsMotherNature = false;
        conqueredIsland = false;
        container = new StudentContainer();
        noEntryTile = 0;
        numberOfTowers = 0;
        towersOnHold = 0;
        influence = 0;
        ignoredInfluence = null;
    }


    /** Method checkIfIslandIsEmpty checks if island is empty by computing the size of the container associated to
     * the island: if the five hash map of the container are all size 0, then the island is actually empty .
     *
     * @return boolean - the result of the iteration: true if the island is empty, false otherwise */
    public boolean checkIfIslandIsEmpty(){
        return container.size() == 0;
    }


    /** Method addStudent, called when a player moves a student from the entrance to an island of choice,
     * adds a student to the hash set of the student's color in the container of the said island.
     *
     * @param myStudent - of type Student - the student selected by the player */
    public void addStudent(Student myStudent){
        container.addStudent(myStudent);
    }


    /** Method removeStudent retrieves a student from the island by removing it from the hash set of the corresponding
    * color of the container on the island. As per rules, students can be moved from islands only in Expert Mode
    * when provided by a character card, but it is used by the Board class during merges.
    *
    * @param color - of type PawnDiscColor - the color of the student to be removed
    * @return Student - the reference to the actual Student removed by the entrance */
    public Student removeStudent(PawnDiscColor color){
        return container.retrieveStudent(color);
    }


    /** Method getInfluenceByColor returns the number of students of the color given that are on the island, in
    * order to calculate the influence the player has on the island for that color. Influence is calculated if
    * the player actually has the professor of corresponding color.
    * Variable ignoredInfluence is null by default: it changes only when Character Card 9 is played.
    *
    * @param color - of type PawnDiscColor - the color the influence has to be calculated of
    * @return Integer - the number of student of the color given, present on the island*/
    public int getInfluenceByColor(PawnDiscColor color){
        if(ignoredInfluence != null){
            if(ignoredInfluence.equals(color)) {
                ignoredInfluence = null;
                return 0;
            }
        }
            influence = container.getInfluence(color);
            return influence;
    }


    /** setter method - sets true if mother nature is on the island; if so, the island can be conquered and
    * methods to resolve influence and conquering will be called on said island. Otherwise, those methods
    * won't be called and the island cannot be conquered in that turn*/
    public void setHostsToTrue(){ hostsMotherNature = true; }
    public void setHostsToFalse(){ hostsMotherNature = false; }

    /** getter method - getHost checks if mother nature is on the island7
    *
    * @return boolean - true if mother nature is on this island, false otherwise*/
    public boolean getHost(){ return hostsMotherNature; }


    /** Method addNoEntryTile, only called in Expert Method if character card 5 has been played, increases the
    * number of No Entry Tiles on island.*/
    public void addNoEntryTile(){
        noEntryTile = noEntryTile + 1;
    }


    /** Method removeNoEntryTile, only called in Expert Method if character card 5 has been played, removes a no
     * Entry Tile from an island after mother nature ended up there. */
    public void removeNoEntryTile(){ noEntryTile --; }


    /** Method hasNoEntryTile, only called in Expert Method if character card 5 has been played, checks if an
    * island has one (or more, corner case) no Entry Tiles placed on it.
    * Variable noEntryTile is set by default to 0.
    *
    * @return boolean - true if there is one or more no Entry Islands, false otherwise */
    public boolean hasANoEntryTile(){
        return noEntryTile != 0;
    }


    /** getter method - getOwner returns the Player who owns the island
     *
     * @return Player - the owner of the island */
    public Player getOwner(){
        return owner;
    }


    /** Method getsConquered is used by the Board to assign a Player owner to an island after it has been conquered
    *
    * @param owner - of type Player - the player who has the most influence on the island*/
    public void getsConquered(Player owner){
        this.owner = owner;
        conqueredIsland = true;
    }


    /** getter method - getIslandID returns the island ID
    *
    * @return Integer - island ID */
    public int getIslandID(){
        return islandID;
    }


    /** Method checkIfConquered checks if the island is conquered, therefore if the island has an owner.
    *
    * @return boolean - true if it is conquered, false otherwise */
    public boolean checkIfConquered(){ return conqueredIsland;}


    /** Method increaseNumberOfTowers increases the number of towers on an island.
     * This method is called in case of merge among adjacent islands, where one of the islands stay and increases
     * towers and number of students, while the other(s) are removed from the array list of islands referenced in the
     * Board class.
     *
     * @param number - of type int - the number the tower(s) on the island must be increased by */
    public void increaseNumberOfTowers(int number){
        numberOfTowers += number;
    }


    /** getter method - getNumberOfTowers returns the number of tower(s) present on the island
    *
    * @return Integer - number of towers */
    public int getNumberOfTowers(){
        return numberOfTowers;
    }

    /** getter method - getTowersOnHold returns the number of towers must be ignored during the calculation of the
     * influence on island. Variable towersOnHold is set to 0 by default, can only be modified in Expert Mode when
     * Character Card 6 has been played.
     *
     * @return Integer - number of towers which do not count for influence */
    public int getTowersOnHold(){ return towersOnHold; }


    /** setter method - setTowersOnHold, only called in Expert Mode when Character Card 6 has been played, sets
     * the number the variable must have for the resolving of the influence in that turn.
     *
     * @param n - of type int - number of towers on the island */
    public void setTowersOnHold(int n) { towersOnHold = n; }


    /** ignoreInfluence takes in input the color chosen by the player to be excluded when calculating
     * the influence on an island.
     * Variable ignoredInfluence is set to null by default and only changed if character card 9 is played.
     *
     * @param color - of type PawnDiscColor - color chosen by the player to be excluded */
    public void ignoreInfluence(PawnDiscColor color){
        ignoredInfluence = color;
    }


    /** setter method - setIgnoredInfluence sets variable ignoreInfluence to null in setup or after character
     * card 9 has been used. */
    public void setIgnoredInfluencetoNull(){ignoredInfluence = null;}


    /** getter method - getNumberOfNEtiles returns the number of no entry tiles present on an island
     *
     * @return int - number of no entry tiles */
    public int getNumberOfNEtiles(){return noEntryTile;}
    public void setIslandID(int id){this.islandID = id;}


    /** Method toString builds a String containing all the info stored in this class
     *
     * @return String - FORMAT: ID, BLUE, GREEN, YELLOW, PINK and RED students on the island, owner and numberOfTowers
     * (only if the island has been conquered), each separated by a blank space */
    @Override
    public String toString(){
        String toRet = islandID + " " + container.getInfluence(PawnDiscColor.BLUE) + " " +
                container.getInfluence(PawnDiscColor.GREEN) + " " +
                container.getInfluence(PawnDiscColor.YELLOW) + " " +
                container.getInfluence(PawnDiscColor.PINK) + " " +
                container.getInfluence(PawnDiscColor.RED);

        if(conqueredIsland){
            toRet += " " + owner.getNickname() + " " + numberOfTowers;
        }
        return toRet;
    }
}

