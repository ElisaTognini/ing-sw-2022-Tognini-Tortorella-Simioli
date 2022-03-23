package it.polimi.ingsw.BasicElements;

import it.polimi.ingsw.Enums.PawnDiscColor;
import it.polimi.ingsw.Player;
import it.polimi.ingsw.StudentContainer;

import java.util.HashSet;
import java.util.Set;

//students are contained in five hash sets, separated by color

public class Island {

    private Player owner;
    private int islandID;
    private boolean hostsMotherNature;
    private boolean conqueredIsland;
    private StudentContainer container;

    /*student container data structure is used*/
    public Island(int islandID) {
        this.islandID = islandID;
        hostsMotherNature = false;
        conqueredIsland = false;
        container = new StudentContainer();
    }

    /*adds student into each set by color*/
    public void addStudent(Student myStudent){
        container.addStudent(myStudent);
    }

    //this method returns the number of students of the color given as parameter that are on the island
    //the color taken as parameter is given by the board and is the color of a professor
    public int getInfluenceByColor(PawnDiscColor color){
        return container.getInfluence(color);
    }

    /*sets to true if mother nature is on island to know if island
    * can be conquered*/
    public void setHostsToTrue(){
        hostsMotherNature = true;
    }
    public void setHostsToFalse(){
        hostsMotherNature = false;
    }

    /*returns true if island hosts mothernature*/
    public boolean checkForMotherNature(){
        return hostsMotherNature;
    }

    public Player getOwner(){
        return owner;
    }

    //board assigns owner
    public void getsConquered(Player owner){
        this.owner = owner;
        conqueredIsland = true;
    }
}

