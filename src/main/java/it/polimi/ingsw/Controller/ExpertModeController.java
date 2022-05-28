package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.BoardClasses.BoardExpert;
import it.polimi.ingsw.Model.BoardClasses.RoundManager;
import it.polimi.ingsw.Utils.Enums.NotifyType;
import it.polimi.ingsw.Utils.Enums.TurnFlow;
import it.polimi.ingsw.Model.Model;
import it.polimi.ingsw.Utils.NetMessages.NotifyArgsController;
import it.polimi.ingsw.Utils.NetMessages.BaseServerMessage;
import it.polimi.ingsw.Utils.NetMessages.CustomMessage;
import it.polimi.ingsw.Server.Match;

import java.util.Observable;

public class ExpertModeController extends Observable {
    /* controller class that handles expert game mode -
    * is only instantiated in the controller if expert mode has
    * been chosen for the game */

    private Model model;
    private RoundManager roundManager;

    public ExpertModeController(Model model){
        this.model = model;
        this.roundManager = model.getRoundManager();
    }

    /* this class handles expert mode functionalities:
    * - using the character card */
    public synchronized boolean useCharacterCard(String nickname, Object o, int cardID) {
        BoardExpert board;
        board = (BoardExpert) model.getBoard();
        if (roundManager.getCurrentPlayer().getNickname().equals(nickname)) {
            if (!roundManager.getCurrentState().equals(TurnFlow.BEGINS_TURN)) {
                if (board.checkIfCardPresent(cardID)) {
                    if (board.getPlayersCoinCounter(nickname).checkIfEnoughCoins(
                            board.getCardsCost(cardID))) {
                        if(board.isActionForbidden(cardID, o, nickname)) {
                            board.purchaseCharacterCard(nickname, cardID);
                            try {
                                board.useCard(o, nickname, cardID);
                            } catch (IllegalArgumentException e) {
                                //prints wrong param format error
                                notifyObservers(new NotifyArgsController(nickname,
                                        new BaseServerMessage(CustomMessage.wrongFormat), NotifyType.SEND_ERROR));
                            }
                        }else{
                            notifyObservers(new NotifyArgsController(nickname,
                                    new BaseServerMessage(CustomMessage.actionForbiddenError), NotifyType.SEND_ERROR));
                        }
                    } else {
                        //player doesn't have enough coins
                        notifyObservers(new NotifyArgsController(nickname,
                                new BaseServerMessage(CustomMessage.notEnoughCoinsError), NotifyType.SEND_ERROR));

                    }
                } else{
                        //card has not been extracted
                    notifyObservers(new NotifyArgsController(nickname,
                            new BaseServerMessage(CustomMessage.charCardNotExtractedError), NotifyType.SEND_ERROR));
                    }
                } else {
                    //not the right moment in the turn
                notifyObservers(new NotifyArgsController(nickname,
                        new BaseServerMessage(CustomMessage.turnFlowError), NotifyType.SEND_ERROR));
                }
            } else {
                //error message: not your turn
            notifyObservers(new NotifyArgsController(nickname,
                    new BaseServerMessage(CustomMessage.notYourTurnError), NotifyType.SEND_ERROR));
            }
        return false;
    }
}
