package it.polimi.ingsw.Model.BasicElements;

import it.polimi.ingsw.Utils.Enums.PawnDiscColor;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Model.StudentContainer;

//students are contained in five hash sets, separated by color

public class Island {

    private Player owner;
    private final int islandID;
    private boolean hostsMotherNature;
    private boolean conqueredIsland;
    private StudentContainer container;
    private int noEntryTile;
    private int numberOfTowers;
    private int towersOnHold;
    private int influence;
    private int ignoredInfluence;

    /*student container data structure is used*/
    public Island(int islandID) {
        this.islandID = islandID;
        hostsMotherNature = false;
        conqueredIsland = false;
        container = new StudentContainer();
        noEntryTile = 0;
        numberOfTowers = 0;
        towersOnHold = 0;
        influence = 0;
        ignoredInfluence = 0;
    }

    /* checks if the island is empty*/
    public boolean checkIfIslandIsEmpty(){
        if(container.size() == 0) return true;
        else return false;
    }

    /*adds student into each set by color*/
    public void addStudent(Student myStudent){
        container.addStudent(myStudent);
    }
    public Student removeStudent(PawnDiscColor color){
        return container.retrieveStudent(color);
    }

    //this method returns the number of students of the color given as parameter that are on the island
    //the color taken as parameter is given by the board and is the color of a professor
    /*adding 1 to the influence if the island is conquered by the current player: to be implemented*/
    public int getInfluenceByColor(PawnDiscColor color){
            influence = container.getInfluence(color) + ignoredInfluence;
            ignoredInfluence = 0;
            return influence;
    }

    /*sets to true if mother nature is on island to know if island
    * can be conquered*/
    public void setHostsToTrue(){ hostsMotherNature = true; }
    public void setHostsToFalse(){ hostsMotherNature = false; }
    public boolean getHost(){ return hostsMotherNature; }

    /*returns true if island hosts mother nature*/
    public boolean checkForMotherNature(){ return hostsMotherNature; }

    /* sets to true if an island has got a no entry tile */
    public void addNoEntryTile(){ noEntryTile ++; }

    /* sets to false if an island hasn't got a no entry tile on it */
    public void removeNoEntryTile(){ noEntryTile --; }

    /* returns true if an island is blocked by a No Entry Tile (Expert Mode) */
    public boolean hasANoEntryTile(){
        if(noEntryTile != 0 ) return true;
        else return false;
    }

    public Player getOwner(){
        return owner;
    }

    //board assigns owner
    public void getsConquered(Player owner){
        this.owner = owner;
        conqueredIsland = true;
    }

    public int getIslandID(){
        return islandID;
    }

    public boolean checkIfConquered(){ return conqueredIsland;}

    public void increaseNumberOfTowers(int number){
        numberOfTowers += number;
    }

    public int getNumberOfTowers(){
        return numberOfTowers;
    }
    public int getTowersOnHold(){ return towersOnHold; }
    public void setTowersOnHold(int n) { towersOnHold = n; }

    public void ignoreInfluence(PawnDiscColor color){
        ignoredInfluence = -container.getInfluence(color);
    }
    public void setIgnoredInfluencetoZero(){ignoredInfluence = 0;}
    public int getNumberOfNEtiles(){return noEntryTile;}

    @Override
    /* FORMAT id BLUE GREEN YELLOW PINK RED Owner numberOfTowers NumberOfNE*/
    public String toString(){
        String toRet = islandID + " " + container.getInfluence(PawnDiscColor.BLUE) + " " +
                container.getInfluence(PawnDiscColor.GREEN) + " " +
                container.getInfluence(PawnDiscColor.YELLOW) + " " +
                container.getInfluence(PawnDiscColor.PINK) + " " +
                container.getInfluence(PawnDiscColor.RED);

        if(conqueredIsland){
            toRet += " " + owner.getNickname() + " " + numberOfTowers;
        }
        if(noEntryTile > 0){
            toRet += " " + noEntryTile;
        }

        return toRet;
    }
}

