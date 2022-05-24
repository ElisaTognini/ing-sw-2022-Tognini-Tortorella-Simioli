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

public class BaseActionControllerTest {

    Controller controller;
    Model model;

    @Test
    public void initTest() {
        String[] nicknames = new String[2];
        nicknames[0] = "player1";
        nicknames[1] = "player2";
        model = new Model(GameMode.SIMPLE, nicknames, 2);
        controller = new Controller(model);
    }

    @Test
    public void startGameTest() {
        initTest();
        controller.getBaseActionController().startGame();
    }

    @RepeatedTest(5)
    public void chooseAssistantCardTest() {
        initTest();
        controller.getBaseActionController().startGame();
        if (model.getRoundManager().getCurrentPlayer().getNickname().equals("player1")) {
            //System.out.println("if");
            //System.out.println(model.getRoundManager().getCurrentState());
            assertTrue(controller.getBaseActionController().chooseAssistantCard(1, "player1"));
            assertFalse(controller.getBaseActionController().chooseAssistantCard(12, "player2"));
            assertTrue(controller.getBaseActionController().chooseAssistantCard(2, "player2"));
            //System.out.println(model.getRoundManager().getCurrentState());
        } else {
            //System.out.println("else");
            //System.out.println(model.getRoundManager().getCurrentState());
            assertFalse(controller.getBaseActionController().chooseAssistantCard(12, "player2"));
            assertTrue(controller.getBaseActionController().chooseAssistantCard(2, "player2"));
            assertTrue(controller.getBaseActionController().chooseAssistantCard(1, "player1"));
            //System.out.println(model.getRoundManager().getCurrentState());
        }
    }

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

        /* Increased manually for the purpose of this test only */
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

    /*@RepeatedTest(5)
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

        /* Turn flow manually set to MOVED_STUDENTS from the start just for the purpose of this test
        model.getRoundManager().changeState(TurnFlow.MOVED_STUDENTS);

        if (model.getRoundManager().getCurrentPlayer().getNickname().equals("player1")) {
            assertTrue(controller.getBaseActionController().picksCloud("player1", 1));
            assertFalse(controller.getBaseActionController().picksCloud("player2", 1));
            assertTrue(controller.getBaseActionController().picksCloud("player2", 0));
        } else {
            assertTrue(controller.getBaseActionController().picksCloud("player2", 1));
            assertFalse(controller.getBaseActionController().picksCloud("player1", 1));
            assertTrue(controller.getBaseActionController().picksCloud("player1", 0));
        }

    }*/
}
