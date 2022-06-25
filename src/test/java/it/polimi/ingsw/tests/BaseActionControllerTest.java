package it.polimi.ingsw.tests;

import it.polimi.ingsw.Model.BasicElements.AssistantCard;
import it.polimi.ingsw.Controller.Controller;
import it.polimi.ingsw.Utils.Enums.GameMode;
import it.polimi.ingsw.Utils.Enums.PawnDiscColor;
import it.polimi.ingsw.Utils.Enums.TurnFlow;
import it.polimi.ingsw.Model.Model;
import static org.junit.Assert.*;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

/** Class BaseActionControllerTest tests class BaseActionController */

public class BaseActionControllerTest {

    Controller controller;
    Model model;

    /** Method initTest initialises new instances of model and controller to use in this class */
    @Test
    public void initTest() {
        String[] nicknames = new String[2];
        nicknames[0] = "player1";
        nicknames[1] = "player2";
        model = new Model(GameMode.SIMPLE, nicknames, 2);
        controller = new Controller(model);
    }


    /** Method startGameTest calls for startGame using this controller*/
    @Test
    public void startGameTest() {
        initTest();
        controller.getBaseActionController().startGame();
    }

    /** Method chooseAssistantCardTest checks if round manager is currently on BEGINS_TURN, then simulates the planning
     * phase when players have to pick an AssistantCard: in this test, one of the players enters an invalid card ID
     * to see if the controller handles it correctly.
     * This test is run five times in order to make sure this happens with both players, since the order for the first
     * planning is extracted randomly. */
    @RepeatedTest(5)
    public void chooseAssistantCardTest() {
        initTest();
        controller.getBaseActionController().startGame();
        if (model.getRoundManager().getCurrentPlayer().getNickname().equals("player1")) {

            assertTrue(TurnFlow.BEGINS_TURN.equals(model.getRoundManager().getCurrentState()));
            assertTrue(controller.getBaseActionController().chooseAssistantCard(1, "player1"));
            assertFalse(controller.getBaseActionController().chooseAssistantCard(12, "player2"));
            assertTrue(controller.getBaseActionController().chooseAssistantCard(2, "player2"));
            assertTrue(TurnFlow.CARD_PICKED.equals(model.getRoundManager().getCurrentState()));
        } else {
            assertTrue(TurnFlow.BEGINS_TURN.equals(model.getRoundManager().getCurrentState()));
            assertFalse(controller.getBaseActionController().chooseAssistantCard(12, "player2"));
            assertTrue(controller.getBaseActionController().chooseAssistantCard(2, "player2"));
            assertTrue(controller.getBaseActionController().chooseAssistantCard(1, "player1"));
            assertTrue(TurnFlow.CARD_PICKED.equals(model.getRoundManager().getCurrentState()));
        }
    }


    /** Method moveStudentToDRTest checks if the controller handles the action phase correctly: firstly, it stores
     * two cards for the two players, then checks if current state in round manager is set to CARD_PICKED and, if the
     * player has the selected color in their entrance, it calls for moveStudentToDR to move students from the entrance
     * to the dining room. */
    @RepeatedTest(5)
    public void moveStudentToDRTest() {
        AssistantCard card1;
        AssistantCard card2;

        initTest();
        controller.getBaseActionController().startGame();

        if (model.getRoundManager().getCurrentState().equals(TurnFlow.BEGINS_TURN)) {
            if (model.getRoundManager().getCurrentPlayer().getNickname().equals("player1")) {
                card1 = model.getBoard().playAssistantCard(1, "player1");
                card2 = model.getBoard().playAssistantCard(2, "player2");
            } else {
                card1 = model.getBoard().playAssistantCard(2, "player2");
                card2 = model.getBoard().playAssistantCard(1, "player1");
            }
            model.getRoundManager().storeCards(card1);
            model.getRoundManager().storeCards(card2);
        }
        if (model.getRoundManager().getCurrentState().equals(TurnFlow.CARD_PICKED)) {
            if (model.getRoundManager().getCurrentPlayer().getNickname().equals("player1")) {
                if (model.getBoard().getPlayerSchoolBoard("player1").getEntrance().isColorAvailable(PawnDiscColor.PINK))
                    assertTrue(controller.getBaseActionController().moveStudentToDR(PawnDiscColor.PINK, "player1"));
                else assertFalse(controller.getBaseActionController().moveStudentToDR(PawnDiscColor.PINK, "player1"));
            } else {
                if (model.getBoard().getPlayerSchoolBoard("player2").getEntrance().isColorAvailable(PawnDiscColor.BLUE))
                    assertTrue(controller.getBaseActionController().moveStudentToDR(PawnDiscColor.BLUE, "player2"));
                else assertFalse(controller.getBaseActionController().moveStudentToDR(PawnDiscColor.BLUE, "player2"));
            }
        }
    }



    /** Method moveStudentToIslandTest checks if the controller handles the action phase correctly: firstly, it stores
     * two cards for the two players, then checks if current state in round manager is set to CARD_PICKED and, if the
     * player has the selected color in their entrance, it calls for moveStudentToIsland to move students from the
     * entrace to the dining room.
     * Variable threeStudentsMoved is increased manually for the purpose of this test only.
     * */
    @RepeatedTest(5)
    public void moveStudentToIslandTest(){
        AssistantCard card1;
        AssistantCard card2;

        initTest();
        controller.getBaseActionController().startGame();

        if (model.getRoundManager().getCurrentState().equals(TurnFlow.BEGINS_TURN)) {
            if (model.getRoundManager().getCurrentPlayer().getNickname().equals("player1")) {
                card1 = model.getBoard().playAssistantCard(1, "player1");
                card2 = model.getBoard().playAssistantCard(2, "player2");
            } else {
                card1 = model.getBoard().playAssistantCard(2, "player2");
                card2 = model.getBoard().playAssistantCard(1, "player1");
            }
            model.getRoundManager().storeCards(card1);
            model.getRoundManager().storeCards(card2);
        }


        model.getRoundManager().increaseMovedStudents();
        model.getRoundManager().increaseMovedStudents();

        if (model.getRoundManager().getCurrentState().equals(TurnFlow.CARD_PICKED)) {
            if (model.getRoundManager().getCurrentPlayer().getNickname().equals("player1")) {
                if (model.getBoard().getPlayerSchoolBoard("player1").getEntrance().isColorAvailable(PawnDiscColor.PINK)) {
                    assertTrue(controller.getBaseActionController().moveStudentToIsland(PawnDiscColor.PINK, "player1", model.getBoard().getMotherNaturePosition()));
                }
                else assertFalse(controller.getBaseActionController().moveStudentToIsland(PawnDiscColor.PINK, "player1", model.getBoard().getMotherNaturePosition()));
            } else {
                if (model.getBoard().getPlayerSchoolBoard("player2").getEntrance().isColorAvailable(PawnDiscColor.BLUE)) {
                    assertTrue(controller.getBaseActionController().moveStudentToIsland(PawnDiscColor.BLUE, "player2", model.getBoard().getMotherNaturePosition()));
                }
                else assertFalse(controller.getBaseActionController().moveStudentToIsland(PawnDiscColor.BLUE, "player2", model.getBoard().getMotherNaturePosition()));
            }
        }
    }


    /** Method picksCloudTest checks if the controller handles correctly the choosing of a cloud tile,
     * handling the situation where a cloud has already been picked.
     * Turn flow manually set to MOVED_STUDENTS from the start just for the purpose of this test. */
    @RepeatedTest(5)
    public void picksCloudTest() {

        AssistantCard card1;
        AssistantCard card2;

        initTest();
        controller.getBaseActionController().startGame();

        if (model.getRoundManager().getCurrentState().equals(TurnFlow.BEGINS_TURN)) {
            if (model.getRoundManager().getCurrentPlayer().getNickname().equals("player1")) {
                card1 = model.getBoard().playAssistantCard(1, "player1");
                card2 = model.getBoard().playAssistantCard(2, "player2");
            } else {
                card1 = model.getBoard().playAssistantCard(2, "player2");
                card2 = model.getBoard().playAssistantCard(1, "player1");
            }
            model.getRoundManager().storeCards(card1);
            model.getRoundManager().storeCards(card2);
        }

        model.getRoundManager().changeState(TurnFlow.MOVED_STUDENTS);

        if (model.getRoundManager().getCurrentPlayer().getNickname().equals("player1")) {
            assertTrue(controller.getBaseActionController().picksCloud("player1", 1));
            model.getRoundManager().changeState(TurnFlow.MOVED_STUDENTS);
            assertFalse(controller.getBaseActionController().picksCloud("player2", 1));
            assertTrue(controller.getBaseActionController().picksCloud("player2", 0));
        } else {
            assertTrue(controller.getBaseActionController().picksCloud("player2", 1));
            model.getRoundManager().changeState(TurnFlow.MOVED_STUDENTS);
            assertFalse(controller.getBaseActionController().picksCloud("player1", 1));
            assertTrue(controller.getBaseActionController().picksCloud("player1", 0));
        }

    }
}
