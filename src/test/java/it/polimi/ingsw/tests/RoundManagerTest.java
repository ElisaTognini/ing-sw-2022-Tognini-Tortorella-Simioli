package it.polimi.ingsw.tests;
import it.polimi.ingsw.BasicElements.AssistantCard;
import it.polimi.ingsw.BoardClasses.Board;
import it.polimi.ingsw.BoardClasses.RoundManager;
import it.polimi.ingsw.Enums.GameMode;
import it.polimi.ingsw.Enums.PawnDiscColor;
import it.polimi.ingsw.Enums.TurnFlow;
import it.polimi.ingsw.Player;
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
        players.add(new Player("player 3"));
        manager = new RoundManager(players);
        boardTest = new Board(players, 3, 6, 4, 9, GameMode.SIMPLE);
        boardTest.setup();

        manager.startRound();
        manager.computeTurnOrder(manager.pickFirstPlayerIndex());
        System.out.println("the first player of the game is: " + manager.getCurrentPlayer().getNickname());

        manager.storeCards(boardTest.playAssistantCard(4, manager.getCurrentPlayer().getNickname()));
        manager.refreshCurrentPlayer();
        manager.storeCards(boardTest.playAssistantCard(3, manager.getCurrentPlayer().getNickname()));
        manager.refreshCurrentPlayer();
        manager.storeCards(boardTest.playAssistantCard(2, manager.getCurrentPlayer().getNickname()));

        manager.sortActionPhase();
        System.out.println("the first player is " + manager.getCurrentPlayer().getNickname());

        manager.refreshCurrentPlayer();
        System.out.println("second player is " + manager.getCurrentPlayer().getNickname());

        manager.refreshCurrentPlayer();
        System.out.println("third player is " + manager.getCurrentPlayer().getNickname());

        for(AssistantCard c : manager.getCards()){
            System.out.println("card " + c.getAssistantCardID() + " of player " + c.getOwner().getNickname());
        }
    }

    /*@Test
    public void checkForDupeTest(){
        players.add(new Player("player1"));
        players.add(new Player("player2"));
        players.add(new Player("player3"));
        manager = new RoundManager(players);
        boardTest = new Board(players, 3, 6, 4, 9, GameMode.SIMPLE);
        boardTest.setup();
        manager.computeTurnOrder(manager.pickFirstPlayerIndex());

        manager.storeCards(boardTest.playAssistantCard(4, manager.getCurrentPlayer().getNickname()));
        manager.refreshCurrentPlayer();



    }*/

}
