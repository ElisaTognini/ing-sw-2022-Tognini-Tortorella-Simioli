package it.polimi.ingsw.SchoolBoardClasses;

import it.polimi.ingsw.TailoredExceptions.EmptyException;
import it.polimi.ingsw.BasicElements.Professor;

import java.util.ArrayList;

public class ProfessorTable {

    private ArrayList<Professor> professors;

    public ProfessorTable() {
        this.professors = new ArrayList<Professor>();
    }

    public void addProfessor(Professor myProfessor) throws ArrayIndexOutOfBoundsException{
        if(professors.size()>=5) throw new ArrayIndexOutOfBoundsException();
        else {
            professors.add(myProfessor);
        }
    }

    public void removeProfessor(Professor myProfessor) throws EmptyException {
        if (professors.isEmpty()) throw new EmptyException();
        else  {
            professors.remove(myProfessor);
        }
    }


}
