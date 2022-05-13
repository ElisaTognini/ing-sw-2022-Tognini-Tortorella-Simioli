package it.polimi.ingsw.Client;

import it.polimi.ingsw.Server.BaseServerMessage;
import it.polimi.ingsw.Server.BaseUserMessage;
import it.polimi.ingsw.Server.UserMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client {
    private Parser parser;
    private String ip;
    private int port;
    private String nickname;
    private boolean active = true;
    private ExecutorService executor = Executors.newFixedThreadPool(128);

    public Client(String ip, int port){
        this.ip = ip;
        this.port = port;
    }

    public synchronized boolean isActive(){return active;}

    public synchronized void setActive(boolean active) {
        this.active = active;
    }

    public void run(){
        try{
            Socket socket = new Socket(ip, port);
            System.out.println("Connection to server established.");
            ObjectOutputStream socketOut = new ObjectOutputStream(socket.getOutputStream());
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

    public synchronized void asyncWriteToSocket(ObjectOutputStream socketOut, UserMessage input) {
        executor.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    socketOut.writeObject(input);
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
                        System.out.println(input.toString());
                        if (input instanceof BaseServerMessage) {
                            /* notify() to the view, so the view will update all changes and show them to the user*/
                            if (nickname == null) {
                                if (((BaseUserMessage) input).getNickname() != null) {
                                    nickname = ((BaseUserMessage) input).getNickname();
                                    parser = new Parser(nickname);
                                }
                            }
                        } else if (input instanceof ViewUpdateMessage) {
                            /* notify() to the view, containing a different type of message from the one above*/
                        } else {
                            throw new IllegalArgumentException();
                        }
                    }
                } catch (Exception e) {
                    //setActive(false);
                }
            }
        });
        //t.start();
        return t;
    }
}
