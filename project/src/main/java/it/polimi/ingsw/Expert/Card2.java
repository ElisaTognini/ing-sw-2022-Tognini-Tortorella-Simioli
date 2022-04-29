package it.polimi.ingsw.Expert;

import it.polimi.ingsw.BasicElements.Professor;
import it.polimi.ingsw.BoardClasses.Board;
import it.polimi.ingsw.BoardClasses.BoardExpert;
import it.polimi.ingsw.Enums.PawnDiscColor;
import it.polimi.ingsw.SchoolBoardClasses.SchoolBoard;

import java.util.ArrayList;

/* take control of any number of professors for the turn */

public class Card2 extends CharacterCardTemplate{

    public Card2(BoardExpert board){
        super(board);
        cardID = 2;
        cost = 2;
    }
    public void useCard(Object o, String nickname){
        Parameter parameters;
        SchoolBoard sb;

        if(o instanceof Parameter){
            parameters = (Parameter)o;
        }
        else throw new IllegalArgumentException();

        sb = board.getPlayerSchoolBoard(nickname);
        sb.getProfessorTable().saveProfessors();

        for(PawnDiscColor c: parameters.getColorArrayList()){
                sb.getProfessorTable().addProfessor(c);
        }
        sb.setModifiedTable();
    }

    @Override
    public boolean checkIfActionIsForbidden(Object o, String nickname) throws IllegalArgumentException {
        return false;
    }

}
