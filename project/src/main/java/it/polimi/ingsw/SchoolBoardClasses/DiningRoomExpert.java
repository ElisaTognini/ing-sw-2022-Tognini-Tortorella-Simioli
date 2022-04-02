package it.polimi.ingsw.SchoolBoardClasses;

import it.polimi.ingsw.BoardClasses.*;
import it.polimi.ingsw.Enums.PawnDiscColor;
import it.polimi.ingsw.BasicElements.Student;
import it.polimi.ingsw.StudentContainer;
import it.polimi.ingsw.TailoredExceptions.EmptyException;

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

    /* expert mode cards can remove students from this section, adding a method
    * to handle that as part of a Strategy pattern */
    public Student removeStudentByColor(PawnDiscColor color) throws EmptyException{
        if(super.influenceForProf(color) < 1){
            throw new EmptyException();
        }
        else{
            return super.getContainer().retrieveStudent(color);
        }
    }
}
