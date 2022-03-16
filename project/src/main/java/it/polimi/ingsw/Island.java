package it.polimi.ingsw;

import java.util.HashSet;
import java.util.Set;

//students are contained in five hash sets, separated by color

public class Island {

    //private Player owner;
    private int islandID;
    private boolean hostsMotherNature;
    private boolean conqueredIsland;
    private Set<Student> pinkStudents;
    private Set<Student> greenStudents;
    private Set<Student> yellowStudents;
    private Set<Student> blueStudents;
    private Set<Student> redStudents;


    public Island(int islandID) {
        this.islandID = islandID;
        hostsMotherNature = false;
        conqueredIsland = false;
        pinkStudents = new HashSet<Student>();
        greenStudents = new HashSet<Student>();
        yellowStudents = new HashSet<Student>();
        blueStudents = new HashSet<Student>();
        redStudents = new HashSet<Student>();
    }

    public void addStudent(Student myStudent){

        switch (myStudent.getColor()){

            case RED:
                redStudents.add(myStudent);
                break;
            case YELLOW:
                yellowStudents.add(myStudent);
                break;
            case PINK:
                pinkStudents.add(myStudent);
                break;
            case BLUE:
                blueStudents.add(myStudent);
                break;
            case GREEN:
                greenStudents.add(myStudent);
                break;
        }

    }

    //this method returns the number of students of the color given as parameter that are on the island
    //the color taken as parameter is given by the board and is the color of a professor
    public int getInfluenceByColor(PawnDiscColor color){
        int toRet = 0;
        switch (color){

            case RED:
                toRet = redStudents.size();
                break;
            case YELLOW:
                toRet = yellowStudents.size();
                break;
            case PINK:
                toRet = pinkStudents.size();
                break;
            case BLUE:
                toRet = blueStudents.size();
                break;
            case GREEN:
                toRet = greenStudents.size();
                break;
        }

        //if player is the owner we need to add 1 to the influence
        //if (owner.equals(game.getCurrentPlayer())){
            toRet++;
        //}

        return toRet;
    }

    public void setHostsToTrue(){
        hostsMotherNature = true;
    }

    public void setHostsToFalse(){
        hostsMotherNature = false;
    }

    /*public Player getOwner(){
        return owner;
    }

    public void getsConquered(Player owner){
        this.owner = owner;
        conqueredIsland = true;
    }*/
}
