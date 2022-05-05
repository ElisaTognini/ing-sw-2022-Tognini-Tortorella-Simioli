package it.polimi.ingsw.tests;

import it.polimi.ingsw.BoardClasses.BoardExpert;
import it.polimi.ingsw.Enums.GameMode;
import it.polimi.ingsw.Enums.PawnDiscColor;
import it.polimi.ingsw.Expert.CardManager;
import it.polimi.ingsw.Expert.CharacterCardTemplate;
import it.polimi.ingsw.Expert.Parameter;
import it.polimi.ingsw.Player;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertFalse;

public class Card6Test {
    BoardExpert board;
    ArrayList<Player> players;

    @Test
    //testing the initialization of the card
    public void initTest(){
        CharacterCardTemplate[] cards;
        players = new ArrayList<>();
        players.add(new Player("player1"));
        players.add(new Player("player2"));
        board = new BoardExpert(players, 2, 8, 3, 20, GameMode.EXPERT);
        board.setup();
        CardManager manager = new CardManager(board);

        cards = new CharacterCardTemplate[3];
        cards[0] = manager.returnCard(6);
        cards[1] = manager.returnCard(4);
        cards[2] = manager.returnCard(5);

        board.setExtractedCards(cards);
    }

    @Test
    public void usageTest(){
        initTest();
        Parameter param = new Parameter();
        board.setMotherNaturePosition(8);
        if(board.getPlayerSchoolBoard("player1").getEntrance().isColorAvailable(PawnDiscColor.RED)) {
            board.moveStudent(PawnDiscColor.RED, "player1", 8);
            if(board.getPlayerSchoolBoard("player1").getEntrance().isColorAvailable(PawnDiscColor.RED)) {
                board.moveStudent(PawnDiscColor.RED, "player1");
            }
        }
        board.assignProfessors();
        board.conquerIsland();
        if(board.getPlayerSchoolBoard("player2").getEntrance().isColorAvailable(PawnDiscColor.BLUE)) {
            board.moveStudent(PawnDiscColor.BLUE, "player2", 8);
            if(board.getPlayerSchoolBoard("player2").getEntrance().isColorAvailable(PawnDiscColor.BLUE)) {
                board.moveStudent(PawnDiscColor.BLUE, "player2", 8);
                if (board.getPlayerSchoolBoard("player2").getEntrance().isColorAvailable(PawnDiscColor.BLUE)) {
                    board.moveStudent(PawnDiscColor.BLUE, "player2");
                }
            }
        }
        board.assignProfessors();
        param.setIslandID(8);
        try {
            board.useCard(param, "player2", 6);
            board.conquerIsland();
        } catch (IndexOutOfBoundsException e) {
            System.out.println("not allowed\n");
        }
    }

    @Test
    public void actionForbiddenTest(){
        initTest();
        Parameter param = new Parameter();
        param.setIslandID(2);
        assertFalse(board.isActionForbidden(6, param, "player2"));
    }

}
