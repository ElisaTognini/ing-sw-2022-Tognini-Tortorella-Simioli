package it.polimi.ingsw.Server;

import it.polimi.ingsw.Controller.Controller;
import it.polimi.ingsw.Enums.GameMode;
import it.polimi.ingsw.Model;

import java.util.ArrayList;
import java.util.HashMap;

public class Match {

    private Server server;
    private Controller controller;
    private Model model;
    private int numberOfPlayers;
    private GameMode gameMode;
    private ArrayList<VirtualView> matchPlayersViews;
    private final int matchID;

    public Match(Server server, int numberOfPlayers, GameMode gameMode, int matchID){
        this.server = server;
        this.numberOfPlayers = numberOfPlayers;
        this.gameMode = gameMode;
        this.matchID = matchID;
    }

    /* TO BE CHECKED */
    public void instantiateMVC(Model model, Controller controller, ArrayList<VirtualView> views){
        this.model = model;
        this.controller = controller;
        this.matchPlayersViews = views;
        controller.setMatch(this);
    }

    public GameMode getGameMode(){return gameMode;}

    public synchronized void sendAll(BaseServerMessage message){
        for(VirtualView v : matchPlayersViews){
            v.sendErrorMessage(message);
        }
    }

    public int getMatchID() {
        return matchID;
    }

    public ArrayList<VirtualView> getMatchPlayersViews(){
        return matchPlayersViews;
    }

    public void sendErrorTo(String nickname, BaseServerMessage message){
        for(VirtualView v : matchPlayersViews){
            if(v.getNickname().equals(nickname)){
                v.sendErrorMessage(message);
            }
        }
    }
}
