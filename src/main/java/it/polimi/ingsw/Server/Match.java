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

    public Match(Server server, int numberOfPlayers, GameMode gameMode){
        this.server = server;
        this.numberOfPlayers = numberOfPlayers;
        this.gameMode = gameMode;
    }

    /* TO BE CHECKED */
    public void instantiateMVC(Model model, Controller controller, ArrayList<VirtualView> views){
        this.model = model;
        this.controller = controller;
        this.matchPlayersViews = views;
    }

    public GameMode getGameMode(){return gameMode;}

    public synchronized void sendAll(ServerMessage message){
        for(VirtualView v : matchPlayersViews){
            /* non è clientConnection a fare la asyncSend, ma un metodo della view (da scrivere)*/
            v.getClientConnection().asyncSend(message);
        }
    }

}
