package it.polimi.ingsw.tests;
import it.polimi.ingsw.BasicElements.AssistantCard;
import it.polimi.ingsw.BoardClasses.Board;
import it.polimi.ingsw.Enums.GameMode;
import it.polimi.ingsw.Player;
import it.polimi.ingsw.TailoredExceptions.EmptyException;
import it.polimi.ingsw.TailoredExceptions.FullCloudException;
import it.polimi.ingsw.TailoredExceptions.InvalidCardActionException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.Assert.*;

public class BoardTest {

    ArrayList<Player> players = new ArrayList<>();
    Board boardToTest = new Board(players, 2, 8,
            3, 7, GameMode.SIMPLE);

    public void setup() {
        players.add(new Player("player1"));
        players.add(new Player("player2"));
    }

    @Test
    public void playAssistantCardTest(){
        setup();
        boardToTest.setup();
        AssistantCard card = new AssistantCard(1, 1, players.get(0));
        card = boardToTest.playAssistantCard(1, "player1");
        assertEquals(1, card.getAssistantCardID());
        assertEquals(1, card.getMotherNatureMovements());
    }

}
