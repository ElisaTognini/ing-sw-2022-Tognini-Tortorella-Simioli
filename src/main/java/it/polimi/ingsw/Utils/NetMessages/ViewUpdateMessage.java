package it.polimi.ingsw.Utils.NetMessages;

import java.io.Serializable;
import java.util.ArrayList;

/** contains representation of:
 * - islands
 * - clouds
 * - schoolBoards
 * - assistantCards
 * all the information is stored in arrayLists, and it fully represents the model's current state.
 * These messages are sent each time the model updates itself, so that changes to the game space
 * can be seen by the players with changes to the view (both CLI and GUI)
 * As these messages are sent via sockets, therefore this class implements Serializable
 * @see Serializable
 * @see ServerMessage */

public class ViewUpdateMessage implements ServerMessage, Serializable {

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


