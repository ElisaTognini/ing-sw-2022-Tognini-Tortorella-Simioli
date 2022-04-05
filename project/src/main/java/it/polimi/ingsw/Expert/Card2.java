package it.polimi.ingsw.Expert;

import it.polimi.ingsw.BasicElements.Professor;
import it.polimi.ingsw.BoardClasses.Board;
import it.polimi.ingsw.BoardClasses.BoardExpert;
import it.polimi.ingsw.Enums.PawnDiscColor;
import it.polimi.ingsw.SchoolBoardClasses.SchoolBoard;
import it.polimi.ingsw.TailoredExceptions.ActionNotAuthorizedException;

import java.util.ArrayList;

/* take control of any number of professors for the turn */
public class Card2 extends CharacterCardTemplate{

    public Card2(BoardExpert board){
        super(board);
        cardID = 2;
        cost = 2;
    }

    public void useCard(String nickname, ArrayList<PawnDiscColor> to_control) throws ActionNotAuthorizedException {
        SchoolBoard sb;

        sb = board.getPlayerSchoolBoard(nickname);

    }
}
