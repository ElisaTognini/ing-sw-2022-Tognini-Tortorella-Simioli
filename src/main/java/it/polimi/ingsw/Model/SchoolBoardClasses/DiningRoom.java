package it.polimi.ingsw.Model.SchoolBoardClasses;

import it.polimi.ingsw.Model.BasicElements.Student;
import it.polimi.ingsw.Utils.Enums.PawnDiscColor;
import it.polimi.ingsw.Model.StudentContainer;

/** Class DiningRoom is one of the classes which compose the player's school board: it contains a reference to a student
 * container which stores the students the player puts in the dining room. */

public class DiningRoom {

    private StudentContainer container;

    /** Constructor DiningRoom creates a new instance of DiningRoom: it creates a new container and assigns it to
     * the attribute container of the new dining room. */
    public DiningRoom() {
        container = new StudentContainer();
    }


    /** Method addStudent calls the addStudent method in container to add a new student into the dining room
     *
     * @param myStudent of type Student - the student to add to the dining room*/
    public void addStudent(Student myStudent){
        container.addStudent(myStudent);

    }


    /** Method checkIfMod3 checks if there are three(or multiple) students of the specified color in the dining room
     *
     * @param color of type PawnDiscColor - the color of the students
     *
     * @return boolean - true if the size of the array list of the corresponding color is three or multiple, false
     * otherwise */
    public boolean checkIfMod3(PawnDiscColor color){
        if(container.getInfluence(color) % 3 == 0){
            return true;
        }
        return false;
    }


    /** Method influenceForProf returns the number of student of the selected color inside the dining room
     *
     * @param color of type PawnDiscColor - the color of the students
     *
     * @return int - the number of students of said color inside the specific array list in the container */
    public int influenceForProf(PawnDiscColor color){
        return container.getInfluence(color);
    }


    /** getter method - Method getContainer returns the container in this class
     *
     * @return StudentContainer - student container of the dining room */
    public StudentContainer getContainer(){
        return container;
    }


    /** Method checkIfDiningRoomIsFull checks if the size of the array list of the selected color is bigger than 10,
     * which is the maximum number of spots available in the dining room for the students of one color.
     *
     * @param color of type PawnDiscColor - color of the students
     *
     * @return boolean - true if the dining room is full, false otherwise */
    public boolean checkIfDiningRoomIsFull(PawnDiscColor color){
        if(container.getInfluence(color) >= 10) return true;
        else return false;
    }


    /** Method toString builds a String containing all the info stored in this class
     *
     *  @return String - FORMAT: the number of pink, yellow, blue, green and red students inside the container,
     *  each separated by a blank space.
     *  */
    @Override
    public String toString(){
        return container.getInfluence(PawnDiscColor.PINK) + " " +
               container.getInfluence(PawnDiscColor.YELLOW) + " " +
               container.getInfluence(PawnDiscColor.BLUE) + " " +
               container.getInfluence(PawnDiscColor.GREEN) + " " +
               container.getInfluence(PawnDiscColor.RED);
    }
}
