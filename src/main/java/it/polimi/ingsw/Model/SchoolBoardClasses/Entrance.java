package it.polimi.ingsw.Model.SchoolBoardClasses;
import it.polimi.ingsw.Utils.Enums.PawnDiscColor;
import it.polimi.ingsw.Model.StudentContainer;
import it.polimi.ingsw.Model.BasicElements.Student;


/** Class Entrance is one of the classes which compose the player's school board: it contains a reference to a student
 * container which contains the students to be either moved to the islands or to the dining room throughout the game and
 * it has an attribute named studentNumber which indicates the number of students that must be added to the entrance at
 * the beginning of each game. */
public class Entrance {

    private StudentContainer studentsInEntrance;
    private int studentNumber;

    /** Constructor Entrance creates a new instance of Entrance: it creates a new student container and assigns it to
     * the entrance and assigns parameter studentNumber to the one inside this class.
     *
     * @param studentNumber of type int - the number of student in the entrance*/
    public Entrance(int studentNumber) {
        this.studentNumber = studentNumber;
        this.studentsInEntrance = new StudentContainer();
    }


    /** Method addStudent adds a student to the entrance if the number of students currently inside the entrance are
     * less than the value of attribute studentNumber
     *
     * @param myStudent of type Student - the student to add to the entrance */
    public void addStudent(Student myStudent){
        if (studentsInEntrance.size()<studentNumber)
            studentsInEntrance.addStudent(myStudent);
    }


    /** Method isEntranceFull checks if the students currently inside the entrance are equal or more than the value
     * of the attribute studentNumber.
     *
     * @return boolean - true if the entrance is full, false otherwise */
    public boolean isEntranceFull(){
        int sum = 0;
        for(PawnDiscColor c : PawnDiscColor.values()){
            sum += studentsInEntrance.getInfluence(c);
        }
        if(sum >= studentNumber)
            return true;
        return false;
    }


    /** Method removeStudent calls for method retrieveStudent from the cointainer studentsInEntrance in order to
     * take away a student of a selected color from the entrance.
     *
     * @param color of type PawnDiscColor - the color of the student to remove
     *
     * @return Student - the student removed from the entrance */
    public Student removeStudent(PawnDiscColor color){
        return studentsInEntrance.retrieveStudent(color);
    }


    /** Method isColorAvailable checks if there is at least a student of a selected color in the entrance
     *
     * @param c of type PawnDiscColor - selected color
     *
     * @return boolean - true if there is at least one student of said color, false otherwise */
    public boolean isColorAvailable(PawnDiscColor c){
        if(studentsInEntrance.getInfluence(c) > 0) return true;
        else return false;
    }

    /** Method getColorAvailability returns the number of students of a selected color which are currently
     * in the entrance.
     *
     * @param color of type PawnDiscColor - the color requested
     *
     * @return int - the number of students of that color */
    public int getColorAvailability(PawnDiscColor color){return studentsInEntrance.getInfluence(color);}


    /** Method toString builds a String containing all the info stored in this class
     *
     *  @return String - FORMAT: the number of pink, yellow, blue, green and red students inside the container,
     *  each separated by a blank space.
     *  */
    @Override
    public String toString(){
        return studentsInEntrance.getInfluence(PawnDiscColor.PINK) + " " +
                studentsInEntrance.getInfluence(PawnDiscColor.YELLOW) + " " +
                studentsInEntrance.getInfluence(PawnDiscColor.BLUE) + " " +
                studentsInEntrance.getInfluence(PawnDiscColor.GREEN) + " " +
                studentsInEntrance.getInfluence(PawnDiscColor.RED);
    }
}
