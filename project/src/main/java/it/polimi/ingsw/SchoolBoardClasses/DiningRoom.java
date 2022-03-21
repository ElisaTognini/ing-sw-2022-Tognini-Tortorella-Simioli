package it.polimi.ingsw.SchoolBoardClasses;

import it.polimi.ingsw.BasicElements.Student;

import java.util.HashSet;

public class DiningRoom {

    protected HashSet<Student> pinkStudentsInDiningRoom;
    protected HashSet<Student> yellowStudentsInDiningRoom;
    protected HashSet<Student> greenStudentsInDiningRoom;
    protected HashSet<Student> blueStudentsInDiningRoom;
    protected HashSet<Student> redStudentsInDiningRoom;

    public DiningRoom() {
        pinkStudentsInDiningRoom = new HashSet<Student>();
        yellowStudentsInDiningRoom = new HashSet<Student>();
        blueStudentsInDiningRoom = new HashSet<Student>();
        greenStudentsInDiningRoom = new HashSet<Student>();
        redStudentsInDiningRoom = new HashSet<Student>();
    }

    /*needs a smoother version of the conditional code - very repetitive
    * based on color adds student to different sets to count the correct set*/
    public void addStudent(Student myStudent) throws IndexOutOfBoundsException{

        switch (myStudent.getColor()){

            case RED:
                if (redStudentsInDiningRoom.size()>=10) throw new IndexOutOfBoundsException();
                else redStudentsInDiningRoom.add(myStudent);
                break;
            case YELLOW:
                if (yellowStudentsInDiningRoom.size()>=10) throw new IndexOutOfBoundsException();
                else yellowStudentsInDiningRoom.add(myStudent);
                break;
            case PINK:
                if (pinkStudentsInDiningRoom.size()>=10) throw new IndexOutOfBoundsException();
                else pinkStudentsInDiningRoom.add(myStudent);
                break;
            case BLUE:
                if (blueStudentsInDiningRoom.size()>=10) throw new IndexOutOfBoundsException();
                else blueStudentsInDiningRoom.add(myStudent);
                break;
            case GREEN:
                if (greenStudentsInDiningRoom.size()>=10) throw new IndexOutOfBoundsException();
                else greenStudentsInDiningRoom.add(myStudent);
                break;
        }
    }
}
