package it.polimi.ingsw;

import java.util.ArrayList;

public class Entrance {

    private ArrayList<Student> studentsInEntrance;

    public Entrance() {
        this.studentsInEntrance = new ArrayList<Student>();
    }

    public void addStudent(Student myStudent) throws ArrayIndexOutOfBoundsException{
        if (studentsInEntrance.size()>=7) throw new ArrayIndexOutOfBoundsException();
        else {
            studentsInEntrance.add(myStudent);
        }
    }

    public void removeStudent(int index) throws EmptyException{
        if(studentsInEntrance.isEmpty()) throw new EmptyException();
        else studentsInEntrance.remove(index);
    }

}
