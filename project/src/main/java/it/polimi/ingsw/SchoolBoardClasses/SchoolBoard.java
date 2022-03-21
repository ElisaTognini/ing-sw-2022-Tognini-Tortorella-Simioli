package it.polimi.ingsw.SchoolBoardClasses;

import it.polimi.ingsw.Enums.GameMode;
import it.polimi.ingsw.Enums.TowerColor;

public class SchoolBoard {

    private Entrance entrance;
    private DiningRoom diningRoom;
    private ProfessorTable professorTable;
    private TowerSection towerSection;

    public SchoolBoard(int numberOfTowers, TowerColor towerColor, GameMode mode){
        entrance = new Entrance();
        professorTable = new ProfessorTable();
        towerSection = new TowerSection(numberOfTowers, towerColor);

        if(mode.equals(GameMode.EXPERT)){
            diningRoom = new DiningRoomExpert();
        }
        else if (mode.equals(GameMode.SIMPLE)){
            diningRoom = new DiningRoom();
        }

    }

}
