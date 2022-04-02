package it.polimi.ingsw.BasicElements;

import it.polimi.ingsw.Enums.PawnDiscColor;
import it.polimi.ingsw.Player;
import it.polimi.ingsw.StudentContainer;

import java.util.HashSet;
import java.util.Set;

//students are contained in five hash sets, separated by color

public class Island {

    private Player owner;
    private final int islandID;
    private boolean hostsMotherNature;
    private boolean conqueredIsland;
    private StudentContainer container;
    private boolean noEntrytile;

    /*student container data structure is used*/
    public Island(int islandID) {
        this.islandID = islandID;
        hostsMotherNature = false;
        conqueredIsland = false;
        container = new StudentContainer();
        noEntrytile = false;
    }

    /*adds student into each set by color*/
    public void addStudent(Student myStudent){
        container.addStudent(myStudent);
    }

    //this method returns the number of students of the color given as parameter that are on the island
    //the color taken as parameter is given by the board and is the color of a professor
    /*adding 1 to the influence if the island is conquered by the current player: to be implemented*/
    public int getInfluenceByColor(PawnDiscColor color){
        return container.getInfluence(color);
    }

    /*sets to true if mother nature is on island to know if island
    * can be conquered*/
    public void setHostsToTrue(){ hostsMotherNature = true; }
    public void setHostsToFalse(){ hostsMotherNature = false; }
    public boolean getHost(){ return hostsMotherNature; }

    /*returns true if island hosts mother nature*/
    public boolean checkForMotherNature(){ return hostsMotherNature; }

    /* sets to true if an island has got a no entry tile */
    public void setNoEntrytileToTrue(){ noEntrytile = true; }

    /* sets to false if an island hasn't got a no entry tile on it */
    public void setNoEntrytileToFalse(){ noEntrytile = false; }

    /* returns true if an island is blocked by a No Entry Tile (Expert Mode) */
    public boolean isANoEntryTile(){ return noEntrytile; }

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

    public void setConquered(){conqueredIsland = true;}
    public void setNotConquered(){conqueredIsland = false;}

}

