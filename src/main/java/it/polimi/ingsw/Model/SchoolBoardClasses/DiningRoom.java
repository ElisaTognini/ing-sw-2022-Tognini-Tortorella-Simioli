package it.polimi.ingsw.Model.SchoolBoardClasses;

import it.polimi.ingsw.Model.BasicElements.Student;
import it.polimi.ingsw.Utils.Enums.PawnDiscColor;
import it.polimi.ingsw.Model.StudentContainer;

public class DiningRoom {

    private StudentContainer container;

    public DiningRoom() {
        container = new StudentContainer();
    }

    public void addStudent(Student myStudent){
        container.addStudent(myStudent);

    }

    public boolean checkIfMod3(PawnDiscColor color){
        if(container.getInfluence(color) % 3 == 0){
            return true;
        }
        return false;
    }

    public int influenceForProf(PawnDiscColor color){
        return container.getInfluence(color);
    }

    public StudentContainer getContainer(){
        return container;
    }

    public boolean checkIfDiningRoomIsFull(PawnDiscColor color){
        if(container.getInfluence(color) >= 10) return true;
        else return false;
    }

    @Override
    /* FORMAT pink yellow blue green red */
    public String toString(){
        return container.getInfluence(PawnDiscColor.PINK) + " " +
               container.getInfluence(PawnDiscColor.YELLOW) + " " +
               container.getInfluence(PawnDiscColor.BLUE) + " " +
               container.getInfluence(PawnDiscColor.GREEN) + " " +
               container.getInfluence(PawnDiscColor.RED);
    }
}
