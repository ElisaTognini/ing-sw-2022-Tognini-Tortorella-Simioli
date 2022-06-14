package it.polimi.ingsw.Model.BasicElements;

import it.polimi.ingsw.Utils.Enums.PawnDiscColor;

import java.util.*;

/** Class StudentBag contains a list of all the students which can be used to play the game */

public class StudentBag {

    protected List<Student> studentList;
    private int numStudents;

    /** Constructor StudentBag generates a list through adding same size arrays for each color
     *
     * @param numStudents - of type int - number of students */
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


    /** checkIfStudentBagIsEmpty checks to see if there are any students left in the bag
     *
     * @return boolean - true if size is 0, false otherwise */
    public boolean checkIfStudentBagEmpty(){ return studentList.isEmpty(); }


    /** drawStudent randomly draws a student from the student bag using class Random.
     *
     * @return Student - student extracted
     * */
    public Student drawStudent(){
        int bound = studentList.size();
        if(bound == 1){
            Student s = studentList.get(0);
            studentList.remove(s);
            return s;
        }
        Random rand = new Random();
        int randomIndex = rand.nextInt(bound);
        Student extracted = studentList.get(randomIndex);
        studentList.remove(extracted);

        return extracted;
    }

    /** addStudentBack, only called in expert mode, puts a Student back into the student bag.
     *
     * @param student - of type Student - the student to add back
     * */
    public void addStudentBack(Student student){
        studentList.add(student);
    }


    /** availableStudents returns the number of students inside the bag.
     *
     * @return Integer - number of students inside the bag*/
    public int availableStudents(){
        return studentList.size();
    }
}
