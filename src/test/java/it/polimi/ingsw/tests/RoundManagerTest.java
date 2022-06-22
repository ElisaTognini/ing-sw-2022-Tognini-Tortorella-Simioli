package it.polimi.ingsw.tests;
import it.polimi.ingsw.Controller.BaseActionController;
import it.polimi.ingsw.Model.BasicElements.AssistantCard;
import it.polimi.ingsw.Model.BoardClasses.Board;
import it.polimi.ingsw.Model.BoardClasses.RoundManager;
import it.polimi.ingsw.Model.Model;
import it.polimi.ingsw.Utils.Enums.GameMode;
import it.polimi.ingsw.Utils.Enums.PawnDiscColor;
import it.polimi.ingsw.Utils.Enums.TurnFlow;
import it.polimi.ingsw.Model.Player;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.Assert.*;

/** Class RoundManagerTest tests class RoundManager */

public class RoundManagerTest {

    RoundManager manager;
    ArrayList<Player> players = new ArrayList<>();
    Board boardTest;

    /** Method getterSetterTest changes the current state of the match and then calls for getCurrentState to see
     * if both setter and getter work properly. */
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


    /** Method pickFirstPlayerTest test calls for pickFirstPlayerIndex and prints the nickname of the player
     * extracted to screen. */
    @Test
    public void pickFirstPlayerTest(){
        players.add(new Player("player1"));
        players.add(new Player("player2"));
        players.add(new Player("player 3"));
        manager = new RoundManager(players);

        System.out.println("first player is: " + players.get(manager.pickFirstPlayerIndex()).getNickname());
    }

    /** Method startRoundTest calls for method startRound and checks that all the players have not picked a card
     * and that the current state of the turn stored in round manager is BEGINS_TURN */
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


    /** Method planningPhaseTurnTest calls for method computeTurnOrder with a randomly extracted player ID, then
     * uses refresh current player two times to print out to screen the nickname of second and third player. */
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

    /** Method storeCardsComputeNextTurn test checks if card sorting order is correct, and if clockwise turn
     * order is correctly computed. After that, it calls getCurrentPlayersCard and expects it to return null
     * because no cards have been played yet. Then, it stores an assistant card for each player and calls for
     * method ActionPhase to compute the order of the players based on the power factor of the card they played
     * and prints their nickname as well as the card they played. Finally it prints all the cards played by the
     * players in this round. */
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

        assertEquals(null, manager.getCurrentPlayersCard());

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

    /** Method checkForDupeTest simulates the situation for which two players played the same card; for the first
     * player, it calls method checkForDupe and expects it to return false, while expects true for the second player.
     * */
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


    /** Method movingStudentsTest checks if getMovedStudents and increaseMovedStudents work properly */
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


    /** Method multiRoundTest simulates the course of turn of a match between two players. */
    @Test
    public void multiRoundTest(){

        String[] names = new String[2];

        names[0] = "player1";
        names[1] = "player2";

        Model model = new Model(GameMode.SIMPLE, names, 2);
        BaseActionController controller = new BaseActionController(model);

        controller.startGame();
        controller.chooseAssistantCard(1, "player1");
        controller.chooseAssistantCard(8, "player2");

        controller.moveStudentToDR(PawnDiscColor.BLUE, "player1");
        controller.moveStudentToDR(PawnDiscColor.YELLOW, "player1");
        controller.moveStudentToDR(PawnDiscColor.YELLOW, "player1");

        controller.picksCloud("player1", 0);

        controller.moveStudentToDR(PawnDiscColor.BLUE, "player2");
        controller.moveStudentToDR(PawnDiscColor.YELLOW, "player2");
        controller.moveStudentToDR(PawnDiscColor.YELLOW, "player2");

        controller.picksCloud("player2", 1);

        controller.chooseAssistantCard(2, "player1");
        controller.chooseAssistantCard(9, "player2");

        controller.moveStudentToDR(PawnDiscColor.BLUE, "player1");
        controller.moveStudentToDR(PawnDiscColor.YELLOW, "player1");
        controller.moveStudentToDR(PawnDiscColor.YELLOW, "player1");

        controller.picksCloud("player1", 0);

        controller.moveStudentToDR(PawnDiscColor.BLUE, "player2");
        controller.moveStudentToDR(PawnDiscColor.YELLOW, "player2");
        controller.moveStudentToDR(PawnDiscColor.YELLOW, "player2");

        controller.picksCloud("player2", 1);
    }
}
