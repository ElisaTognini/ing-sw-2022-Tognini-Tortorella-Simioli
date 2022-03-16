package it.polimi.ingsw;

import java.util.*;


public class StudentBag {
    private List<Student> studentList;
    private Student tempArrayPink[] = new Student[24];
    private Student tempArrayRed[] = new Student[24];
    private Student tempArrayGreen[] = new Student[24];
    private Student tempArrayBlue[] = new Student[24];
    private Student tempArrayYellow[] = new Student[24];

    public StudentBag(){
        for(int i=0; i<24; i++){
             tempArrayPink[i] = new Student(PawnDiscColor.PINK);
             tempArrayRed[i] = new Student(PawnDiscColor.RED);
             tempArrayGreen[i] = new Student(PawnDiscColor.GREEN);
             tempArrayYellow[i] = new Student(PawnDiscColor.YELLOW);
             tempArrayBlue[i] = new Student(PawnDiscColor.BLUE);
        }

        studentList = new ArrayList<Student>();
        Collections.addAll(studentList, tempArrayBlue);
        Collections.addAll(studentList, tempArrayPink);
        Collections.addAll(studentList, tempArrayRed);
        Collections.addAll(studentList, tempArrayGreen);
        Collections.addAll(studentList, tempArrayYellow);

    }

    public Student drawStudent(){

        Random rand = new Random();
        int randomIndex = rand.nextInt(studentList.size());
        Student extracted = studentList.get(randomIndex);
        studentList.remove(extracted);

        return extracted;
    }


    public int availableStudents(){
        return studentList.size();
    }
}
