package it.polimi.ingsw.SchoolBoardClasses;

import it.polimi.ingsw.Enums.PawnDiscColor;
import it.polimi.ingsw.StudentContainer;
import it.polimi.ingsw.TailoredExceptions.EmptyException;
import it.polimi.ingsw.BasicElements.Student;

import java.util.ArrayList;

public class Entrance {

    private StudentContainer studentsInEntrance;

    public Entrance() {
        this.studentsInEntrance = new StudentContainer();
    }

    public void addStudent(Student myStudent) throws ArrayIndexOutOfBoundsException{
        if (studentsInEntrance.size()>=7) throw new ArrayIndexOutOfBoundsException();
        else {
            studentsInEntrance.addStudent(myStudent);
        }
    }

    public Student removeStudent(PawnDiscColor color) throws EmptyException {
        if(studentsInEntrance.size() == 0) throw new EmptyException();
        return studentsInEntrance.retrieveStudent(color);
    }

}
