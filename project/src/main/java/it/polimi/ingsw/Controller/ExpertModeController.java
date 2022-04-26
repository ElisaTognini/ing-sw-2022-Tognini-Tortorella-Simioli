package it.polimi.ingsw.Controller;

import it.polimi.ingsw.BoardClasses.BoardExpert;
import it.polimi.ingsw.BoardClasses.RoundManager;
import it.polimi.ingsw.Enums.TurnFlow;
import it.polimi.ingsw.Model;
import it.polimi.ingsw.Player;

public class ExpertModeController {
    /* controller class that handles expert game mode -
    * is only instantiated in the controller if expert mode has
    * been chosen for the game */

    private Model model;
    private RoundManager roundManager;
    private BaseActionController baseController;

    public ExpertModeController(Model model, BaseActionController controller){
        this.model = model;
        this.roundManager = model.getRoundManager();
        this.baseController = controller;
    }

    /* this class handles expert mode functionalities:
    * - using the character card */
    public boolean useCharacterCard(String nickname, Object o, int cardID){
        BoardExpert board;
        board = (BoardExpert) model.getBoard();
        if(roundManager.getCurrentPlayer().getNickname().equals(nickname)){
            if(!roundManager.getCurrentState().equals(TurnFlow.BEGINS_TURN)){
                //write a method in boardexpert that resets all extras caused by cards when round starts
                if(board.checkIfCardPresent(cardID)){
                    if(board.getPlayersCoinCounter(nickname).checkIfEnoughCoins(
                            board.getCardsCost(cardID))){
                            board.purchaseCharacterCard(nickname, cardID);
                            try {
                                board.useCard(o, nickname, cardID);
                            }catch(IllegalArgumentException e){
                                //prints wrong param format error
                            }
                    }else{
                        //player doesn't have enough coins
                    }
                }else{
                    //card has not been extracted
                }
            }else{
                //not the right moment in the turn
            }
        }else{
            //error message: not your turn
        }
        return false;
    }

}
