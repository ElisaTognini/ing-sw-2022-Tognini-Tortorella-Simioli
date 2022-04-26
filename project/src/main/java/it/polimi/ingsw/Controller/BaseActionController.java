package it.polimi.ingsw.Controller;

import it.polimi.ingsw.BoardClasses.RoundManager;
import it.polimi.ingsw.Enums.PawnDiscColor;
import it.polimi.ingsw.Enums.TurnFlow;
import it.polimi.ingsw.Model;
import it.polimi.ingsw.Player;

public class BaseActionController {
    private Model model;
    private RoundManager roundManager;
    private boolean isLastRound;
    private Player winner;

    /* model and roundManager will be given as parameters when the lobby is created
    * in the network classes */
    public BaseActionController(Model model){
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

       // assignWizards();

    }

    public void endGame(){
        winner = model.getWinner();
        winner.setWinner();
        /* view will display that winner won the game, net will close connections etc... */
    }

    /* -------------- METHODS THAT CHECK AND ENACT PLAYER'S ACTIONS --------------- */

    /* this method will receive cardID and nickname as a network message */
    public boolean chooseAssistantCard(int cardID, String nickname){

        if(roundManager.getCurrentPlayer().getNickname().equals(nickname)){
            if(roundManager.getCurrentState().equals(TurnFlow.BEGINS_TURN)){
                if(model.getBoard().isCardInDeck(nickname, cardID)) {
                    if (!isLastRound) {
                        if (!roundManager.checkForDupe(cardID)) {
                            roundManager.storeCards(model.getBoard().playAssistantCard(cardID, nickname));
                            model.getBoard().getPlayersDeck(nickname).removeCard(cardID);
                            roundManager.refreshCurrentPlayer();
                            return true;
                        } else {
                            /* the view will display that the card has already been played */
                        }
                    }
                }
                else{
                    /* the view will display that the card is not present inside the deck */
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

    /* after checking if the move is legal, student is moved to dining room from current
    * player's entrance */
    public boolean moveStudentToDR(PawnDiscColor color, String nickname){

        if(checkStudents(color, nickname)){
            if(!model.getBoard().getPlayerSchoolBoard(nickname).getDiningRoom().checkIfDiningRoomIsFull(color)){
                model.getBoard().moveStudent(color, nickname);
                roundManager.increaseMovedStudents();
                actionPhaseCurrentPlayer(nickname);
                return true;
            }else{
                /* view will display that dining room has no more available spaces for desired color */
            }
        }
        return false;
    }

    /* after checking if the move is legal, student is moved to selected island from current
     * player's entrance */
    public boolean moveStudentToIsland(PawnDiscColor color, String nickname, int islandID){

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
    public boolean picksCloud(String nickname, int cloudID){

        if(isLastRound){
            //display last round message
            return true;
        }

        if(roundManager.getCurrentPlayer().getNickname().equals(nickname)) {
            if (roundManager.getCurrentState().equals(TurnFlow.MOVED_STUDENTS)) {
                if(!(cloudID > model.getNumberOfClouds()-1 || cloudID < 0)){
                    if(!model.getBoard().getCloud(cloudID).isCloudEmpty()){
                        model.getBoard().chooseCloudTile(nickname, cloudID);
                        roundManager.refreshCurrentPlayerAction();
                        roundManager.changeState(TurnFlow.PICKED_CLOUD);
                        if(model.isGameOver()){
                            endGame();
                        }
                        if(model.getBoard().allCloudsPicked()){
                            startNewRound();
                        }
                        return true;
                    }else{
                        //display cloud is empty error message
                    }
                }else{
                    //error message for invalid cloud id will be displayed
                }
            } else {
                //view will show error message
            }
        }else{
            //view will display not current player error message
        }
        return false;
    }

    /* --------------- PRIVATE UTILITY METHODS ---------------- */

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

    /* private method that invokes model's methods to move mother nature and
    * attempt to conquer an island if the current player has moved three students */
    private void actionPhaseCurrentPlayer(String nickname){
        if(roundManager.threeStudentsMoved(nickname)){
            /* moves mn according to card movements */
            model.getBoard().moveMotherNature(roundManager.getCurrentPlayersCard().getMotherNatureMovements());
            /* attempts to conquer an island */
            model.getBoard().assignProfessors();
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
    private void startNewRound(){
        roundManager.startRound();
        model.getBoard().roundSetup();
        /*checks if studentBag is empty or if decks only
          have one card left ; if any of those conditions is true current round will be last round*/
        if(model.getBoard().getStudentBag().checkIfStudentBagEmpty() ||
            model.getBoard().getDecks().get(0).size() == 1){
            isLastRound = true;
        }
    }

}
