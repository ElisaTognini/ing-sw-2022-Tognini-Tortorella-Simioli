package it.polimi.ingsw.BasicElements;

import it.polimi.ingsw.BasicElements.Student;
import it.polimi.ingsw.Enums.PawnDiscColor;
import it.polimi.ingsw.TailoredExceptions.EmptyException;

import java.util.*;


public class StudentBag {
    protected List<Student> studentList;
    private int numStudents;
    /*generates a list through adding same size arrays for each color*/
    public StudentBag(int numStudents){
        this.numStudents = numStudents;
        studentList = new ArrayList<>();
        for(int i=0; i<numStudents/5; i++){
             studentList.add(new Student(PawnDiscColor.PINK));
             studentList.add(new Student(PawnDiscColor.RED));
             studentList.add(new Student(PawnDiscColor.GREEN));
             studentList.add(new Student(PawnDiscColor.YELLOW));
             studentList.add(new Student(PawnDiscColor.BLUE));
        }

    }

    /*randomly draws a student*/
    public Student drawStudent() throws EmptyException {

        if(studentList.isEmpty()){
            throw new EmptyException();
        }

        Random rand = new Random();
        int randomIndex = rand.nextInt(studentList.size());
        Student extracted = studentList.get(randomIndex);
        studentList.remove(extracted);

        return extracted;
    }

    public void addStudentBack(Student student){
        studentList.add(student);
    }
    public int availableStudents(){
        return studentList.size();
    }
}
