package it.polimi.ingsw.Server;
import java.util.*;

public class VirtualView extends Observable implements Observer{
    private ClientConnection clientConnection;
    private String nickname;

    /** @param clientConnection allows the virtualView to have a reference through
     * which messahes can be sent
     * clientConnection adds this as an Observer*/
    public VirtualView (ClientConnection clientConnection){
        this.clientConnection = clientConnection;
        this.nickname = clientConnection.getNickname();
        clientConnection.addObserver(this);
    }

    /** forces sending of a generic message through socket via ClientConnection methods
     * @param message generic Object to be sent
     * @see ClientConnection*/
    public synchronized void sendMessage(Object message){
        clientConnection.asyncSend(message);
    }

    /** getter method for clientConnection reference
     * @return ClientConnection
     * @see ClientConnection*/
    public ClientConnection getClientConnection(){ return clientConnection; }

    /** getter method for nickname (String)
     * @return String nickname*/
    public String getNickname(){return nickname;}


    /** based on the object received here (which is the message received by the
    * clientConnection) the virtualView will notify the controller and have it
    * call the necessary method. */
    @Override
    public void update(Observable o, Object arg) {
        setChanged();
        notifyObservers(arg);
    }
}
