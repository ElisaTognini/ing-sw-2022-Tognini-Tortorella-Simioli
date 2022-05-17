package it.polimi.ingsw.tests;
import it.polimi.ingsw.Model.BasicElements.AssistantCard;
import it.polimi.ingsw.Model.BoardClasses.Board;
import it.polimi.ingsw.Model.BoardClasses.RoundManager;
import it.polimi.ingsw.Utils.Enums.GameMode;
import it.polimi.ingsw.Utils.Enums.TurnFlow;
import it.polimi.ingsw.Model.Player;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.Assert.*;


public class RoundManagerTest {

    RoundManager manager;
    ArrayList<Player> players = new ArrayList<>();
    Board boardTest;

    @Test
    public void getterSetterTest(){
        players.add(new Player("player1"));
        players.add(new Player("player2"));
        manager = new RoundManager(players);

        manager.changeState(TurnFlow.BEGINS_TURN);
        assertEquals(TurnFlow.BEGINS_TURN, manager.getCurrentState());

        manager.changeState(TurnFlow.CARD_PICKED);
        assertEquals(TurnFlow.CARD_PICKED, manager.getCurrentState());

    }

    @Test
    public void pickFirstPlayerTest(){
        players.add(new Player("player1"));
        players.add(new Player("player2"));
        players.add(new Player("player 3"));
        manager = new RoundManager(players);

        System.out.println("first player is: " + players.get(manager.pickFirstPlayerIndex()).getNickname());
    }

    @Test
    public void startRoundTest(){
        players.add(new Player("player1"));
        players.add(new Player("player2"));
        players.add(new Player("player 3"));
        manager = new RoundManager(players);

        manager.startRound();
        for(Player p: players){
            assertEquals(false, p.getCardPicked());
        }

        assertEquals(TurnFlow.BEGINS_TURN, manager.getCurrentState());
    }

    @Test
    public void planningPhaseTurnTest(){
        players.add(new Player("player1"));
        players.add(new Player("player2"));
        players.add(new Player("player 3"));
        manager = new RoundManager(players);

        manager.startRound();
        manager.computeTurnOrder(manager.pickFirstPlayerIndex());

        System.out.println("the first player is " + manager.getCurrentPlayer().getNickname());

        manager.refreshCurrentPlayer();
        System.out.println("second player is " + manager.getCurrentPlayer().getNickname());

        manager.refreshCurrentPlayer();
        System.out.println("third player is " + manager.getCurrentPlayer().getNickname());
    }

    /* checks if card sorting order is correct, and if clockwise turn
    * order is correctly computed*/
    @Test
    public void storeCardsComputeNextTurnTest(){
        players.add(new Player("player1"));
        players.add(new Player("player2"));
        players.add(new Player("player3"));
        manager = new RoundManager(players);
        boardTest = new Board(players, 3, 6, 4, 9, GameMode.SIMPLE);
        boardTest.setup();

        manager.startRound();
        manager.computeTurnOrder(manager.pickFirstPlayerIndex());

        System.out.println("the first player of the game is: " + manager.getCurrentPlayer().getNickname());

        assertEquals(null, manager.getCurrentPlayersCard()); // no card has been played yet

        manager.storeCards(boardTest.playAssistantCard(4, manager.getCurrentPlayer().getNickname()));
        manager.refreshCurrentPlayer();
        manager.storeCards(boardTest.playAssistantCard(3, manager.getCurrentPlayer().getNickname()));
        manager.refreshCurrentPlayer();
        manager.storeCards(boardTest.playAssistantCard(2, manager.getCurrentPlayer().getNickname()));

        manager.sortActionPhase();
        System.out.println("the first player is " + manager.getCurrentPlayer().getNickname());
        System.out.println("playing card " + manager.getCurrentPlayersCard().getAssistantCardID());

        manager.refreshCurrentPlayerAction();
        System.out.println("second player is " + manager.getCurrentPlayer().getNickname());
        System.out.println("playing card " + manager.getCurrentPlayersCard().getAssistantCardID());

        manager.refreshCurrentPlayerAction();
        System.out.println("third player is " + manager.getCurrentPlayer().getNickname());
        System.out.println("playing card " + manager.getCurrentPlayersCard().getAssistantCardID());

        for(AssistantCard c : manager.getCards()){
            System.out.println("card " + c.getAssistantCardID() + " of player " + c.getOwner().getNickname());
        }
    }

    @Test
    public void checkForDupeTest(){
        players.add(new Player("player1"));
        players.add(new Player("player2"));
        players.add(new Player("player3"));
        manager = new RoundManager(players);
        boardTest = new Board(players, 3, 6, 4, 9, GameMode.SIMPLE);
        boardTest.setup();
        manager.computeTurnOrder(manager.pickFirstPlayerIndex());

        boardTest.playAssistantCard(4, manager.getCurrentPlayer().getNickname());
        assertFalse(manager.checkForDupe(4));
        manager.storeCards(boardTest.playAssistantCard(4, manager.getCurrentPlayer().getNickname()));

        manager.refreshCurrentPlayer();

        boardTest.playAssistantCard(4, manager.getCurrentPlayer().getNickname());
        assertTrue(manager.checkForDupe(4));

        boardTest.playAssistantCard(3, manager.getCurrentPlayer().getNickname());
        assertFalse(manager.checkForDupe(3));
        manager.storeCards(boardTest.playAssistantCard(3, manager.getCurrentPlayer().getNickname()));

        manager.refreshCurrentPlayer();

        boardTest.playAssistantCard(4, manager.getCurrentPlayer().getNickname());
        assertTrue(manager.checkForDupe(4));

        boardTest.playAssistantCard(3, manager.getCurrentPlayer().getNickname());
        assertTrue(manager.checkForDupe(3));

        boardTest.playAssistantCard(7, manager.getCurrentPlayer().getNickname());
        assertFalse(manager.checkForDupe(7));
        manager.storeCards(boardTest.playAssistantCard(7, manager.getCurrentPlayer().getNickname()));
    }

    @Test
    public void movingStudentsTest(){
        players.add(new Player("player1"));
        manager = new RoundManager(players);
        boardTest = new Board(players, 3, 6, 4, 9, GameMode.SIMPLE);
        boardTest.setup();

        for(manager.getMovedStudents(); manager.getMovedStudents() < 3; manager.increaseMovedStudents()){
            assertFalse(manager.threeStudentsMoved());
        }
        assertTrue(manager.threeStudentsMoved());
    }

}
