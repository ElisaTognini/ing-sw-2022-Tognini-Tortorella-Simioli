package it.polimi.ingsw.Model.SchoolBoardClasses;

import it.polimi.ingsw.Utils.Enums.PawnDiscColor;
import it.polimi.ingsw.Model.BasicElements.Professor;

/** Class ProfessorTable is one of the classes which compose the player's school board: it contains two arrays, one
  * which stores the professors when and as the player get control of them and the other one is used in expert mode
  * when character card 2 is played to store the state of the professor table before the player decides which
  * professor to take control of.*/
public class ProfessorTable {

    private Professor[] professors;
    private Professor[] previousState;

    /** Constructor ProfessorTable creates a new instance of professor table and initializes the professors */
    public ProfessorTable() {
        this.professors = new Professor[5];
        initializeProfessors();

    }


    /** Method addProfessor adds the professor of the selected color to the professor table and calls method of
     * professor to signal that this professor is owned by a player.
     *
     * @param color of type PawnDiscColor - color of the professor
     * */
    public void addProfessor(PawnDiscColor color) {
        for (Professor p : professors) {
            if (p.getColor().equals(color)) {
                p.setOwnedToTrue();
            }
        }
    }


    /** Method removeProfessor removes the professor of the selected color from the professor table and calls method
     * of professor to signal that this professor is owned by a player.
     *
     * @param color of type PawnDiscColor - color of the professor
     * */
    public void removeProfessor(PawnDiscColor color) {
        for (Professor p : professors) {
            if (p.getColor().equals(color)) {
                p.setOwnedToFalse();
            }
        }
    }

    /** Method hasProfessor checks if the professor of selected color is owned by a player
     *
     * @param color of type PawnDiscColor - color of the professor
     * */
    public boolean hasProfessor(PawnDiscColor color) {
        for (Professor p : professors) {
            if (p.getColor().equals(color)) {
                return p.isOwnedByPlayer();
            }
        }
        return false;
    }

    /** Private method initializeProfessors creates a new Professor for each value of the PawnDiscColor enum
     * and sets value of attribute ownedByPlayer to false by default. */
    private void initializeProfessors() {
        int i = 0;
        for (PawnDiscColor c : PawnDiscColor.values()) {
            professors[i] = new Professor(c);
            professors[i].setOwnedToFalse();
            i++;
        }
    }

    /** Method getNumberOfProfessors returns the number of professors owned by a player
     *
     * @return int - number of professors owned */
    public int getNumberOfProfessors() {
        int count = 0;
        for (Professor p : professors) {
            if (p.isOwnedByPlayer())
                count++;
        }
        return count;
    }

    /** Method toString builds a String containing all the info stored in this class
     *
     *  @return String - FORMAT: the professors owned by the player, each separated by a blank space.
     *  */
    public String toString() {
        StringBuilder toRet = new StringBuilder();

        for(Professor professor : professors){
            if(professor.isOwnedByPlayer()){
                toRet.append(professor.getColor()).append(" ");
            }
        }
        return toRet.toString();
    }

    /** Method saveProfessor keeps track of the professors the player had before playing character card 2
     * and saves it in array previousState */
    public void saveProfessors() {
        previousState = new Professor[professors.length];
        int i = 0;

        for (Professor p : professors) {
            previousState[i] = new Professor(p.getColor());
            if (p.isOwnedByPlayer()) previousState[i].setOwnedToTrue();
            i++;
        }
    }

    /** Method resetPreviousProfessorTable resets the player's professor table to the state it was before playing
     * character card 2*/
    public void resetPreviousProfessorTable(){
        professors = previousState;
    }
}

