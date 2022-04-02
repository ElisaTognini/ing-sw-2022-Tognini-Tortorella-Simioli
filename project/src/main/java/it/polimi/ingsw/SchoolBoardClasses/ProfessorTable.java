package it.polimi.ingsw.SchoolBoardClasses;

import it.polimi.ingsw.Enums.PawnDiscColor;
import it.polimi.ingsw.TailoredExceptions.EmptyException;
import it.polimi.ingsw.BasicElements.Professor;

import java.util.ArrayList;

public class ProfessorTable {

    private Professor[] professors;

    public ProfessorTable() {

        this.professors = new Professor[5];
        initializeProfessors();

    }

    public void addProfessor(PawnDiscColor color) {
        for(Professor p : professors){
            if(p.getColor().equals(color)){
                p.setOwnedToTrue();
            }
        }
    }

    public void removeProfessor(PawnDiscColor color){
        for(Professor p : professors){
            if(p.getColor().equals(color)){
                p.setOwnedToFalse();
            }
        }
    }

    public boolean hasProfessor(PawnDiscColor color){
        for(Professor p : professors){
            if(p.getColor().equals(color)){
                return p.isOwnedByPlayer();
            }
        }
        return false;
    }

    private void initializeProfessors(){
        int i = 0;
        for(PawnDiscColor c : PawnDiscColor.values()){
            professors[i] = new Professor(c);
            professors[i].setOwnedToFalse();
            i++;
        }
    }


}
