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

import static org.junit.Assert.assertEquals;

/** Class Card11Test tests class Card11*/

public class Card11Test {
    BoardExpert board;
    ArrayList<Player> players;

    /** Method initTest tests the initialisation of the cards */
    @Test
    public void initTest(){
        CharacterCardTemplate[] cards;
        players = new ArrayList<>();
        players.add(new Player("player1"));
        players.add(new Player("player2"));
        board = new BoardExpert(players, 2, 8, 3, 7, GameMode.EXPERT);
        board.setup();
        CardManager manager = new CardManager(board);

        cards = new CharacterCardTemplate[3];
        cards[0] = manager.returnCard(11);
        cards[1] = manager.returnCard(1);
        cards[2] = manager.returnCard(4);

        board.setExtractedCards(cards);
    }

    /** Method usageTest tests the usage of the card and its impact on the game: if the selected color is present among
     * the ones of the students which are on the card, it prints the player's dining room before and after the card is
     * used. Moreover, it covers the corner case where the student bag is empty and the card is not refilled after use.
     *
     **/
    @RepeatedTest(5)
    public void usageTest(){
        int x;

        initTest();

        Parameter param = new Parameter();
        param.setColor(PawnDiscColor.YELLOW);

        System.out.println(board.getPlayerSchoolBoard("player1").getDiningRoom());
        if(!board.isActionForbidden(11, param, "player1")){
            board.useCard(param, "player1", 11);
            System.out.println(board.getPlayerSchoolBoard("player1").getDiningRoom());
        }
        else
        {
            System.out.println("action not permitted");
        }

        x = board.getStudentBag().availableStudents();
        for(int i= 0; i < x ; i++) {
            board.getStudentBag().drawStudent();
        }

        assertEquals(0, board.getStudentBag().availableStudents());

        System.out.println(board.getExtractedCards()[0].toStringCard());
        param.setColor(PawnDiscColor.GREEN);
        if(!board.isActionForbidden(11, param, "player1")) {
            board.useCard(param, "player1", 11);
            System.out.println(board.getExtractedCards()[0].toStringCard());
        }

    }


    /** Method toStringCardTest checks if toStringCard prints the correct info about the card selected. */
    @Test
    public void toStringCardTest(){
        initTest();
        System.out.println(board.getExtractedCards()[0].toStringCard());
    }

}
