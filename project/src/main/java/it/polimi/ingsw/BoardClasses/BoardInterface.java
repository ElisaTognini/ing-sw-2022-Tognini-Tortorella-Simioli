package it.polimi.ingsw.BoardClasses;

import it.polimi.ingsw.Enums.TurnFlow;
import it.polimi.ingsw.TailoredExceptions.ActionNotAuthorizedException;
import it.polimi.ingsw.TailoredExceptions.WrongTurnOrderException;

public class BoardInterface {

    public BoardInterface() {
    }

    public void picksCard(int cardID, String nickname) throws ActionNotAuthorizedException, WrongTurnOrderException{

        /*before applying changes to the state of the game, checks whether the
        * player requesting the action is currently in turn and whether it is the appropriate turn time
        * to perform the action*/
        if(!Board.roundManager.getCurrentState().equals(TurnFlow.BEGINS_TURN)){
            throw new ActionNotAuthorizedException();
        }
        if(!Board.roundManager.getCurrentPlayer().getNickname().equals(nickname)){
            throw new WrongTurnOrderException();
        }
        /*it is the board's responsibility to draw the card and handle
        * exceptions if they occur*/
        Board.drawCardFromDeck(cardID, Board.roundManager.getCurrentPlayer());
    }

    public void moveStudents(String nickname, char[] colors, char[] islandOrDiningRoom, int[] islandID){}
    public void picksCloudTile(String nickname, int cloudNumber){}
}
