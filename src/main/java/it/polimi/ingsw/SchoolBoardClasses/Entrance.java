package it.polimi.ingsw.SchoolBoardClasses;

import it.polimi.ingsw.Enums.PawnDiscColor;
import it.polimi.ingsw.StudentContainer;
import it.polimi.ingsw.BasicElements.Student;

import java.util.ArrayList;

public class Entrance {

    private StudentContainer studentsInEntrance;
    private int studentNumber;

    public Entrance(int studentNumber) {
        this.studentNumber = studentNumber;
        this.studentsInEntrance = new StudentContainer();
    }

    public void addStudent(Student myStudent){
        if (studentsInEntrance.size()<studentNumber)
            studentsInEntrance.addStudent(myStudent);
    }

    public boolean isEntranceFull(){
        int sum = 0;
        for(PawnDiscColor c : PawnDiscColor.values()){
            sum += studentsInEntrance.getInfluence(c);
        }
        if(sum >= studentNumber)
            return true;
        return false;
    }

    public Student removeStudent(PawnDiscColor color){
        return studentsInEntrance.retrieveStudent(color);
    }

    public boolean checkIfEntranceIsEmpty(){
        if(studentsInEntrance.size() == 0) return true;
        else return false;
    }

    public boolean isColorAvailable(PawnDiscColor c){
        if(studentsInEntrance.getInfluence(c) > 0) return true;
        else return false;
    }

    public int getColorAvailability(PawnDiscColor color){return studentsInEntrance.getInfluence(color);}

    @Override
    public String toString(){
        return "In entrance" +
                "\nYELLOW " + studentsInEntrance.getInfluence(PawnDiscColor.YELLOW) +
                "\nPINK " + studentsInEntrance.getInfluence(PawnDiscColor.PINK) +
                "\nGREEN " + studentsInEntrance.getInfluence(PawnDiscColor.GREEN) +
                "\nBLUE " + studentsInEntrance.getInfluence(PawnDiscColor.BLUE) +
                "\nRED " + studentsInEntrance.getInfluence(PawnDiscColor.RED);
    }
}
