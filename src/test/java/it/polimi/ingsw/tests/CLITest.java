package it.polimi.ingsw.tests;

import it.polimi.ingsw.View.CLI;
import org.junit.jupiter.api.Test;

public class CLITest {
    private CLI clitest = new CLI();

    @Test
    public void printBoardTest(){
        clitest.buildNewBoard();
    }
}
