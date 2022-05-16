package it.polimi.ingsw.Server;
import it.polimi.ingsw.Client.ViewUpdateMessage;
import it.polimi.ingsw.Controller.Controller;

import java.util.*;

public class VirtualView extends Observable implements Observer{
    private ClientConnection clientConnection;
    private String nickname;

    public VirtualView (ClientConnection clientConnection){
        this.clientConnection = clientConnection;
        this.nickname = clientConnection.getNickname();
        clientConnection.addObserver(this);
    }

    public synchronized void sendMessage(Object message){
        clientConnection.asyncSend(message);
    }

    public ClientConnection getClientConnection(){ return clientConnection; }

    public String getNickname(){return nickname;}


    /* based on the object received here (which is the message received by the
    * clientConnection) the virtualView will notify the controller and have it
    * call the necessary method. */
    @Override
    public void update(Observable o, Object arg) {

    }
}
