package it.polimi.ingsw;

import it.polimi.ingsw.BoardClasses.RoundManager;
import it.polimi.ingsw.Enums.TurnFlow;

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


}
