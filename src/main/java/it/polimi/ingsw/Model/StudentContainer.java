package it.polimi.ingsw.Model;
import it.polimi.ingsw.Model.BasicElements.Student;
import it.polimi.ingsw.Utils.Enums.PawnDiscColor;

import java.util.*;

/** Class StudentContainer is a utility data structure, more precisely a hash map that has the color enum values as
 * key and five array lists of students as values*/

public class StudentContainer {

    private ArrayList<Student> pinkStudents;
    private ArrayList<Student> yellowStudents;
    private ArrayList<Student> greenStudents;
    private ArrayList<Student> blueStudents;
    private ArrayList<Student> redStudents;

    private Map<PawnDiscColor, ArrayList<Student>> container;

    /** Constructor StudentContainer creates a new instance of student container, where it instantiates the map and the
     * five arrays in the map and then associates the specified values with the specified keys in the map. */
    public StudentContainer() {
        pinkStudents = new ArrayList<>();
        yellowStudents = new ArrayList<>();
        blueStudents = new ArrayList<>();
        greenStudents = new ArrayList<>();
        redStudents = new ArrayList<>();

        container = new HashMap<>();

        container.put(PawnDiscColor.PINK, pinkStudents);
        container.put(PawnDiscColor.RED, redStudents);
        container.put(PawnDiscColor.BLUE, blueStudents);
        container.put(PawnDiscColor.YELLOW, yellowStudents);
        container.put(PawnDiscColor.GREEN, greenStudents);
    }

    /** Method getInfluece returns the size of the array list associated to the specified color
     *
     * @param color of type PawnDiscColor - color
     *
     * @return int - the number of student of said color inside the array list */
    public int getInfluence(PawnDiscColor color){
        return container.get(color).size();
    }


    /** Method addStudent adds a student to the container and puts it the array list associated to the color
     * of said student.
     *
     * @param student of type Student - the student to be added to the container
     **/
    public void addStudent(Student student){
        container.get(student.getColor()).add(student);
    }


    /** Method retrieveStudent retrieves a student of the specified color from the corresponding array list inside
     * the hash map.
     *
     * @param color of type PawnDiscColor - the color of the student
     *
     * @return Student - the student associated to that color */
    public Student retrieveStudent(PawnDiscColor color){
        Student toReturn;

        toReturn = container.get(color).get(container.get(color).size()-1);
        container.get(color).remove(container.get(color).size()-1);

        return toReturn;
    }


    /** Method size returns the number of student inside the entire student container, result of the sum of
     * the size of each color array list.
     *
     * @return int - the size of the student container */
    public int size(){
        return pinkStudents.size() + yellowStudents.size() + redStudents.size()
                + greenStudents.size() + blueStudents.size();
    }
}
