package it.polimi.ingsw.AppClasses;

import it.polimi.ingsw.Server.Server;

/** app class for Server*/
public class ServerApp {
    public static void main(String[] args) {
        Server server = new Server();
        server.run();
    }
}
