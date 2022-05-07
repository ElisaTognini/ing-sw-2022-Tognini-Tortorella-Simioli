package it.polimi.ingsw.Server;
import java.util.*;

public class VirtualView extends Observable implements Observer {
    private ClientConnection clientConnection;
    private String nickname;

    public class MessageReceiver implements Observer{

        @Override
        public void update(Observable o, Object arg) {
            System.out.println("Message received from client");
            try{
                /* gestione azioni client da decidere */
            }catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public VirtualView (ClientConnection clientConnection){
        this.clientConnection = clientConnection;
        this.nickname = clientConnection.getNickname();
        clientConnection.addObserver(new MessageReceiver());
    }

    @Override
    public void update(Observable o, Object arg) {

    }


    public ClientConnection getClientConnection(){ return clientConnection; }

}
