package it.polimi.ingsw.View.CLI;

import it.polimi.ingsw.Client.Client;

import java.util.Scanner;

public class MainCLI {

    public static void main(String[] args) {
        String ip;
        Scanner scanner = new Scanner(System.in);

        System.out.println("insert the server IP address: ");
        ip = scanner.nextLine();

        Client client = new Client(ip, 12345);
        CLI cli = new CLI();
        cli.addObserver(client.getNetworkHandler());
        client.addObserver(cli);
        cli.getParser().addObserver(client.getNetworkHandler());
        client.run();
    }

}
