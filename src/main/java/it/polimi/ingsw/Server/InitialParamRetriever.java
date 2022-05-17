package it.polimi.ingsw.Server;

import java.io.ObjectInputStream;

/* this class needs to listen to one message, make sending available, and then the thread
* needs to go back to the pool. */

public class InitialParamRetriever implements Runnable{
    @Override
    public void run() {
        ObjectInputStream in;
        Object read;
    }
}
