package it.polimi.ingsw.tests;

import it.polimi.ingsw.Utils.NetMessages.ViewUpdateMessage;
import it.polimi.ingsw.View.CLI.CLI;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

public class CLITest {
    private CLI clitest = new CLI();
    private ViewUpdateMessage message = new ViewUpdateMessage();

    @Test
    public void printBoardTest(){
        ArrayList<String> islands = message.getIslands();
        ArrayList<String> clouds = message.getClouds();
        clouds.add("0 BLUE RED YELLOW");
        clouds.add("1 PINK PINK GREEN");
        islands.add("1 1 0 5 4 2");
        islands.add("3 2 5 7 6 1 player1 1");
        islands.add("6 4 1 2 3 5 player2 2");
        clitest.buildNewBoard(message);
    }
}
