package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.BoardClasses.RoundManager;
import it.polimi.ingsw.Model.Model;
import it.polimi.ingsw.Utils.Enums.ActionType;
import it.polimi.ingsw.Utils.Enums.NotifyType;
import it.polimi.ingsw.Utils.Enums.PawnDiscColor;
import it.polimi.ingsw.Utils.Enums.TurnFlow;
import it.polimi.ingsw.Utils.NetMessages.NotifyArgsController;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Utils.NetMessages.BaseServerMessage;
import it.polimi.ingsw.Utils.NetMessages.CustomMessage;
import it.polimi.ingsw.Utils.NetMessages.PlayedCardMessage;

import java.util.Observable;

/**
 * BaseActionController class is the base controller class for both base and expert mode.
 *
 * @see java.util.Observable
 * */

public class BaseActionController extends Observable {
    private Model model;
    private RoundManager roundManager;
    private boolean isLastRound;
    private Player winner;

    /**
     * Constructor BaseActionController creates a new BaseActionController instance.
     *
     * @param model of type Model - Model reference.
     */

    public BaseActionController(Model model){
        this.model = model;
        this.roundManager = model.getRoundManager();
        /* this boolean variable allows the turnflow to change
        * when it is the last round */
        isLastRound = false;
    }

    /**
     * Method startGame picks and stores the first player of the game.
     * */

    public void startGame(){
        roundManager.computeTurnOrder(roundManager.pickFirstPlayerIndex());
        model.getRoundManager().notifyObservers(ActionType.PLAYER_CHANGE);
        model.getBoard().setup();
        roundManager.changeState(TurnFlow.BEGINS_TURN);

    }

    /**
     * Method endGame sets the winner and notifies that the game has been won.
     * */

    public void endGame(){
        winner = model.getWinner();
        winner.setWinner();
        model.getRoundManager().change();
        model.getRoundManager().notifyObservers(ActionType.END_GAME);
    }

    /* -------------- METHODS THAT CHECK AND ENACT PLAYER'S ACTIONS --------------- */


    /**
     * Method chooseAssistantCard checks that whoever's playing the card is in turn and it's the right point
     * in their turn to play an assistant card. In that case, this method stores the card among the assistant
     * cards played in the round and removes it from the player's deck.
     *
     * @param cardID - of type int - cardID reference.
     * @param nickname - of type String - current player's nickname reference
     *
     * @return boolean - true if the action has been performed, false if not.
     * */

    public synchronized boolean chooseAssistantCard(int cardID, String nickname){
        PlayedCardMessage playedCard = new PlayedCardMessage();

        if(roundManager.getCurrentPlayer().getNickname().equals(nickname)){
            if(roundManager.getCurrentState().equals(TurnFlow.BEGINS_TURN)){
                if(model.getBoard().isCardInDeck(nickname, cardID)) {
                        if (!roundManager.checkForDupe(cardID) || model.getBoard().getPlayersDeck(nickname).size() == 1) {
                            playedCard.setCardID(String.valueOf(cardID));
                            playedCard.setOwner(nickname);
                            playedCard.setPowerFactor(
                                    String.valueOf(model.getBoard().getPlayersDeck(nickname).drawCard(cardID).getMotherNatureMovements()));
                            roundManager.storeCards(model.getBoard().playAssistantCard(cardID, nickname));
                            setChanged();
                            notifyObservers(playedCard);
                            model.getBoard().getPlayersDeck(nickname).removeCard(cardID);
                            model.getBoard().notifyObservers();
                            if(roundManager.isPlanningPhase())
                                roundManager.refreshCurrentPlayer();
                            for(Player p : model.getPlayerList()){
                                if(!p.getCardPicked()){
                                    return true;
                                }
                            }
                            return true;
                        } else {
                            /* the view will display that the card has already been played by another player */
                            setChanged();
                            notifyObservers(new NotifyArgsController(nickname,
                                    new BaseServerMessage(CustomMessage.cardAlreadyPlayedError), NotifyType.SEND_ERROR));
                        }
                }
                else{
                    /* the view will display that the card is not present inside the deck */
                    setChanged();
                    notifyObservers(new NotifyArgsController(nickname,
                            new BaseServerMessage(CustomMessage.cardNotPresentError), NotifyType.SEND_ERROR));
                }
            }
            else{
                /* the view will display that the action cannot be performed at this point of the turn */
                setChanged();
                notifyObservers(new NotifyArgsController(nickname,
                        new BaseServerMessage(CustomMessage.turnFlowError), NotifyType.SEND_ERROR));
            }
        }
        else{
            /* the view will display that it's not this player's turn */
            setChanged();
            notifyObservers(new NotifyArgsController(nickname,
                    new BaseServerMessage(CustomMessage.notYourTurnError), NotifyType.SEND_ERROR));
        }
        return false;
    }

    /**
     * Method moveStudentToDR, after checking if the move is legal, moves a student to dining room
     * from current player's entrance.
     *
     * @param color - of type PawnDiscColor - references the color of the student.
     * @param nickname - of type String - current player's nickname reference.
     *
     * @return boolean - true if the action has been performed, false if not.
     * */
    public synchronized boolean moveStudentToDR(PawnDiscColor color, String nickname){

        if(checkStudents(color, nickname)){
            if(!model.getBoard().getPlayerSchoolBoard(nickname).getDiningRoom().checkIfDiningRoomIsFull(color)){
                model.getBoard().moveStudent(color, nickname);
                roundManager.increaseMovedStudents();
                actionPhaseCurrentPlayer();
                return true;
            }else{
                /* view will display that dining room has no more available spaces for desired color */
                setChanged();
                notifyObservers(new NotifyArgsController(nickname,
                        new BaseServerMessage(CustomMessage.fullDRError), NotifyType.SEND_ERROR));
            }
        }
        return false;
    }


    /**
     * Method moveStudentToIsland, after checking if the move is legal, moves a student
     * to selected island from current player's entrance.
     *
     * @param color - of type PawnDiscColor - references the color of the student.
     * @param nickname - of type String - current player's nickname reference.
     * @param islandID - of type int - identifies the island on which the student is moved.
     *
     * @return boolean - true if the action has been performed, false if not.
     * */

    public synchronized boolean moveStudentToIsland(PawnDiscColor color, String nickname, int islandID){

        if(checkStudents(color, nickname)) {
            if (islandID < model.getBoard().getIslandList().size()) {
                model.getBoard().moveStudent(color, nickname, islandID);
                roundManager.increaseMovedStudents();
                actionPhaseCurrentPlayer();
                return true;
            } else {
                setChanged();
                notifyObservers(new NotifyArgsController(nickname,
                        new BaseServerMessage(CustomMessage.invalidIslandIDError), NotifyType.SEND_ERROR));
            }
        }

        return false;
    }


    /**
     * Method picksCloud allows current player to pick a cloud if the turn timing is right,
     * if the supplied cloud id is valid and if the chosen cloud is not empty.
     *
     * @param nickname - of type String - current player's nickname reference.
     * @param cloudID - of type int - identifies picked cloud.
     *
     * @return boolean - true if action has been correctly performed, false if not.
     */
    public synchronized boolean picksCloud(String nickname, int cloudID){

        if(isLastRound){
            roundManager.refreshCurrentPlayerAction();
            return true;
        }

        if(roundManager.getCurrentPlayer().getNickname().equals(nickname)) {
            if (roundManager.getCurrentState().equals(TurnFlow.MOVED_STUDENTS)) {
                if(!(cloudID > model.getNumberOfClouds()-1 || cloudID < 0)){
                    if(!model.getBoard().getCloud(cloudID).isCloudEmpty()){
                        model.getBoard().chooseCloudTile(nickname, cloudID);
                        roundManager.getCurrentPlayer().setCloudPicked(true);
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
                        setChanged();
                        notifyObservers(new NotifyArgsController(nickname,
                                new BaseServerMessage(CustomMessage.emptyCloudError), NotifyType.SEND_ERROR));
                    }
                }else{
                    setChanged();
                    notifyObservers(new NotifyArgsController(nickname,
                            new BaseServerMessage(CustomMessage.invalidCloudIDError), NotifyType.SEND_ERROR));
                }
            } else {
                setChanged();
                notifyObservers(new NotifyArgsController(nickname,
                        new BaseServerMessage(CustomMessage.turnFlowError), NotifyType.SEND_ERROR));
            }
        }else{
            setChanged();
            notifyObservers(new NotifyArgsController(nickname,
                    new BaseServerMessage(CustomMessage.notYourTurnError), NotifyType.SEND_ERROR));
        }
        return false;
    }

    /* --------------- PRIVATE UTILITY METHODS ---------------- */

    /**
     * Method checkStudents makes general checks for students moved to either
     * dining room or an island.
     *
     * @param color - of type PawnDiscColor - references the color of the student moved.
     * @param nickname - of type String - current player's nickname reference.
     *
     * @return boolean - true if action has been correctly performed, false if not.
     * */

    private synchronized boolean checkStudents(PawnDiscColor color, String nickname){

        if(roundManager.getCurrentPlayer().getNickname().equals(nickname)){
            if(roundManager.getCurrentState().equals(TurnFlow.CARD_PICKED)){
                if(!roundManager.threeStudentsMoved()){
                    if(model.getBoard().getPlayerSchoolBoard(nickname).getEntrance().isColorAvailable(color)){
                        return true;
                    }
                    else{
                        /* the view will display that the color is not available in the entrance */
                        setChanged();
                        notifyObservers(new NotifyArgsController(nickname,
                                new BaseServerMessage(CustomMessage.colorNotAvailableError), NotifyType.SEND_ERROR));
                    }
                }
                else{
                    setChanged();
                    /* the view will display that three students have already been moved */
                    notifyObservers(new NotifyArgsController(nickname,
                            new BaseServerMessage(CustomMessage.allStudentsMovedError), NotifyType.SEND_ERROR));
                }
            }
            else{
                setChanged();
                /* the view will display that the action cannot be performed at this point of the turn */
                notifyObservers(new NotifyArgsController(nickname,
                        new BaseServerMessage(CustomMessage.turnFlowError), NotifyType.SEND_ERROR));
            }
        }
        else{
            setChanged();
            /* the view will display that it's not this player's turn */
            notifyObservers(new NotifyArgsController(nickname,
                    new BaseServerMessage(CustomMessage.notYourTurnError), NotifyType.SEND_ERROR));
        }

        return false;
    }

    /**
     * Method actionPhaseCurrentPlayer invokes model's methods to move mother nature and
     * attempt to conquer an island if the current player has moved three students.
     */

    private synchronized void actionPhaseCurrentPlayer(){
        if(roundManager.threeStudentsMoved()){
            /* attempts to conquer an island */
            model.getBoard().moveMotherNature(roundManager.getCurrentPlayersCard().getMotherNatureMovements());
            model.getBoard().assignProfessors();
            /*checks if player has enough towers: if not, current player is the winner.*/
            if(model.getBoard().isGameOver()){
                endGame();
                return;
            }
            model.getBoard().conquerIsland();
            /*attempts to merge islands*/
            model.getBoard().checkForMerge();
            if(model.isGameOver()){
                endGame();
                return;
            }
            roundManager.changeState(TurnFlow.MOVED_STUDENTS);
        }
    }


    /**
     * Method startNewRound starts a new round once all players have picked a cloud tile.
     */

    private synchronized void startNewRound(){

        for(Player p : model.getPlayerList()){
            p.setCloudPicked(false);
        }
        roundManager.startRound();
        model.getBoard().roundSetup();
        isLastRound = model.getBoard().isLastRound();
        /*checks if studentBag is empty or if decks only
          have one card left ; if any of those conditions is true current round will be last round*/
        if((model.getBoard().getDecks().get(0).size() == 1) || (model.getBoard().getStudentBag().checkIfStudentBagEmpty())){
            isLastRound = true;
            setChanged();
            notifyObservers( new BaseServerMessage(CustomMessage.lastRound));
        }

        setChanged();
        notifyObservers(new NotifyArgsController(null,
                new BaseServerMessage(CustomMessage.startNewRound), NotifyType.SEND_ALL));

        model.getRoundManager().setCurrentPlayer(model.getRoundManager().getFirstPlanningPhase());
        model.getRoundManager().change();
        model.getRoundManager().notifyObservers(ActionType.PLAYER_CHANGE);
    }
}
