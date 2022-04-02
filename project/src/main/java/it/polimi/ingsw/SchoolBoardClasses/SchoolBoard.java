package it.polimi.ingsw.SchoolBoardClasses;

import it.polimi.ingsw.Enums.*;
import it.polimi.ingsw.Player;

public class SchoolBoard {

    private Entrance entrance;
    private DiningRoom diningRoom;
    private ProfessorTable professorTable;
    private TowerSection towerSection;
    private Player owner;

    public SchoolBoard(int numberOfTowers, TowerColor towerColor, GameMode mode, Player owner){
        entrance = new Entrance();
        professorTable = new ProfessorTable();
        towerSection = new TowerSection(numberOfTowers, towerColor);
        this.owner = owner;

        if(mode.equals(GameMode.EXPERT)){
            diningRoom = new DiningRoomExpert();
        }
        else if (mode.equals(GameMode.SIMPLE)){
            diningRoom = new DiningRoom();
        }

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
}
