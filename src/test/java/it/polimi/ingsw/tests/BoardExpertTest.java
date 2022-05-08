package it.polimi.ingsw.tests;

import it.polimi.ingsw.BoardClasses.BoardExpert;
import it.polimi.ingsw.Enums.GameMode;
import it.polimi.ingsw.Enums.PawnDiscColor;
import it.polimi.ingsw.Expert.Parameter;
import it.polimi.ingsw.Model;
import it.polimi.ingsw.Player;
import static org.junit.Assert.*;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class BoardExpertTest {

    private BoardExpert board;
    private ArrayList<Player> players = new ArrayList<Player>();
    Model model;

    @Test
    /* following the execution of the method with the debugger;
    * instantiation of the three random cards is also tested here -
    * the methods involved work correctly */
    public void setupTest(){
        players.add(new Player("player1"));
        players.add(new Player("player2"));
        board = new BoardExpert(players, 2,8,3,7, GameMode.EXPERT);
        board.setup();
    }

    @Test
    /* tests whether a coin is assigned for every three students of the same
    * color that are placed in the dining room*/
    public void assignCoinTest(){
        players.add(new Player("player1"));
        players.add(new Player("player2"));
        board = new BoardExpert(players, 2,8,3,30, GameMode.EXPERT);
        board.setup();

        for(int i = 0; i < 7; i++) {
            if (board.getPlayerSchoolBoard("player1").getEntrance().isColorAvailable(PawnDiscColor.PINK)) {
                board.moveStudent(PawnDiscColor.PINK, "player1");
                System.out.println("player has " + board.getPlayerSchoolBoard("player1").getDiningRoom().influenceForProf(PawnDiscColor.PINK) + " pink students in dining room");
            }
            System.out.println("this player owns " + board.getPlayersCoinCounter("player1").getCoins() + " coins" );
        }
    }

    /* purchase card test - testing the unavailability of a card and the case in which
    * a player does not have enough coins, as well as the increasing of the cost of the card after usage*/
    @RepeatedTest(50)
    public void purchaseCardTest(){
        players.add(new Player("player1"));
        players.add(new Player("player2"));
        int prevCost;
        Parameter param = new Parameter();
        ArrayList<PawnDiscColor> colors = new ArrayList<>();
        colors.add(PawnDiscColor.BLUE);
        colors.add(PawnDiscColor.GREEN);
        param.setColorArrayList(colors);
        board = new BoardExpert(players, 2,8,3,10, GameMode.EXPERT);
        board.setup();

        /* player earns some coins */
        for(int i = 0; i < 7; i++) {
            if (board.getPlayerSchoolBoard("player1").getEntrance().isColorAvailable(PawnDiscColor.RED)) {
                board.moveStudent(PawnDiscColor.RED, "player1");
            }
        }

        /* purchasing and using card */
        if(board.checkIfCardPresent(2) && board.getPlayersCoinCounter("player1").checkIfEnoughCoins(
                board.getCardsCost(2)
        )){
            prevCost = board.getCardsCost(2);
            board.purchaseCharacterCard("player1", 2);
            board.useCard(param, "player1", 2);
            System.out.println("card 2 purchased; player now has " + board.getPlayersCoinCounter("player1").getCoins() + " coins");
            assertEquals(prevCost+1, board.getCardsCost(2));

        }
        else if(!board.checkIfCardPresent(2))
            System.out.println("card 2 not available.");
        else
            System.out.println("player does not have enough coins");
    }


}
