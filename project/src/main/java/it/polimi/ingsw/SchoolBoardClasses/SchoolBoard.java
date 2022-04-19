package it.polimi.ingsw.SchoolBoardClasses;

import it.polimi.ingsw.Enums.*;
import it.polimi.ingsw.Player;

public class SchoolBoard {

    private Entrance entrance;
    private DiningRoom diningRoom;
    private ProfessorTable professorTable;
    private TowerSection towerSection;
    private Player owner;
    private boolean modifiedTable;

    public SchoolBoard(int studentsInEntrance, int numberOfTowers, TowerColor towerColor, GameMode mode, Player owner){
        entrance = new Entrance(studentsInEntrance);
        professorTable = new ProfessorTable();
        towerSection = new TowerSection(numberOfTowers, towerColor);
        diningRoom = new DiningRoom();
        this.owner = owner;
        modifiedTable = false;
    }

    public Player getOwner(){
        return owner;
    }

    public Entrance getEntrance(){
        return entrance;
    }

    public DiningRoom getDiningRoom(){
        return diningRoom;
    }

    public ProfessorTable getProfessorTable() {
        return professorTable;
    }

    public TowerSection getTowerSection(){
        return towerSection;
    }

    /* flag set if player plays character card 2, in order to reset the professor's table previous state */
    public void setModifiedTable(){
        modifiedTable = true;
    }

    public boolean getModifiedTable(){return modifiedTable;}
}
