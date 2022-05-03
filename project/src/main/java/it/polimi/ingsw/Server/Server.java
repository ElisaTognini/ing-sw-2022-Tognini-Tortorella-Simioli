package it.polimi.ingsw.Server;

import it.polimi.ingsw.Enums.GameMode;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
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

    public synchronized void lobby(ClientConnection c){
        GameMode gameMode;
        waitingClients.add(c);
        if(waitingClients.size() == 1 ){
            c.send(new BaseServerMessage(CustomMessage.requestNumberOfPlayers));
            matchPlayers = c.parseNumberOfPlayers();
            c.send(new BaseServerMessage(CustomMessage.requestGameMode));
            gameMode = c.parseGameMode();
            c.send(new BaseServerMessage(CustomMessage.askNickname));
            c.setNickname(c.parseNickname());
            /* Now creating match*/
            matches.add(new Match(this, matchPlayers, gameMode));

        }
    }


}
