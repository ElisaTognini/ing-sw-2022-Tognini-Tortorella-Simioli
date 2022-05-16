package it.polimi.ingsw.Server;

import it.polimi.ingsw.Client.ExpertViewUpdateMessage;
import it.polimi.ingsw.Client.ViewUpdateMessage;
import it.polimi.ingsw.Controller.Controller;
import it.polimi.ingsw.Enums.GameMode;
import it.polimi.ingsw.Model;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/* Match is a message gateway class that stores references to all components of
* the MVC (including virtualViews) on server side. */

public class Match implements Observer {

    private Server server;
    private Controller controller;
    private Model model;
    private int numberOfPlayers;
    private GameMode gameMode;
    private ArrayList<VirtualView> matchPlayersViews;
    private final int matchID;
    private ViewUpdateMessageWrapper messageWrapper;

    public Match (Server server, int numberOfPlayers, GameMode gameMode, int matchID){
        this.server = server;
        this.numberOfPlayers = numberOfPlayers;
        this.gameMode = gameMode;
        this.matchID = matchID;
        messageWrapper = new ViewUpdateMessageWrapper();
    }

    public void instantiateMVC(Model model, Controller controller, ArrayList<VirtualView> views){
        this.model = model;
        this.controller = controller;
        this.matchPlayersViews = views;
        controller.addMatchAsObserver(this);
        model.addObserver(this);
        model.getTurnUpdates().addObserver(this);
    }

    public GameMode getGameMode(){return gameMode;}

    public synchronized void sendAll(Object message){
        for(VirtualView v : matchPlayersViews){
            v.sendMessage(message);
        }
    }

    public int getMatchID() {
        return matchID;
    }

    public ArrayList<VirtualView> getMatchPlayersViews(){
        return matchPlayersViews;
    }


    @Override
    public void update(Observable o, Object arg) {
        sendAll(arg);
    }
}
