package it.polimi.ingsw.Client;

import it.polimi.ingsw.Utils.NetMessages.UserMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Observable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Class client represents a client.
 *
 * @see java.util.Observable
 * */

public class Client extends Observable {
    private NetworkHandler networkHandler;
    private String ip;
    private int port;
    private boolean active = true;
    private ExecutorService executor = Executors.newFixedThreadPool(128);
    private ObjectOutputStream socketOut;

    /**
     * Constructor Client creates a new Client instance.
     *
     * @param ip - of type String - address of the server.
     * @param port - of type int - port of the server.
     * */

    public Client(String ip, int port){
        this.ip = ip;
        this.port = port;
        networkHandler = new NetworkHandler(this);
    }

    /**
     * Method setActive sets this Client as active, based on the boolean parameter that is passed.
     *
     * @param active - of type boolean - indicates the status of the client (true if active).
     * */

    public synchronized void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Method run creates the client-side socket in order to connect this client to the server.
     * */

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
            } catch (Exception e){
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

    /**
     * Method asyncWriteToSocket sends an Object to the server.
     *
     * @param input - of type Object - references what is being sent.
     * */

    public synchronized void asyncWriteToSocket(Object input) {
        executor.submit(() -> {
            try {
                socketOut.writeUnshared(input);
                socketOut.flush();
                socketOut.reset();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }

        });
    }

    /**
     * Method asyncReadFromSocket gets input from the server.
     *
     * @param socketIn - of type ObjectInputStream - references the input received.
     *
     * @return the Thread t that reads the object.
     * */

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

    /**
     * Getter method getNetworkHandler returns the NetworkHandler that is handling the connection of this client to the server.
     *
     * @return the networkHandler (of type NetworkHandler) of this Client object.
     * */

    public NetworkHandler getNetworkHandler() {
        return networkHandler;
    }
}
