package it.polimi.ingsw.Model.SchoolBoardClasses;

import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Utils.Enums.GameMode;
import it.polimi.ingsw.Utils.Enums.TowerColor;

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
    public void resetModifiedTable(){modifiedTable = false;}

    public boolean getModifiedTable(){return modifiedTable;}

    @Override
    /* FORMAT owner
    * entrance
    * dining room
    * professor table
    * tower section*/
    public String toString(){
        return owner.getNickname() + "\n" +
                entrance.toString() + "\n" +
                diningRoom.toString() + "\n" +
                professorTable.toString() + "\n" +
                towerSection.toString();
    }
}