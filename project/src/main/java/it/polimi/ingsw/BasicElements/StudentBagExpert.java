package it.polimi.ingsw.BasicElements;

public class StudentBagExpert extends StudentBag{

    public StudentBagExpert(int numStudents){
        super(numStudents);
    }

    public void addStudentBack(Student student){
        studentList.add(student);
    }
}
