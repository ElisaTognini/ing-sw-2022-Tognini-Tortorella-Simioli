package it.polimi.ingsw.Server;

import it.polimi.ingsw.Utils.Enums.GameMode;
import it.polimi.ingsw.Utils.NetMessages.BaseServerMessage;
import it.polimi.ingsw.Utils.NetMessages.BaseUserMessage;
import it.polimi.ingsw.Utils.NetMessages.CustomMessage;
import it.polimi.ingsw.Utils.NetMessages.SetupServerMessage;

import java.util.Observable;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

@Deprecated
public class ClientConnection extends Observable implements Runnable {

    /* inputStream è da salvare come attributo o regge il get nei metodi
    * per il numero di giocatori ecc...? */

    private Socket socket;
    private Server server;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private boolean active;
    private String nickname;
    private int matchID;
    private boolean matchHasStarted = false;

    public ClientConnection(Socket socket, Server server){
        this.socket = socket;
        this.server = server;
        active =  true;
    }

    @Override
    public void run() {
        Object read;
        System.out.println("clientconnection run method has been called");
        try {
            System.out.println("try-catch entered");
            in = new ObjectInputStream(socket.getInputStream());
            System.out.println("input stream gained");
            out = new ObjectOutputStream(socket.getOutputStream());
            send(new SetupServerMessage(CustomMessage.welcomeMessage));
            server.lobby(this);
            while(isActive()){
                read = in.readObject();
                setChanged();
                notifyObservers(read);
            }
        } catch (Exception e) {
            System.err.println("Something went wrong!");
            System.err.println(e.getMessage());
        } finally {
            close();
        }
    }

    public synchronized boolean isActive() { return active;}

    public synchronized void setNickname(String nickname) { this.nickname = nickname;}
    public synchronized String getNickname(){return nickname;}

    public synchronized void send(Object message){
        try{
            out.reset();
            out.writeObject(message);
            out.flush();
        } catch (IOException e) {
            System.err.println("Error while sending message\n");
        }
    }

    public void asyncSend(final Object message){
        new Thread(new Runnable() {
            @Override
            public void run() {
                send(message);
            }
        }).start();
    }

    public synchronized void closeConnection(){
        send(new BaseServerMessage(CustomMessage.closingConnection));
        try{
            socket.close();
        } catch (IOException e) {
            System.err.println("Error while closing connection!\n");
        }
        active = false;
    }

    private void close(){
        closeConnection();
        System.out.println("Deregistering client");
        server.deregisterConnection(this);
        System.out.println("Client deregistered successfully!");
    }

    public synchronized int parseNumberOfPlayers() {
        Object read;
        while(true){
            try {
                read = in.readObject();
                if (read instanceof BaseUserMessage){
                    if(((BaseUserMessage) read).getNumberOfPlayers() == 2 ||
                            ((BaseUserMessage) read).getNumberOfPlayers() == 3 ){
                        return ((BaseUserMessage) read).getNumberOfPlayers();
                    }
                    else send(new BaseServerMessage(CustomMessage.errorNumberOfPlayers));
                }
            } catch (IOException | ClassNotFoundException  e) {
                System.err.println(e.getMessage());
                server.deregisterConnection(this);
            }
        }
    }

    public synchronized GameMode parseGameMode(){
        Object read;
        String gameMode;
        while(true){
            try {
                read = in.readObject();
                if (read instanceof BaseUserMessage){
                    gameMode = ((BaseUserMessage) read).getGameMode().toUpperCase();
                    if(gameMode.equals("SIMPLE") || gameMode.equals("EXPERT")){
                        return GameMode.valueOf(gameMode);
                    } else send(new BaseServerMessage(CustomMessage.errorGameMode));
                }
                else send(new BaseServerMessage(CustomMessage.errorGameMode));
            } catch (IOException | ClassNotFoundException  e) {
                System.err.println("Input stream error\n");
            }
        }
    }
    /* this method will be used for all players, but for the second and third players there will also be a check
    * that will call this method again if the inserted nickname has already been chosen. */
    public synchronized String parseNickname(){
        Object read;
        String nickname;
        while(true){
            try {
                read = in.readObject();
                if (read instanceof BaseUserMessage){
                    return ((BaseUserMessage) read).getNickname();
                }
                else send(new BaseServerMessage(CustomMessage.invalidFormat));
            } catch (IOException | ClassNotFoundException  e) {
                System.err.println("Input stream error\n");
            }
        }
    }

    public void setMatchID(int matchID){
        this.matchID = matchID;
    }

    public int getMatchID(){
        return matchID;
    }

    public void startMatch() {
        matchHasStarted = true;
    }
}
