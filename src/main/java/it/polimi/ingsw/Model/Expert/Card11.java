package it.polimi.ingsw.Model.Expert;

import it.polimi.ingsw.Model.BoardClasses.BoardExpert;
import it.polimi.ingsw.Model.SchoolBoardClasses.SchoolBoard;
import it.polimi.ingsw.Model.StudentContainer;
import it.polimi.ingsw.Utils.Enums.PawnDiscColor;

/* in setup, draw 4 students and place them on this card
*  take 1 student from this card and place it in your dining room.
*  then, take a new student from the bag and place it on this card */

public class Card11 extends CharacterCardTemplate{

    private StudentContainer students;

    public Card11(BoardExpert board){
        super(board);
        cardID = 11;
        cost = 2;
        students = new StudentContainer();
        setupCard();
    }

    public void useCard(Object o, String nickname){
        Parameter parameters;
        SchoolBoard sb = board.getPlayerSchoolBoard(nickname);


        if(o instanceof Parameter){
            parameters = (Parameter)o;
        }
        else throw new IllegalArgumentException();

        sb.getDiningRoom().addStudent(students.retrieveStudent(parameters.getColor()));
        if(board.getStudentBag().checkIfStudentBagEmpty()){
            board.setLastRound();
        }
        else
            students.addStudent(board.getStudentBag().drawStudent());
    }

    private void setupCard() {
        for(int i=0; i<4; i++){
            students.addStudent(board.getStudentBag().drawStudent());
        }
    }

    @Override
    public boolean checkIfActionIsForbidden(Object o, String nickname) throws IllegalArgumentException {

        Parameter parameters;

        if(o instanceof Parameter){
            parameters = (Parameter)o;
        }
        else throw new IllegalArgumentException();

        if(students.getInfluence(parameters.getColor()) > 0){
            if(students.size() > 0){
                if(!board.getPlayerSchoolBoard(nickname).getDiningRoom().checkIfDiningRoomIsFull(parameters.getColor())) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public String toStringCard(){
        StringBuilder toRet = new StringBuilder(cardID + " " + cost + " ");

        for(PawnDiscColor c : PawnDiscColor.values()){
            toRet.append(c).append(" ").append(students.getInfluence(c)).append(" ");
        }
        return toRet.toString();
    }


}
