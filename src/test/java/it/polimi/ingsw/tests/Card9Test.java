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

/** Class Card9Test tests class Card9 */

public class Card9Test {
    BoardExpert board;
    ArrayList<Player> players;
    CharacterCardTemplate[] cards;

    /** Method initTest tests the initialisation of the cards */
    @Test
    public void initTest(){
        players = new ArrayList<>();
        players.add(new Player("player1"));
        players.add(new Player("player2"));
        board = new BoardExpert(players, 2, 8, 3, 20, GameMode.EXPERT);
        board.setup();
        CardManager manager = new CardManager(board);

        cards = new CharacterCardTemplate[3];
        cards[0] = manager.returnCard(9);
        cards[1] = manager.returnCard(4);
        cards[2] = manager.returnCard(5);

        board.setExtractedCards(cards);
    }

    /** Method usageTest tests the usage of the card and its impact on the game: firstly, player 1 conquers an island;
     * then player2 plays this card to cancel pink influence and conquer the island themselves.
     *
     **/
    @RepeatedTest(5)
    public void usageTest(){
        initTest();
        Parameter param = new Parameter();
        board.setMotherNaturePosition(8);
        if(board.getPlayerSchoolBoard("player1").getEntrance().isColorAvailable(PawnDiscColor.PINK)) {
            board.moveStudent(PawnDiscColor.PINK, "player1", 8);
            if(board.getPlayerSchoolBoard("player1").getEntrance().isColorAvailable(PawnDiscColor.PINK)) {
                board.moveStudent(PawnDiscColor.PINK, "player1", 8);
                if (board.getPlayerSchoolBoard("player1").getEntrance().isColorAvailable(PawnDiscColor.PINK)) {
                    board.moveStudent(PawnDiscColor.PINK, "player1");
                }
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
        param.setColor(PawnDiscColor.PINK);
        if(!board.isActionForbidden(9, param, "player2")){
            board.useCard(param, "player2", 9);
            board.conquerIsland();
            board.roundSetup();
        }

        System.out.println("conqueror is " + board.getIslandList().get(board.getMotherNaturePosition()).getOwner().getNickname());
    }

    /** Method toStringCardTest checks if toStringCard prints the correct info about the card selected. */
    @Test
    public void toStringCardTest(){
        initTest();
        System.out.println(board.getExtractedCards()[0].toStringCard());
    }
}
