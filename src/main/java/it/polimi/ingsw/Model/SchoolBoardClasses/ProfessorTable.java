package it.polimi.ingsw.Model.SchoolBoardClasses;

import it.polimi.ingsw.Utils.Enums.PawnDiscColor;
import it.polimi.ingsw.Model.BasicElements.Professor;

public class ProfessorTable {

    private Professor[] professors;
    private Professor[] previousState;

    public ProfessorTable() {

        this.professors = new Professor[5];
        initializeProfessors();

    }

    public void addProfessor(PawnDiscColor color) {
        for (Professor p : professors) {
            if (p.getColor().equals(color)) {
                p.setOwnedToTrue();
            }
        }
    }

    public void removeProfessor(PawnDiscColor color) {
        for (Professor p : professors) {
            if (p.getColor().equals(color)) {
                p.setOwnedToFalse();
            }
        }
    }

    public boolean hasProfessor(PawnDiscColor color) {
        for (Professor p : professors) {
            if (p.getColor().equals(color)) {
                return p.isOwnedByPlayer();
            }
        }
        return false;
    }

    private void initializeProfessors() {
        int i = 0;
        for (PawnDiscColor c : PawnDiscColor.values()) {
            professors[i] = new Professor(c);
            professors[i].setOwnedToFalse();
            i++;
        }
    }

    public int getNumberOfProfessors() {
        int count = 0;
        for (Professor p : professors) {
            if (p.isOwnedByPlayer())
                count++;
        }
        return count;
    }

    @Override
    /* FORMAT only PawnDiscColor of owned professors */
    public String toString() {
        StringBuilder toRet = new StringBuilder();

        for(Professor professor : professors){
            if(professor.isOwnedByPlayer()){
                toRet.append(professor.getColor()).append(" ");
            }
        }
        return toRet.toString();
    }

    /* this method keeps track of the professors the player had before playing card 2 */
    public void saveProfessors() {
        previousState = new Professor[professors.length];
        int i = 0;

        for (Professor p : professors) {
            previousState[i] = new Professor(p.getColor());
            if (p.isOwnedByPlayer()) previousState[i].setOwnedToTrue();
            i++;
        }
    }

    /* this method resets the player's professor table to the state it was before playing card 2*/
    public void resetPreviousProfessorTable(){
        professors = previousState;
    }
}

