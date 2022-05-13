package it.polimi.ingsw.Server;

import it.polimi.ingsw.Controller.Controller;
import it.polimi.ingsw.Enums.GameMode;
import it.polimi.ingsw.Model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.ClientInfoStatus;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private final int PORT = 12345;
    private ServerSocket serverSocket;
    private ExecutorService executor = Executors.newFixedThreadPool(128);
    private ArrayList<ClientConnection> waitingClients;
    private ArrayList<Match> matches;
    private int matchPlayers;

    public Server(){
        try{
            this.serverSocket = new ServerSocket(PORT);
        } catch (IOException e){
            System.err.println("Error while creating server socket");
        }
    }

    public synchronized void lobby(ClientConnection c){
        GameMode gameMode;
        String nickChecker;
        waitingClients.add(c);
        if(waitingClients.size() == 1 ){
            c.send(new SetupServerMessage(CustomMessage.requestNumberOfPlayers));
            matchPlayers = c.parseNumberOfPlayers();
            c.send(new SetupServerMessage(CustomMessage.requestGameMode));
            gameMode = c.parseGameMode();
            c.send(new SetupServerMessage(CustomMessage.askNickname));
            c.setNickname(c.parseNickname());
            /* Now creating match*/
            matches.add(new Match(this, matchPlayers, gameMode, matches.size()));
        }
        else if(waitingClients.size() == matchPlayers){
            do {
                c.send(new SetupServerMessage(CustomMessage.askNickname));
                nickChecker = c.parseNickname();
            }while(isNicknameDuplicated(nickChecker, c));
            matchInitializer();
            waitingClients.clear();
            matches.get(matches.size()-1).sendAll(new BaseServerMessage(CustomMessage.matchStarting));
        }
        else if(1 < waitingClients.size() && waitingClients.size() < matchPlayers){
            do {
                c.send(new SetupServerMessage(CustomMessage.askNickname));
                nickChecker = c.parseNickname();
            }while(isNicknameDuplicated(nickChecker, c));
        }
    }

    private boolean isNicknameDuplicated(String n, ClientConnection conn){
        for(ClientConnection c: waitingClients){
            if(n.equals(c.getNickname())) {
                c.send(new SetupServerMessage(CustomMessage.duplicatedNickname));
                return true;
            }
        }
        return false;
    }

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
            v.addControllerAsObserver(controller);
        }
        matches.get(matches.size()-1).instantiateMVC(model, controller, virtualViews);

        /* addObserver to model --> virtualViews are its observers */

        for(VirtualView v: virtualViews){
            v.addObserver(controller);
        }
    }

    public void run(){
        while(true){
            try{
                Socket newSocket = serverSocket.accept();
                System.out.println("Connection established.");
                ClientConnection clientConnection = new ClientConnection(newSocket, this);
                executor.submit(clientConnection);
                System.out.println("ClientConnection submitted to executor");
            } catch (IOException e){
                System.err.println("Connection error!");
            }
        }
    }

    /* everytime someone disconnects, the match is over. */
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

    private Match getMatchByID(int matchID) throws IllegalArgumentException{
        for(Match m : matches){
            if(m.getMatchID() == matchID)
                return m;
        }
        throw new IllegalArgumentException();
    }
}
