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

    public void addStudent(Student myStudent) throws IndexOutOfBoundsException{

        if(container.getInfluence(myStudent.getColor()) >= 10){
            throw new IndexOutOfBoundsException();
        };

        container.addStudent(myStudent);

    }

    public int influenceForProf(PawnDiscColor color){
        return container.getInfluence(color);
    }
}
