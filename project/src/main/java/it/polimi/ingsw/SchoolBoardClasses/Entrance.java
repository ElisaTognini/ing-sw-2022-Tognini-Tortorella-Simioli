package it.polimi.ingsw.SchoolBoardClasses;

import it.polimi.ingsw.Enums.PawnDiscColor;
import it.polimi.ingsw.StudentContainer;
import it.polimi.ingsw.BasicElements.Student;

import java.util.ArrayList;

public class Entrance {

    private StudentContainer studentsInEntrance;

    public Entrance() {
        this.studentsInEntrance = new StudentContainer();
    }

    public void addStudent(Student myStudent){
        if (studentsInEntrance.size()<7) studentsInEntrance.addStudent(myStudent);
    }

    public Student removeStudent(PawnDiscColor color){
        return studentsInEntrance.retrieveStudent(color);
    }

    public boolean checkIfEntranceIsEmpty(){
        if(studentsInEntrance.size() == 0) return true;
        else return false;
    }
}
