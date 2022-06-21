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

    private Socket socket;
    private Server server;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private boolean active;
    private String nickname;
    private int matchID;
    private boolean matchHasStarted = false;

    /** constructor for ClientConnection class, saves references to both the
     * client socket and the server that accepted it, and sets connection to active
     * @param server of type Server
     * @param socket of type Socket
     * */
    public ClientConnection(Socket socket, Server server){
        this.socket = socket;
        this.server = server;
        active =  true;
    }

    /** ClientConnection class extends Runnable, this method implements the method run
     * in an override.
     * In this method, the input and output streams are retrieved from the socket and saved as
     * attributes. After that, a welcome message is sent via socket and the server lobby method is called so
     * that the connection can be properly handled. A while loop stays listening for messages
     * to the socket, and when a message is received, the instance of VirtualView observing @this
     * is notified.
     * @see VirtualView
     * @see Observable
     * @see Runnable*/
    @Override
    public void run() {
        Object read;
        System.out.println("clientconnection run method has been called");
        try {
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
            send(new SetupServerMessage(CustomMessage.welcomeMessage));
            out.flush();
            server.lobby(this);
            while(isActive()){
                if (matchHasStarted) {
                    read = in.readObject();
                    setChanged();
                    notifyObservers(read);
                }
            }
        } catch (Exception e) {
            System.err.println("Something went wrong!");
            System.err.println(e.getMessage());
        } finally {
            close();
        }
    }

    /** @return active of type boolean - true if connection is active, false otherwise*/
    public synchronized boolean isActive() { return active;}

    /** @param nickname of type String represents the user's nickname in the game
     * setter method for nickname*/
    public synchronized void setNickname(String nickname) { this.nickname = nickname;}

    /** @return nickname of type string*/
    public synchronized String getNickname(){return nickname;}

    /** @param message of type Object sends a generic message object via the socket*/
    public synchronized void send(Object message){
        try{
            out.reset();
            out.writeObject(message);
            out.flush();
        } catch (IOException e) {
            System.err.println("Error while sending message\n");
            System.out.println(e.getMessage());
        }
    }

    /** @param message is a final object that is sent via socket. This method
     * makes the sending asynchronous by using a thread and calling the send
     * method in an anonymous runnable*/
    public void asyncSend(final Object message){
        new Thread(() -> send(message)).start();
    }

    /** notifies the user that connection is being closed and closes socket,
     * then setting the connection as inactive by setting active to false*/
    public synchronized void closeConnection(){
        send(new BaseServerMessage(CustomMessage.closingConnection));
        try{
            socket.close();
        } catch (IOException e) {
            System.err.println("Error while closing connection!\n");
        }
        active = false;
    }

    /** this method passes a reference to this instance to the server,
     * makimg it deregister this connection*/
    private void close(){
        closeConnection();
        System.out.println("Deregistering client");
        server.deregisterConnection(this);
        System.out.println("Client deregistered successfully!");
    }

    /** this method requests the number of players for the next match to the user
     * if the user is the first player to connect in said match*/
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
    /** this method requests the game mode (simple or expert) for the next match to the user
     * if the user is the first player to connect in said match*/
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

    /** this method requests and parses username for this player */
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

    /** this method associates the player connection to the match the player
     * is participating in
     * @param matchID of type int identifies the match*/
    public void setMatchID(int matchID){
        this.matchID = matchID;
    }

    /** this method returns the match the user on this connection is partaking in
     * @return matchID of type int*/
    public int getMatchID(){
        return matchID;
    }

    /** this method is called once the match starts so that the socket can
     * effectively start listening for messages*/
    public void startMatch() {
        matchHasStarted = true;
    }
}
