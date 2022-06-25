package it.polimi.ingsw.tests;

import it.polimi.ingsw.Model.BoardClasses.BoardExpert;
import it.polimi.ingsw.Utils.Enums.GameMode;
import it.polimi.ingsw.Utils.Enums.PawnDiscColor;
import it.polimi.ingsw.Model.Expert.Parameter;
import it.polimi.ingsw.Model.Model;
import it.polimi.ingsw.Model.Player;
import static org.junit.Assert.*;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

/** Class BoardExpertTest tests class BoardExpert. */

public class BoardExpertTest {

    private BoardExpert board;
    private ArrayList<Player> players = new ArrayList<Player>();
    Model model;


    /** Method setupTest adds two new instances of player to the playerList and then creates and sets up a new
     * instance of BoardExpert. */
    @Test
    public void setupTest(){
        players.add(new Player("player1"));
        players.add(new Player("player2"));
        board = new BoardExpert(players, 2,8,3,7, GameMode.EXPERT);
        board.setup();
    }


    /** Method assignCoinTest tests whether a coin is assigned for every three students of the same color that are
     * placed in the dining room*/
    @Test
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


    /** Method purchaseCardTest tests purchase of a character card and handles the unavailability of a card, the case
     * in which a player does not have enough coins, as well as the increasing of the cost of the card after usage. */
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

        for(int i = 0; i < 7; i++) {
            if (board.getPlayerSchoolBoard("player1").getEntrance().isColorAvailable(PawnDiscColor.RED)) {
                board.moveStudent(PawnDiscColor.RED, "player1");
            }
        }

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

