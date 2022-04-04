package it.polimi.ingsw.Expert;

/*  */

import it.polimi.ingsw.BoardClasses.Board;
import it.polimi.ingsw.Enums.PawnDiscColor;
import it.polimi.ingsw.SchoolBoardClasses.SchoolBoard;
import it.polimi.ingsw.TailoredExceptions.EmptyException;

/* you may switch up to 2 students between your entrance and your dining room */

public class Card10 extends CharacterCardTemplate{

    public Card10(Board board){
        super(board);
        cardID = 10;
        cost = 1;
    }

    /* insert null for students you don't want to switch
    *  or use 2 different methods: useCard1 and useCard2 for one or two switches */

    public void useCard(PawnDiscColor entranceColor1, PawnDiscColor drColor1, PawnDiscColor entranceColor2,
                        PawnDiscColor drColor2, SchoolBoard sb) throws EmptyException {
        sb.getEntrance().removeStudent(entranceColor1);
        sb.getEntrance().addStudent(sb.getDiningRoom().getContainer().retrieveStudent(drColor1));
        sb.getDiningRoom().getContainer().retrieveStudent(drColor1);
        sb.getDiningRoom().addStudent(sb.getEntrance().removeStudent(entranceColor1));

        sb.getEntrance().removeStudent(entranceColor2);
        sb.getEntrance().addStudent(sb.getDiningRoom().getContainer().retrieveStudent(drColor2));
        sb.getDiningRoom().getContainer().retrieveStudent(drColor2);
        sb.getDiningRoom().addStudent(sb.getEntrance().removeStudent(entranceColor2));
    }

}
