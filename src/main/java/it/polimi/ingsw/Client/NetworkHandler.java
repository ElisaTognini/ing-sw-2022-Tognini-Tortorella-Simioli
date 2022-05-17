package it.polimi.ingsw.Client;

import it.polimi.ingsw.Utils.Enums.TurnFlow;

import java.util.Observable;
import java.util.Observer;

/* this class receives an update each time the player interacts with the view.
* When the update is received, simple checks are performed client side and then the message is made so that it
* is the correct format to be read server side. */

public class NetworkHandler implements Observer {

    private Client client;

    public NetworkHandler(Client client) {
        this.client = client;
    }

    @Override
    public synchronized void update(Observable o, Object arg) {
        client.asyncWriteToSocket(arg);
    }
}
