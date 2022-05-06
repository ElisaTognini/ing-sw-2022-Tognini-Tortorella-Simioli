package it.polimi.ingsw.Client;

import it.polimi.ingsw.Server.BaseUserMessage;
import it.polimi.ingsw.Server.UserMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {
    private Parser parser;
    private String ip;
    private int port;
    private boolean active = true;

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
            ObjectInputStream socketIn = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream socketOut = new ObjectOutputStream(socket.getOutputStream());

            try {
                Thread t0 = asyncReadFromSocket(socketIn);
                Thread t1 = asyncWriteToSocket(socketOut);
                t0.join();
                t1.join();
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
    /* invece di fargli ritornare un thread, viene chiamata con una notify() in modo tale che un thread
    * nasca e muoia in questo metodo (simile alla syncSend in ClientConnection) per poter avere un mex
    * come parametro */

    public Thread asyncWriteToSocket(ObjectOutputStream socketOut) {
        UserMessage input;
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    while(isActive()){
                        //input =
                        //socketOut.writeObject(input);
                        socketOut.flush();
                        socketOut.reset();
                    }
                } catch (Exception e){
                    setActive(false);
                }
            }
        });
        t.start();
        return t;
    }

    public Thread asyncReadFromSocket(ObjectInputStream socketIn) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    while (isActive()){
                        Object input = socketIn.readObject();
                        if(input instanceof BaseUserMessage){
                            /* notify() to the view, so the view will update all changes and show them to the user*/
                        } else if (input instanceof ViewUpdateMessage){
                            /* notify() to the view, containing a different type of message from the one above*/
                        } else {
                            throw new IllegalArgumentException();
                        }
                    }
                } catch (Exception e){
                    setActive(false);
                }
            }
        });
        t.start();
        return t;
    }
}
