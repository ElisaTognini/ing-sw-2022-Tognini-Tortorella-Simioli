package it.polimi.ingsw.SchoolBoardClasses;

import it.polimi.ingsw.BasicElements.Student;
import it.polimi.ingsw.Enums.PawnDiscColor;
import it.polimi.ingsw.StudentContainer;

import java.util.HashSet;

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
    public String toString(){
        return "in dining room: \n"+
                "\nPINK " + container.getInfluence(PawnDiscColor.PINK) +
                "\nYELLOW " + container.getInfluence(PawnDiscColor.YELLOW) +
                "\nBLUE " + container.getInfluence(PawnDiscColor.BLUE) +
                "\nGREEN " + container.getInfluence(PawnDiscColor.GREEN) +
                "\nRED " + container.getInfluence(PawnDiscColor.RED);
    }
}
