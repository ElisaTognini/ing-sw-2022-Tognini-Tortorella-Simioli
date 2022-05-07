package it.polimi.ingsw.tests;

import it.polimi.ingsw.Client.Client;
import it.polimi.ingsw.Server.Server;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.Socket;

public class LobbyTest {

    private Server server;
    private Client client;

    @Test
    public void lobbyTestServer(){
        server = new Server();
        server.run();
    }

    @Test
    public void lobbyTestClient() throws IOException {
        client = new Client("127.0.0.1", 12345);
        client.run();
    }

}
