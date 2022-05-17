package it.polimi.ingsw.Model;
import it.polimi.ingsw.Model.BasicElements.Student;
import it.polimi.ingsw.Utils.Enums.PawnDiscColor;

import java.util.*;

/*utility data structure which is a hashmap that has
* - the color enum values as key
* - the arraylists of students as values*/

public class StudentContainer {

    //value hash sets
    private ArrayList<Student> pinkStudents;
    private ArrayList<Student> yellowStudents;
    private ArrayList<Student> greenStudents;
    private ArrayList<Student> blueStudents;
    private ArrayList<Student> redStudents;

    private Map<PawnDiscColor, ArrayList<Student>> container;

    public StudentContainer() {
        pinkStudents = new ArrayList<Student>();
        yellowStudents = new ArrayList<Student>();
        blueStudents = new ArrayList<Student>();
        greenStudents = new ArrayList<Student>();
        redStudents = new ArrayList<Student>();

        container = new HashMap<PawnDiscColor, ArrayList<Student>>();

        container.put(PawnDiscColor.PINK, pinkStudents);
        container.put(PawnDiscColor.RED, redStudents);
        container.put(PawnDiscColor.BLUE, blueStudents);
        container.put(PawnDiscColor.YELLOW, yellowStudents);
        container.put(PawnDiscColor.GREEN, greenStudents);
    }

    public int getInfluence(PawnDiscColor color){
        return container.get(color).size();
    }

    public void addStudent(Student student){
        container.get(student.getColor()).add(student);
    }

    public Student retrieveStudent(PawnDiscColor color){
        Student toReturn;

        toReturn = container.get(color).get(container.get(color).size()-1);
        container.get(color).remove(container.get(color).size()-1);

        return toReturn;
    }

    public int size(){
        return pinkStudents.size() + yellowStudents.size() + redStudents.size()
                + greenStudents.size() + blueStudents.size();
    }

    public boolean checkIfContainerIsEmpty(PawnDiscColor color){
        if(container.get(color).size() == 0) return true;
        else return false;
    }
}
