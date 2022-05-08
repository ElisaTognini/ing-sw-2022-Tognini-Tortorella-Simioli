package it.polimi.ingsw.Controller;

import it.polimi.ingsw.BoardClasses.BoardExpert;
import it.polimi.ingsw.BoardClasses.RoundManager;
import it.polimi.ingsw.Enums.TurnFlow;
import it.polimi.ingsw.Model;
import it.polimi.ingsw.Player;
import it.polimi.ingsw.Server.BaseServerMessage;
import it.polimi.ingsw.Server.CustomMessage;
import it.polimi.ingsw.Server.Match;

public class ExpertModeController {
    /* controller class that handles expert game mode -
    * is only instantiated in the controller if expert mode has
    * been chosen for the game */

    private Model model;
    private RoundManager roundManager;
    private Match match;

    public ExpertModeController(Model model){
        this.model = model;
        this.roundManager = model.getRoundManager();
    }

    public void setMatch(Match m){ this.match = m;}

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
                        board.purchaseCharacterCard(nickname, cardID);
                        try {
                            board.useCard(o, nickname, cardID);
                        } catch (IllegalArgumentException e) {
                            //prints wrong param format error
                            match.sendErrorTo(nickname, new BaseServerMessage(CustomMessage.wrongFormat));
                        }
                    } else {
                        //player doesn't have enough coins
                        match.sendErrorTo(nickname, new BaseServerMessage(CustomMessage.notEnoughCoinsError));

                    }
                } else{
                        //card has not been extracted
                        match.sendErrorTo(nickname, new BaseServerMessage(CustomMessage.charCardNotExtractedError));
                    }
                } else {
                    //not the right moment in the turn
                    match.sendErrorTo(nickname, new BaseServerMessage(CustomMessage.turnFlowError));
                }
            } else {
                //error message: not your turn
                match.sendErrorTo(nickname, new BaseServerMessage(CustomMessage.notYourTurnError));
            }
        return false;
    }
}
