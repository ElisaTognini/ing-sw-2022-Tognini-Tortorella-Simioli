package it.polimi.ingsw.Client;

import it.polimi.ingsw.Utils.NetMessages.UserMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Observable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client extends Observable {
    private NetworkHandler networkHandler;
    private String ip;
    private int port;
    private boolean active = true;
    private ExecutorService executor = Executors.newFixedThreadPool(128);
    private ObjectOutputStream socketOut;

    public Client(String ip, int port){
        this.ip = ip;
        this.port = port;
        networkHandler = new NetworkHandler(this);
    }

    public synchronized boolean isActive(){return active;}

    public synchronized void setActive(boolean active) {
        this.active = active;
    }

    public void run(){
        try{
            Socket socket = new Socket(ip, port);
            System.out.println("Connection to server established.");
            this.socketOut = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream socketIn = new ObjectInputStream(socket.getInputStream());

            try {
                Thread t0 = asyncReadFromSocket(socketIn);
                t0.start();
                t0.join();
            } catch (InterruptedException e){
                System.err.println("Thread issue.");
            }finally {
                socketIn.close();
                socketOut.close();
                socket.close();
            }

        } catch (IOException e){
            System.err.println("Something went wrong while creating socket");
        }
    }
    /* metodo chiamato da un'altra classe, presumibilmente il Parser,
     dopo che sono stati fatti i controlli per la validità dell'azione dell'utente */

    public synchronized void asyncWriteToSocket(Object input) {
        executor.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    socketOut.writeUnshared(input);
                    socketOut.flush();
                    socketOut.reset();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }

            }
        });
    }

    public Thread asyncReadFromSocket(ObjectInputStream socketIn) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while(active) {
                        Object input = socketIn.readObject();
                        setChanged();
                        notifyObservers(input);
                    }
                } catch (IOException | ClassNotFoundException e) {
                    setActive(false);
                }
            }
        });
        return t;
    }

    public NetworkHandler getNetworkHandler() {
        return networkHandler;
    }
}
