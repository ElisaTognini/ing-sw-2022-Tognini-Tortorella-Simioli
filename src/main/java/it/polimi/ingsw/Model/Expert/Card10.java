package it.polimi.ingsw.Model.Expert;

import it.polimi.ingsw.Model.BasicElements.Student;
import it.polimi.ingsw.Model.BoardClasses.BoardExpert;
import it.polimi.ingsw.Utils.Enums.PawnDiscColor;
import it.polimi.ingsw.Model.SchoolBoardClasses.SchoolBoard;

import java.util.ArrayList;

/** Class Card10 represents one of the twelve character cards: it contains a brief description of the effect of the card.
 * */

public class Card10 extends CharacterCardTemplate{

    private final String description = "switch up to 2 students between entrance and dining room";


    /** Constructor Card10 creates a new instance of this character card, assigning its ID and cost.
     *
     * @param board of type BoardExpert - board */
    public Card10(BoardExpert board){
        super(board);
        cardID = 10;
        cost = 1;
    }


    /** Method useCard takes the parameter passed by the player inside the parameter o, one array list for the color of the
     * students the player wants to move from the entrance to the dining room and one for those they want to move from the
     * dining room to the entrance.
     * This method removes one of the selected students from the entrance and stores it in a local variable while adding
     * to the dining one of the students from the other batch of selected students. Finally, this method adds the student
     * stored in the local variable inside the dining room.
     * This process is repeated for each color stored in the first array list.
     *
     *
     * @param o of type Object - the parameters passed by the player
     * @param nickname of type String - the nickname of the player.
     * */
    public void useCard(Object o, String nickname){
        Parameter parameters;
        SchoolBoard sb = board.getPlayerSchoolBoard(nickname);
        Student student1;

        if(o instanceof Parameter){
            parameters = (Parameter)o;
        }
        else throw new IllegalArgumentException();

        ArrayList<PawnDiscColor> studentsInEntrance = parameters.getColorArrayList();
        ArrayList<PawnDiscColor> studentsInDiningRoom = parameters.getColorArrayList2();

        for(int i = 0; i < studentsInEntrance.size(); i++){
            student1 = sb.getEntrance().removeStudent(studentsInEntrance.get(i));
            sb.getEntrance().addStudent(sb.getDiningRoom().getContainer().retrieveStudent(studentsInDiningRoom.get(i)));
            sb.getDiningRoom().getContainer().addStudent(student1);
        }

    }


    /** Method checkIfActionIsForbidden checks that the player has got at least two students in entrance of the
     * requested colors
     *
     * @param o of type Object - the parameters passed by the player
     * @param nickname of type String - the player's nickname.
     *
     * @return boolean - true if action is forbidden, false otherwise
     */
    @Override
    public boolean checkIfActionIsForbidden(Object o, String nickname){

        Parameter parameters;
        int sum1 = 0;
        int sum2 = 0;

        if(o instanceof Parameter){
            parameters = (Parameter)o;
        }
        else throw new IllegalArgumentException();

        ArrayList<PawnDiscColor> studentsInEntrance = parameters.getColorArrayList();
        ArrayList<PawnDiscColor> studentsInDiningRoom = parameters.getColorArrayList2();

        if(studentsInEntrance.size()!=studentsInDiningRoom.size() ||
                studentsInDiningRoom.size() > 2 || studentsInEntrance.size() > 2)
            return true;

        for(PawnDiscColor color : PawnDiscColor.values()){
            sum1 = 0;
            sum2 = 0;
            for(PawnDiscColor c : studentsInEntrance){
                if(c.equals(color))
                    sum1++;
            }

            for(PawnDiscColor c : studentsInDiningRoom){
                if(c.equals(color))
                    sum2++;
            }

            if(board.getPlayerSchoolBoard(nickname).getEntrance().getColorAvailability(color) < sum1)
                return true;

            if(board.getPlayerSchoolBoard(nickname).getDiningRoom().influenceForProf(color) < sum2)
                return true;
        }

        return false;

    }


    /** Method toStringCard builds a String containing all the info stored in this class
     *
     *  @return String - FORMAT: cardID, cost and description of the effect of the card, each separated by a "-" .
     *  */
    @Override
    public String toStringCard(){
        return cardID + "-" + cost + "-" + this.getDescription();
    }


    /**getter method - Method getDescription returns the description of the effect of the card
     *
     * @return String - the description of the character card */
    @Override
    public String getDescription(){
        return description;
    }
}
