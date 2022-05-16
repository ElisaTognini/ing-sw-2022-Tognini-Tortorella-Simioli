package it.polimi.ingsw.Controller;

import it.polimi.ingsw.BoardClasses.RoundManager;
import it.polimi.ingsw.Enums.*;
import it.polimi.ingsw.Model;
import it.polimi.ingsw.NotifyArgsController;
import it.polimi.ingsw.Player;
import it.polimi.ingsw.Server.BaseServerMessage;
import it.polimi.ingsw.Server.CustomMessage;
import it.polimi.ingsw.Server.Match;

import java.util.Observable;

public class BaseActionController extends Observable {
    private Model model;
    private RoundManager roundManager;
    private boolean isLastRound;
    private Player winner;
    private Match match;

    /* model and roundManager will be given as parameters when the lobby is created
    * in the network classes */

    //CHECK DRAFT FOR ASSIGN WIZARDS (LINE 167) - to do

    public BaseActionController(Model model){
        this.model = model;
        this.roundManager = model.getRoundManager();
        /* this boolean variable allows the turnflow to change
        * when it is the last round */
        isLastRound = false;
    }

    public void startGame(){
        /* first player of the game is picked and stored */
        model.getBoard().setup();
        roundManager.computeTurnOrder(roundManager.pickFirstPlayerIndex());
        roundManager.changeState(TurnFlow.BEGINS_TURN);

       // assignWizards();

    }

    public void endGame(){
        winner = model.getWinner();
        winner.setWinner();
        model.getRoundManager().notifyObservers(ActionType.END_GAME);
        /* view will display that winner won the game, net will close connections etc... */
    }

    /* -------------- METHODS THAT CHECK AND ENACT PLAYER'S ACTIONS --------------- */

    /* this method will receive cardID and nickname as a network message */
    public synchronized boolean chooseAssistantCard(int cardID, String nickname){

        if(roundManager.getCurrentPlayer().getNickname().equals(nickname)){
            if(roundManager.getCurrentState().equals(TurnFlow.BEGINS_TURN)){
                if(model.getBoard().isCardInDeck(nickname, cardID)) {
                    if (!isLastRound) {
                        if (!roundManager.checkForDupe(cardID)) {
                            roundManager.storeCards(model.getBoard().playAssistantCard(cardID, nickname));
                            model.getBoard().getPlayersDeck(nickname).removeCard(cardID);
                            model.getBoard().notifyObservers();
                            roundManager.refreshCurrentPlayer();
                            return true;
                        } else {
                            /* the view will display that the card has already been played */
                            notifyObservers(new NotifyArgsController(nickname,
                                    new BaseServerMessage(CustomMessage.cardAlreadyPlayedError), NotifyType.SEND_ERROR));
                        }
                    }
                }
                else{
                    /* the view will display that the card is not present inside the deck */
                    notifyObservers(new NotifyArgsController(nickname,
                            new BaseServerMessage(CustomMessage.cardNotPresentError), NotifyType.SEND_ERROR));
                }
            }
            else{
                /* the view will display that the action cannot be performed at this point of the turn */
                notifyObservers(new NotifyArgsController(nickname,
                        new BaseServerMessage(CustomMessage.turnFlowError), NotifyType.SEND_ERROR));
            }
        }
        else{
            /* the view will display that it's not this player's turn */
            notifyObservers(new NotifyArgsController(nickname,
                    new BaseServerMessage(CustomMessage.notYourTurnError), NotifyType.SEND_ERROR));
        }
        return false;
    }

    /* after checking if the move is legal, student is moved to dining room from current
    * player's entrance */
    public synchronized boolean moveStudentToDR(PawnDiscColor color, String nickname){

        if(checkStudents(color, nickname)){
            if(!model.getBoard().getPlayerSchoolBoard(nickname).getDiningRoom().checkIfDiningRoomIsFull(color)){
                model.getBoard().moveStudent(color, nickname);
                roundManager.increaseMovedStudents();
                actionPhaseCurrentPlayer(nickname);
                return true;
            }else{
                /* view will display that dining room has no more available spaces for desired color */
                notifyObservers(new NotifyArgsController(nickname,
                        new BaseServerMessage(CustomMessage.fullDRError), NotifyType.SEND_ERROR));
            }
        }
        return false;
    }

    /* after checking if the move is legal, student is moved to selected island from current
     * player's entrance */
    public synchronized boolean moveStudentToIsland(PawnDiscColor color, String nickname, int islandID){

        if(checkStudents(color, nickname)){
            model.getBoard().moveStudent(color, nickname, islandID);
            roundManager.increaseMovedStudents();
            actionPhaseCurrentPlayer(nickname);
            return true;
        }

        return false;
    }

    /* method allows current player to pick a cloud if the turn timing is right,
    if the supplied cloud id is valid and if the chosen cloud is not empty  */
    public synchronized boolean picksCloud(String nickname, int cloudID){

        if(isLastRound){
            //display last round message
            notifyObservers(new NotifyArgsController(nickname,
                    new BaseServerMessage(CustomMessage.lastRound), NotifyType.SEND_ALL));
            return true;
        }

        if(roundManager.getCurrentPlayer().getNickname().equals(nickname)) {
            if (roundManager.getCurrentState().equals(TurnFlow.MOVED_STUDENTS)) {
                if(!(cloudID > model.getNumberOfClouds()-1 || cloudID < 0)){
                    if(!model.getBoard().getCloud(cloudID).isCloudEmpty()){
                        model.getBoard().chooseCloudTile(nickname, cloudID);
                        roundManager.refreshCurrentPlayerAction();
                        if(model.isGameOver()){
                            endGame();
                        }
                        if(model.getBoard().allCloudsPicked()){
                            roundManager.changeState(TurnFlow.PICKED_CLOUD);
                            startNewRound();
                        }
                        return true;
                    }else{
                        notifyObservers(new NotifyArgsController(nickname,
                                new BaseServerMessage(CustomMessage.emptyCloudError), NotifyType.SEND_ERROR));
                    }
                }else{
                    notifyObservers(new NotifyArgsController(nickname,
                            new BaseServerMessage(CustomMessage.invalidCloudIDError), NotifyType.SEND_ERROR));
                }
            } else {
                notifyObservers(new NotifyArgsController(nickname,
                        new BaseServerMessage(CustomMessage.turnFlowError), NotifyType.SEND_ERROR));
            }
        }else{
            notifyObservers(new NotifyArgsController(nickname,
                    new BaseServerMessage(CustomMessage.notYourTurnError), NotifyType.SEND_ERROR));
        }
        return false;
    }

    /* draft for assignWizards - to be checked
    public synchronized boolean assignWizards(Wizards wizard){
        for(Player p : model.getRoundManager().getPlayerList()){
            if(p.getWizard().equals(wizard)){
                return false;
            }
        }
        return true;
    }*/

    /* --------------- PRIVATE UTILITY METHODS ---------------- */

    /* general checks for students moved to either dining room or an island */
    private synchronized boolean checkStudents(PawnDiscColor color, String nickname){

        if(roundManager.getCurrentPlayer().getNickname().equals(nickname)){
            if(roundManager.getCurrentState().equals(TurnFlow.CARD_PICKED)){
                if(!roundManager.threeStudentsMoved()){
                    if(model.getBoard().getPlayerSchoolBoard(nickname).getEntrance().isColorAvailable(color)){
                        return true;
                    }
                    else{
                        /* the view will display that the color is not available in the entrance */
                        notifyObservers(new NotifyArgsController(nickname,
                                new BaseServerMessage(CustomMessage.colorNotAvailableError), NotifyType.SEND_ERROR));
                    }
                }
                else{
                    /* the view will display that three students have already been moved */
                    notifyObservers(new NotifyArgsController(nickname,
                            new BaseServerMessage(CustomMessage.allStudentsMovedError), NotifyType.SEND_ERROR));
                }
            }
            else{
                /* the view will display that the action cannot be performed at this point of the turn */
                notifyObservers(new NotifyArgsController(nickname,
                        new BaseServerMessage(CustomMessage.turnFlowError), NotifyType.SEND_ERROR));
            }
        }
        else{
            /* the view will display that it's not this player's turn */
            notifyObservers(new NotifyArgsController(nickname,
                    new BaseServerMessage(CustomMessage.notYourTurnError), NotifyType.SEND_ERROR));
        }

        return false;
    }

    /* private method that invokes model's methods to move mother nature and
    * attempt to conquer an island if the current player has moved three students */
    private synchronized void actionPhaseCurrentPlayer(String nickname){
        if(roundManager.threeStudentsMoved()){
            /* moves mn according to card movements */
            model.getBoard().moveMotherNature(roundManager.getCurrentPlayersCard().getMotherNatureMovements());
            /* attempts to conquer an island */
            model.getBoard().assignProfessors();
            /*checks if player has enough towers: if not, current player is the winner.*/
            if(model.getBoard().isGameOver()){
                endGame();
            }
            model.getBoard().conquerIsland();
            /*attempts to merge islands*/
            model.getBoard().checkForMerge(nickname);
            if(model.isGameOver()){
                endGame();
            }
            roundManager.changeState(TurnFlow.MOVED_STUDENTS);
        }
    }

    /* this method starts a new round once all players have picked
    * a cloud tile*/
    private synchronized void startNewRound(){
        isLastRound = model.getBoard().isLastRound();
        roundManager.startRound();
        model.getBoard().roundSetup();
        /*checks if studentBag is empty or if decks only
          have one card left ; if any of those conditions is true current round will be last round*/
        if(model.getBoard().getDecks().get(0).size() == 1){
            isLastRound = true;
        }
        notifyObservers(new NotifyArgsController(null,
                new BaseServerMessage(CustomMessage.startNewRound), NotifyType.SEND_ALL));
    }
}
