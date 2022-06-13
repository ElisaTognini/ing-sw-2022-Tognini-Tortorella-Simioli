package it.polimi.ingsw.Client;

import it.polimi.ingsw.Utils.Enums.TurnFlow;

import java.util.Observable;
import java.util.Observer;

/**
 * Class NetworkHandler receives an update each time the player interacts with the view.
* When the update is received, simple checks are performed client side and then the message is made so that it
* is the correct format to be read server side.
 *
 * @see java.util.Observer
 * */

public class NetworkHandler implements Observer {

    private Client client;

    /**
     * Constructor NetworkHandler creates a new NetworkHandler instance.
     *
     * @param client - of type Client - client reference.
     * */

    public NetworkHandler(Client client) {
        this.client = client;
    }

    /**
     * When a player interacts with their view, a message is sent via socket to the corresponding client,
     * after an update of the internal status of the game.
     *
     * @see Observer#update(Observable, Object)
     * */

    @Override
    public synchronized void update(Observable o, Object arg) {
        client.asyncWriteToSocket(arg);
    }
}
