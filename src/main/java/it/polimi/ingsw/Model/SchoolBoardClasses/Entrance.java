package it.polimi.ingsw.Model.SchoolBoardClasses;

import it.polimi.ingsw.Utils.Enums.PawnDiscColor;
import it.polimi.ingsw.Model.StudentContainer;
import it.polimi.ingsw.Model.BasicElements.Student;

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
    /* FORMAT pink yellow blue green red */
    public String toString(){
        return studentsInEntrance.getInfluence(PawnDiscColor.PINK) + " " +
                studentsInEntrance.getInfluence(PawnDiscColor.YELLOW) + " " +
                studentsInEntrance.getInfluence(PawnDiscColor.BLUE) + " " +
                studentsInEntrance.getInfluence(PawnDiscColor.GREEN) + " " +
                studentsInEntrance.getInfluence(PawnDiscColor.RED);
    }
}
