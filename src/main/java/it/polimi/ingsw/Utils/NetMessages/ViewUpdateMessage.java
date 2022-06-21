package it.polimi.ingsw.Utils.NetMessages;

import java.io.Serializable;
import java.util.ArrayList;

/* every time the model updates itself, it sends a message of this type towards the
* virtualView, so that the virtualView can send it to the client and the client
* can view the update. */
public class ViewUpdateMessage implements ServerMessage, Serializable {
    /* must contain representation of:
    * - islands
    * - clouds
    * - schoolboards
    * - assistantCards */

    private ArrayList<String> islands = new ArrayList<>();
    private ArrayList<String> clouds = new ArrayList<>();
    private ArrayList<String> schoolboards = new ArrayList<>();
    private ArrayList<String> decks = new ArrayList<>();
    private int mnPosition;
    private String currentPlayer;

    public ArrayList<String> getIslands() {
        return islands;
    }

    public ArrayList<String> getClouds() {
        return clouds;
    }

    public ArrayList<String> getSchoolboards() {
        return schoolboards;
    }

    public ArrayList<String> getDecks() {
        return decks;
    }

    public String getCurrentPlayer() {
        return currentPlayer;
    }

    public int getMnPosition(){ return mnPosition; }

    public void setCurrentPlayer(String currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public void setMnPosition(int pos) { this.mnPosition = pos; }
}


