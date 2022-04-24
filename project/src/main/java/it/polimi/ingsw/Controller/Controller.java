package it.polimi.ingsw.Controller;

import it.polimi.ingsw.BoardClasses.RoundManager;
import it.polimi.ingsw.Enums.PawnDiscColor;
import it.polimi.ingsw.Enums.TurnFlow;
import it.polimi.ingsw.Model;

public class Controller {
    private Model model;
    private RoundManager roundManager;
    private boolean isLastRound;

    /* model and roundManager will be given as parameters when the lobby is created
    * in the network classes */
    public Controller(Model model){
        this.model = model;
        this.roundManager = model.getRoundManager();
        /* this boolean variable allows the turnflow to change
        * when it is the last round */
        isLastRound = false;
    }

    public void startGame(){
        /* first player of the game is picked and stored */
        roundManager.computeTurnOrder(roundManager.pickFirstPlayerIndex());
        roundManager.changeState(TurnFlow.BEGINS_TURN);


    }

    /* this method will receive cardID and nickname as a network message */

    public boolean chooseAssistantCard(int cardID, String nickname){

        if(roundManager.getCurrentPlayer().getNickname().equals(nickname)){
            if(roundManager.getCurrentState().equals(TurnFlow.BEGINS_TURN)){
                if(model.getBoard().isCardInDeck(nickname, cardID)) {
                    if (!isLastRound) {
                        if (!roundManager.checkForDupe(cardID)) {
                            roundManager.storeCards(model.getBoard().playAssistantCard(cardID, nickname));
                            roundManager.refreshCurrentPlayer();
                            return true;
                        } else {
                            /* the view will display that the card has already been played */
                        }
                    }
                }
                else{
                    /* the view vill display that the card is not present inside the deck */
                }
            }
            else{
                /* the view will display that the action cannot be performed at this point of the turn */
            }
        }
        else{
            /* the view will display that it's not this player's turn */
        }
        return false;
    }

    public boolean moveStudentToDR(PawnDiscColor color, String nickname){

        if(checkStudents(color, nickname)){
            /* specific checks for dining room (missing check for island)
            * + modifies the board's internal state */
        }

        return false;
    }


    /* general checks for students moved to either dining room or an island */
    private boolean checkStudents(PawnDiscColor color, String nickname){

        if(roundManager.getCurrentPlayer().getNickname().equals(nickname)){
            if(roundManager.getCurrentState().equals(TurnFlow.CARD_PICKED)){
                if(!roundManager.threeStudentsMoved(nickname)){
                    if(model.getBoard().getPlayerSchoolBoard(nickname).getEntrance().isColorAvailable(color)){
                        return true;
                    }
                    else{
                        /* the view will display that the color is not available in the entrance */
                    }
                }
                else{
                    /* the view will display that three students have already been moved */
                }
            }
            else{
                /* the view will display that the action cannot be performed at this point of the turn */
            }
        }
        else{
            /* the view will display that it's not this player's turn */
        }

        return false;
    }

}
