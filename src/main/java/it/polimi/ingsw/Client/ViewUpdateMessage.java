package it.polimi.ingsw.Client;

import it.polimi.ingsw.Enums.ActionType;
import it.polimi.ingsw.Server.ServerMessage;

import java.util.ArrayList;

/* every time the model updates itself, it sends a message of this type towards the
* virtualView, so that the virtualView can send it to the client and the client
* can view the update. */
public class ViewUpdateMessage implements ServerMessage {
    private String actionPerformer;
    private String playerInTurn;
    private ActionType actionType;
    private ArrayList<String> schoolBoards;
    private ArrayList<String> changedIsland;
    private ArrayList<String> changesDR;
    private ArrayList<String> changesEntrance;
    private ArrayList<String> changesPT;
    private ArrayList<String> changesCloud;
    private Integer movesMN;
    private Integer choosesAssCard;
    private String choosesWizard;
    private Integer choosesCharCard;

    public void setActionPerformer(String performer){ this.actionPerformer = performer;}
    public void setPlayerInTurn(String playerInTurn){this.playerInTurn = playerInTurn;}
    public void setActionType(ActionType actionType) { this.actionType = actionType; }
    public void setSchoolBoards(ArrayList<String> schoolBoards) { this.schoolBoards = schoolBoards; }
    public void setChangedIsland(ArrayList<String> changedIsland) { this.changedIsland = changedIsland; }
    public void setChangesDR(ArrayList<String> changesDR) {this.changesDR = changesDR; }
    public void setChoosesCharCard(Integer choosesCharCard) { this.choosesCharCard = choosesCharCard; }
    public void setChangesEntrance(ArrayList<String> changesEntrance) { this.changesEntrance = changesEntrance; }
    public void setChangesPT(ArrayList<String> changesPT) { this.changesPT = changesPT; }
    public void setChangesCloud(ArrayList<String> changesCloud) { this.changesCloud = changesCloud; }
    public void setMovesMN(Integer movesMN) { this.movesMN = movesMN; }
    public void setChoosesAssCard(Integer choosesAssCard) { this.choosesAssCard = choosesAssCard; }
    public void setChoosesWizard(String choosesWizard) { this.choosesWizard = choosesWizard; }

    public String getActionPerformer() { return actionPerformer; }
    public String getPlayerInTurn(){return playerInTurn;}
    public ActionType getActionType() { return actionType; }
    public ArrayList<String> getSchoolBoards() { return schoolBoards; }
    public ArrayList<String> getChangedIsland() {return changedIsland; }
    public ArrayList<String> getChangesDR() { return changesDR; }
    public ArrayList<String> getChangesEntrance() { return changesEntrance; }
    public ArrayList<String> getChangesPT() { return changesPT; }
    public ArrayList<String> getChangesCloud() { return changesCloud; }
    public Integer getMovesMN() { return movesMN; }
    public Integer getChoosesAssCard() {return choosesAssCard;}
    public String getChoosesWizard() { return choosesWizard; }
    public Integer getChoosesCharCard() { return choosesCharCard; }

}


