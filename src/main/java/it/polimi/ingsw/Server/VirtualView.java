package it.polimi.ingsw.Server;
import java.util.*;

public class VirtualView extends Observable implements Observer {
    private ClientConnection clientConnection;
    private String nickname;

    public class MessageReceiver implements Observer{

        @Override
        public void update(Observable o, Object arg) {

        }
    }

    public VirtualView (ClientConnection clientConnection){
        this.clientConnection = clientConnection;
    }

    @Override
    public void update(Observable o, Object arg) {

    }


    public ClientConnection getClientConnection(){ return clientConnection; }

}
