package it.polimi.ingsw.Server;

import it.polimi.ingsw.Enums.GameMode;
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
    private boolean active;
    private String nickname;

    public ClientConnection(Socket socket, Server server){
        this.socket = socket;
        this.server = server;
        active =  true;
    }

    @Override
    public void run() {
        ObjectInputStream in;
        Object read;
        try {
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
            send(new BaseServerMessage(CustomMessage.welcomeMessage));
            server.lobby(this);
            while(isActive()){
                read = in.readObject();
                notifyObservers(read);
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Something went wrong!");
            e.printStackTrace();
        } finally {
            close();
        }
    }

    public synchronized boolean isActive() { return active;}

    public synchronized void setNickname(String nickname) { this.nickname = nickname;}
    public synchronized String getNickname(){return nickname;}

    public synchronized void send(ServerMessage message){
        try{
            out.reset();
            out.writeObject(message);
            out.flush();
        } catch (IOException e) {
            System.err.println("Error while sending message\n");
        }
    }

    public void asyncSend(final ServerMessage message){
        new Thread(new Runnable() {
            @Override
            public void run() {
                send(message);
            }
        }).start();
    }

    /*CLOSECONNECTION AND CLOSE TO BE FINISHED*/
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
        //server.deregisterConnection(this);
        System.out.println("Client deregistered successfully!");
    }

    public Socket getSocket(){
        return socket;
    }

    public synchronized int parseNumberOfPlayers() {
        Object read;
        ObjectInputStream in;
        while(true){
            try {
                in = new ObjectInputStream(socket.getInputStream());
                read = in.readObject();
                if (read instanceof BaseUserMessage){
                    if(((BaseUserMessage) read).getNumberOfPlayers() == 2 ||
                            ((BaseUserMessage) read).getNumberOfPlayers() == 3 ){
                        return ((BaseUserMessage) read).getNumberOfPlayers();
                    }
                    else send(new BaseServerMessage(CustomMessage.errorNumberOfPlayers));
                }
            } catch (IOException | ClassNotFoundException  e) {
                System.err.println("Input stream error\n");
            }
        }
    }

    public synchronized GameMode parseGameMode(){
        Object read;
        ObjectInputStream in;
        String gameMode;
        while(true){
            try {
                in = new ObjectInputStream(socket.getInputStream());
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
        ObjectInputStream in;
        String nickname;
        while(true){
            try {
                in = new ObjectInputStream(socket.getInputStream());
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
}
