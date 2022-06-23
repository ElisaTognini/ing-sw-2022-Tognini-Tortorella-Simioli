package it.polimi.ingsw.Server;

import it.polimi.ingsw.Controller.Controller;
import it.polimi.ingsw.Utils.Enums.GameMode;
import it.polimi.ingsw.Model.Model;
import it.polimi.ingsw.Utils.NetMessages.GameModeMessage;
import it.polimi.ingsw.Utils.NetMessages.NotifyArgsController;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/** Match is a message gateway class that stores references to all components of
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

    /** constructor to Match class, assigns values received as parameters to local variables*/
    public Match (Server server, int numberOfPlayers, GameMode gameMode, int matchID){
        this.server = server;
        this.numberOfPlayers = numberOfPlayers;
        this.gameMode = gameMode;
        this.matchID = matchID;
        messageWrapper = new ViewUpdateMessageWrapper();
    }

    /** this method stores all of the server-side MVC components locally after taking
     * them as parameters.*/
    public void instantiateMVC(Model model, Controller controller, ArrayList<VirtualView> views){
        this.model = model;
        this.controller = controller;
        this.matchPlayersViews = views;
        controller.addMatchAsObserver(this);
        model.addObserver(this);
        model.getTurnUpdates().addObserver(this);
        for(VirtualView v : views){
            v.addObserver(controller);
            v.getClientConnection().setMatchID(matchID);
        }
        for(VirtualView v : views){
            v.getClientConnection().startMatch();
        }

        sendAll(new GameModeMessage(gameMode));

    }

    /** this method acts on the controller so that the game logic
     * can start  */
    public void startGame(){
        controller.getBaseActionController().startGame();
    }

    /** getter method for GameMode
     * @return gameMode of type GameMode*/
    public GameMode getGameMode(){return gameMode;}

    /** this method loops through the virtualViews of the players and
     * sends a generic message to each of them
     * @param message of type Object is a general message sent to each user*/
    public synchronized void sendAll(Object message){
        for(VirtualView v : matchPlayersViews){
            v.sendMessage(message);
        }
    }

    /** getter method for match identifier
     * @return matchID of type int */
    public int getMatchID() {
        return matchID;
    }

    /** getter method for list of virtual views for each player partaking in the match
     * @return matchPlayersViews which is an arrayList storing direct references
     * to each virtual view*/
    public ArrayList<VirtualView> getMatchPlayersViews(){
        return matchPlayersViews;
    }

    /** this method returns the virtualView belonging to the player whose nickname is
     * given as parameter
     * @param nickname of type String is the nickname of the player whose VirtualView I am requesting
     * @see VirtualView*/
    private synchronized  VirtualView getPlayersVirtualView(String nickname){
        VirtualView view = null;
        for(VirtualView v : matchPlayersViews){
            if(v.getNickname().equals(nickname))
                view = v;
        }
        return view;
    }

    /** this method sends a message to a specific user via its virtualView
     * @param parameter of type NotifyArgsController stores both the nickname of the
     * message's recipient and the generic message that is to be sent*/
    public synchronized void sendTo(NotifyArgsController parameter){
        getPlayersVirtualView(parameter.getNickname()).sendMessage(parameter.getMessage());
    }

    /** this method overrides the Update method:
     * @see Observer
     * based on the type of parameter that is given
     * @param arg of type Object we can determine whether to send all of the players
     * or just one*/
    @Override
    public void update(Observable o, Object arg) {
        if(arg instanceof NotifyArgsController &&
                ((NotifyArgsController)arg).getNickname() != null){
            sendTo((NotifyArgsController) arg);
        }
        else
            sendAll(arg);
    }
}
