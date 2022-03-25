package it.polimi.ingsw;
import it.polimi.ingsw.BasicElements.*;
import it.polimi.ingsw.Enums.*;
import java.util.*;

/*utility data structure which is a hashmap that has
* - the color enum values as key
* - the arraylists of students as values*/

public class StudentContainer {

    //value hash sets
    private ArrayList<Student> pinkStudentsInDiningRoom;
    private ArrayList<Student> yellowStudentsInDiningRoom;
    private ArrayList<Student> greenStudentsInDiningRoom;
    private ArrayList<Student> blueStudentsInDiningRoom;
    private ArrayList<Student> redStudentsInDiningRoom;

    private Map<PawnDiscColor, ArrayList<Student>> container;

    public StudentContainer() {
        pinkStudentsInDiningRoom = new ArrayList<Student>();
        yellowStudentsInDiningRoom = new ArrayList<Student>();
        blueStudentsInDiningRoom = new ArrayList<Student>();
        greenStudentsInDiningRoom = new ArrayList<Student>();
        redStudentsInDiningRoom = new ArrayList<Student>();

        container = new HashMap<PawnDiscColor, ArrayList<Student>>();

        container.put(PawnDiscColor.PINK, pinkStudentsInDiningRoom);
        container.put(PawnDiscColor.RED, redStudentsInDiningRoom);
        container.put(PawnDiscColor.BLUE, blueStudentsInDiningRoom);
        container.put(PawnDiscColor.YELLOW, yellowStudentsInDiningRoom);
        container.put(PawnDiscColor.GREEN, greenStudentsInDiningRoom);
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
}
