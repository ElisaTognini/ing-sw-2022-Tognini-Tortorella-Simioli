package it.polimi.ingsw.Server;
import it.polimi.ingsw.Client.ViewUpdateMessage;
import it.polimi.ingsw.Controller.Controller;

import java.util.*;

/* virtualView class is observed by the Controller and is observes the model.
* This makes it so that:
* - each time something changes on the virtualView, the controller is notified
* - each time the model updates itself, the view is notified. */

public class VirtualView extends Observable implements Observer {
    private ClientConnection clientConnection;
    private String nickname;

    public VirtualView (ClientConnection clientConnection){
        this.clientConnection = clientConnection;
        this.nickname = clientConnection.getNickname();
        clientConnection.addObserver(new MessageReceiver(this));
    }

    public void addControllerAsObserver(Controller controller){
        addObserver(controller);
    }

    /* after receiving a notifying call by the model, this method
    * sends the viewUpdate to the client, so that the view on the client side can display it */
    @Override
    public void update(Observable o, Object arg) {
        if(! (arg instanceof ViewUpdateMessage)){
            //sends error message
        }
        assert arg instanceof ViewUpdateMessage;
        clientConnection.asyncSend((ViewUpdateMessage) arg);
    }

    public ClientConnection getClientConnection(){ return clientConnection; }

}
