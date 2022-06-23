package it.polimi.ingsw.Server;

import it.polimi.ingsw.Controller.Controller;
import it.polimi.ingsw.Utils.Enums.GameMode;
import it.polimi.ingsw.Model.Model;
import it.polimi.ingsw.Utils.NetMessages.*;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private final int PORT = 12345;
    private ServerSocket serverSocket;
    private ExecutorService executor = Executors.newFixedThreadPool(128);
    private ArrayList<ClientConnection> waitingClients = new ArrayList<>();
    private ArrayList<Match> matches;
    private int matchPlayers;


    /**
     * constructor to Server class, creates a server socket listening on PORT
     * */
    public Server(){
        try{
            this.serverSocket = new ServerSocket(PORT);
        } catch (IOException e){
            System.err.println("Error while creating server socket");
        }
        waitingClients = new ArrayList<>();
        matches = new ArrayList<>();
    }

    /**
     * method stores each new connected user in waitingClients. If waitingClients
     * is empty when a new user joins, user is asked to provide the number of players and mode
     * for the next match. Once enough users are connected and therefore stored in waitingClients, the list
     * is cleared and a new match starts. The server can then resume waiting for a new first player,
     * and the process repeats so that multiple parallel matches can be hosted.
     * @param c of type ClientConnection represents the connected user
     */
    public synchronized void lobby(ClientConnection c){
        GameMode gameMode;
        waitingClients.add(c);
        if(waitingClients.size() == 1 ){
            c.send(new SetupServerMessage(CustomMessage.requestNumberOfPlayers));
            matchPlayers = c.parseNumberOfPlayers();
            c.send(new SetupServerMessage(CustomMessage.requestGameMode));
            gameMode = c.parseGameMode();
            c.send(new SetupServerMessage(CustomMessage.askNickname));
            String nick = c.parseNickname();
            c.send(new NicknameMessage(nick));
            c.setNickname(nick);
            /* Now creating match*/
            matches.add(new Match(this, matchPlayers, gameMode, matches.size()));
        }
        else if(waitingClients.size() == matchPlayers){
            String nickChecker;
            do {
                c.send(new SetupServerMessage(CustomMessage.askNickname));
                nickChecker = c.parseNickname();
                if(isNicknameDuplicated(nickChecker, c)) c.send(new BaseServerMessage(CustomMessage.duplicatedNickname));
            }while(isNicknameDuplicated(nickChecker, c));
            c.send(new NicknameMessage(nickChecker));
            c.setNickname(nickChecker);
            matchInitializer();
            waitingClients.clear();
        }
        else if(1 < waitingClients.size() && waitingClients.size() < matchPlayers){
            String nickChecker;
            do {
                c.send(new SetupServerMessage(CustomMessage.askNickname));
                nickChecker = c.parseNickname();
            }while(isNicknameDuplicated(nickChecker, c));
            c.send(new NicknameMessage(nickChecker));
            c.setNickname(nickChecker);
        }
    }

    /**
     * method checks whether the nickname chosen by the currently handled user has already been
     * picked.
     * @return of type boolean - false if nickname is not duplicated, true otherwise.
     * @param n of type String
     * @param conn of type ClientConnection
     * */
    private boolean isNicknameDuplicated(String n, ClientConnection conn){
        for(ClientConnection c: waitingClients){
            if(n.equals(c.getNickname())) {
                c.send(new SetupServerMessage(CustomMessage.duplicatedNickname));
                return true;
            }
        }
        return false;
    }

    /**
     * this method instantiates and connects all of the necessary
     * MVC components for a match
     * */
    private void matchInitializer(){
        Model model;
        Controller controller;
        ArrayList<VirtualView> virtualViews = new ArrayList<>();
        String[] nicknames = new String[matchPlayers];
        int i = 0;

        for(ClientConnection c : waitingClients){
            nicknames[i] = c.getNickname();
            virtualViews.add(new VirtualView(c));
            i++;
        }
        model = new Model(matches.get(matches.size()-1).getGameMode(), nicknames, matchPlayers);
        controller = new Controller(model);
        for(VirtualView v : virtualViews){
            v.addObserver(controller);
        }
        matches.get(matches.size()-1).instantiateMVC(model, controller, virtualViews);

        for(VirtualView v: virtualViews){
            v.addObserver(controller);
        }

        matches.get(matches.size()-1).sendAll(new BaseServerMessage(CustomMessage.matchStarting));
        matches.get(matches.size()-1).startGame();
    }

    /**
     * method run is responsible for listening for client connections and accepting
     * them, creating a new object of type ClientConnection for each one*/
    public void run(){
        while(true){
            try{
                Socket newSocket = serverSocket.accept();
                System.out.println("Connection established.");
                ClientConnection clientConnection = new ClientConnection(newSocket, this);
                executor.submit(clientConnection);
            } catch (IOException e){
                System.err.println("Connection error!");
            }
        }
    }

    /**
     * @param clientConnection of type ClientConnection - provides a reference
     * for the user who has disconnected.
     * This method resets the connection of all the users involved in the same match, effectively
     * terminating it for all of them, without having influence on the other concurrent matches*/
    public void deregisterConnection(ClientConnection clientConnection) {
        ArrayList<VirtualView> match = getMatchByID(clientConnection.getMatchID()).getMatchPlayersViews();
        int id = clientConnection.getMatchID();
        for(VirtualView v : match){
            if(v.getClientConnection() != null){
                v.getClientConnection().closeConnection();
            }
        }
        matches.remove(getMatchByID(id));
        System.out.println("now playing " + matches.size() + " concurrent matches");
    }

    /**
     * this method retrieves the match associated to the given ID from the
     * match list where all concurrent and active games are stored
     * @param matchID - represents the ID of the match that is looked to have a reference to
     * @return of type Match - returns the match associated with the required ID
     * @throws IllegalArgumentException when the id that is provided isn't valid
     */
    private Match getMatchByID(int matchID) throws IllegalArgumentException{
        for(Match m : matches){
            if(m.getMatchID() == matchID)
                return m;
        }
        throw new IllegalArgumentException();
    }

}
