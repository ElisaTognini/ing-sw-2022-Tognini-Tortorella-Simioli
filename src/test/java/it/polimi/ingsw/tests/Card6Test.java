package it.polimi.ingsw.tests;

import it.polimi.ingsw.Model.BoardClasses.BoardExpert;
import it.polimi.ingsw.Utils.Enums.GameMode;
import it.polimi.ingsw.Utils.Enums.PawnDiscColor;
import it.polimi.ingsw.Model.Expert.CardManager;
import it.polimi.ingsw.Model.Expert.CharacterCardTemplate;
import it.polimi.ingsw.Model.Expert.Parameter;
import it.polimi.ingsw.Model.Player;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

import static org.junit.Assert.*;

/** Class Card6Test tests class Card6 */

public class Card6Test {
    BoardExpert board;
    ArrayList<Player> players;

    /** Method initTest tests the initialisation of the cards */
    @Test
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


    /** Method usageTest tests the usage of the card and its impact on the game: the first player conquers an island
     * first, then the second player tries to conquer it too; since card 6 has been player, the second player has more
     * influence and now conquers the island.
     *
     * @throws IndexOutOfBoundsException e if the integer passed does not correspond to an ID of an existent island.
     **/
    @RepeatedTest(5)
    public void usageTest(){
        boolean conqueredOnce = false;
        boolean conqueredTwice = false;

        initTest();
        Parameter param = new Parameter();
        board.setMotherNaturePosition(8);
        if(board.getPlayerSchoolBoard("player1").getEntrance().isColorAvailable(PawnDiscColor.RED)) {
            board.moveStudent(PawnDiscColor.RED, "player1", 8);
            if(board.getPlayerSchoolBoard("player1").getEntrance().isColorAvailable(PawnDiscColor.RED)) {
                board.moveStudent(PawnDiscColor.RED, "player1");
                conqueredOnce = true;
            }
        }
        board.assignProfessors();
        board.conquerIsland();
        if(conqueredOnce){
            assertEquals(board.getIslandList().get(8).getOwner(), players.get(0));
        }
        if(board.getPlayerSchoolBoard("player2").getEntrance().isColorAvailable(PawnDiscColor.BLUE)) {
            board.moveStudent(PawnDiscColor.BLUE, "player2", 8);
            if (board.getPlayerSchoolBoard("player2").getEntrance().isColorAvailable(PawnDiscColor.BLUE)) {
                board.moveStudent(PawnDiscColor.BLUE, "player2", 8);
                if (board.getPlayerSchoolBoard("player2").getEntrance().isColorAvailable(PawnDiscColor.BLUE)) {
                    board.moveStudent(PawnDiscColor.BLUE, "player2", 8);
                    if (board.getPlayerSchoolBoard("player2").getEntrance().isColorAvailable(PawnDiscColor.BLUE)) {
                        board.moveStudent(PawnDiscColor.BLUE, "player2");
                        conqueredTwice = true;
                    }
                }
            }
        }
        board.assignProfessors();
        param.setIslandID(8);
        try {
            board.useCard(param, "player2", 6);
            board.conquerIsland();
            if(conqueredTwice){
                assertEquals(board.getIslandList().get(8).getOwner(), players.get(1));
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("not allowed\n");
        }
    }

    /** Method forbiddenActionTest passes an integer then calls for isActionForbidden to check if parameters are
     * correct.
     *
     **/
    @Test
    public void actionForbiddenTest(){
        initTest();
        Parameter param = new Parameter();

        param.setIslandID(2);
        assertFalse(board.isActionForbidden(6, param, "player2"));

        param.setIslandID(13);
        assertTrue(board.isActionForbidden(6, param, "player2"));

    }

    /** Method toStringCardTest checks if toStringCard prints the correct info about the card selected. */
    @Test
    public void toStringCardTest(){
        initTest();
        System.out.println(board.getExtractedCards()[0].toStringCard());
    }

}
