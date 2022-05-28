package it.polimi.ingsw.Model.Expert;

import it.polimi.ingsw.Model.BoardClasses.BoardExpert;
import it.polimi.ingsw.Utils.Enums.PawnDiscColor;
import it.polimi.ingsw.Model.SchoolBoardClasses.SchoolBoard;

/* take control of any number of professors for the turn */

public class Card2 extends CharacterCardTemplate{

    private final String description = "take control of any number of professors for the turn";

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

    @Override
    public String toStringCard(){
        return String.valueOf(cardID) + "-" + String.valueOf(cost) + "-" + this.getDescription() ;
    }

    @Override
    public String getDescription(){
        return description;
    }

}
