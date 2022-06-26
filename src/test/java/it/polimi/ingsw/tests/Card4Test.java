package it.polimi.ingsw.tests;

import it.polimi.ingsw.Model.BoardClasses.BoardExpert;
import it.polimi.ingsw.Utils.Enums.GameMode;
import it.polimi.ingsw.Model.Expert.CardManager;
import it.polimi.ingsw.Model.Expert.CharacterCardTemplate;
import it.polimi.ingsw.Model.Expert.Parameter;
import it.polimi.ingsw.Model.Player;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.Assert.*;

/** Class Card4Test tests class Card4*/

public class Card4Test {
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
        cards[0] = manager.returnCard(4);
        cards[1] = manager.returnCard(5);
        cards[2] = manager.returnCard(6);

        board.setExtractedCards(cards);
    }


    /** Method usageTest tests the usage of the card and its impact on the game
     *
     * @throws IndexOutOfBoundsException e if the integer passed is less than 0 or bigger than 2.
     **/
    @Test
    public void usageTest(){
        initTest();
        Parameter param = new Parameter();
        param.setMoves(1);
        try {
            board.useCard(param, "player1", 4);
        }catch(IndexOutOfBoundsException e){
            System.out.println("not allowed\n");
        }
        param.setMoves(3);
        try {
            board.useCard(param, "player1", 4);
        }catch(IndexOutOfBoundsException e){
            System.out.println("not allowed\n");
        }
    }


    /** Method forbiddenActionTest passes an integer then calls for isActionForbidden to check if parameters are
     * correct.
     *
     **/
    @Test
    public void forbiddenActionTest(){
        initTest();
        Parameter param = new Parameter();

        param.setMoves(6);
        assertTrue(board.isActionForbidden(4, param, "player2"));

        param.setMoves(2);
        assertFalse(board.isActionForbidden(4, param, "player2"));
    }


    /** Method toStringCardTest checks if toStringCard prints the correct info about the card selected. */
    @Test
    public void toStringCardTest(){
        initTest();
        System.out.println(board.getExtractedCards()[0].toStringCard());
    }

}
