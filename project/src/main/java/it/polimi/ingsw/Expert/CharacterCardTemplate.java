package it.polimi.ingsw.Expert;
import it.polimi.ingsw.BoardClasses.BoardExpert;
import it.polimi.ingsw.Enums.PawnDiscColor;

import java.util.ArrayList;

public abstract class CharacterCardTemplate {
    protected int cost;
    protected int cardID;
    protected BoardExpert board;

    public CharacterCardTemplate(BoardExpert board){
        this.board = board;
    }

    protected void increaseCost(){
        cost++;
    }

    public int getCost(){
        return cost;
    }

    public int getCardID(){
        return cardID;
    }

    public boolean checkIfActionIsForbidden(ArrayList<PawnDiscColor> studentsInEntrance, ArrayList<PawnDiscColor> studentsInDiningRoom,
                                            String nickname){

        if(studentsInEntrance.size()!=studentsInDiningRoom.size() ||
                studentsInDiningRoom.size() > 2 || studentsInEntrance.size() > 2) return true;
        else return false;

    }
}
