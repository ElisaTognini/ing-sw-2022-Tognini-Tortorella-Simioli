package it.polimi.ingsw.tests;

import it.polimi.ingsw.Client.Client;
import it.polimi.ingsw.View.CLI.CLI;

public class ClientAppTest {

    public static void main(String[] args) {
        Client client = new Client("172.20.10.6", 12345);
        CLI cli = new CLI();
        cli.addObserver(client.getNetworkHandler());
        client.addObserver(cli);
        cli.getParser().addObserver(client.getNetworkHandler());
        client.run();

    }
}
