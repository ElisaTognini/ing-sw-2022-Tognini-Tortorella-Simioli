package it.polimi.ingsw.SchoolBoardClasses;

import it.polimi.ingsw.BoardClasses.*;
import it.polimi.ingsw.Enums.PawnDiscColor;
import it.polimi.ingsw.BasicElements.Student;
import it.polimi.ingsw.StudentContainer;

public class DiningRoomExpert extends DiningRoom {

    private StudentContainer container;

    public DiningRoomExpert(){
        super();
    }

    @Override
    public void addStudent(Student myStudent) throws IndexOutOfBoundsException {
        super.addStudent(myStudent);
       //if(checkIfMod3(myStudent.getColor())) assignCoin(Board.getCurrentPlayer());
    }

    public boolean checkIfMod3(PawnDiscColor color){
        if(container.getInfluence(color) % 3 == 0){
            return true;
        }
        return false;
    }
}
