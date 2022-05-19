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

public class Card4Test {
    BoardExpert board;
    ArrayList<Player> players;

    @Test
    //testing the initialization of the card
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

    @Test
    /* test executed with two parameters, one allowed and the other forbidden; the test runs
    * successfully for the first parameter, while throws an index out of bound exception for the second, as
    * it should because the player's only allowed to make two additional moves*/
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

    @Test
    public void forbiddenActionTest(){
        initTest();
        Parameter param = new Parameter();
        param.setMoves(6);

        assertTrue(board.isActionForbidden(4, param, "player2"));
    }

    @Test
    public void toStringCardTest(){
        initTest();
        System.out.println(board.getExtractedCards()[0].toStringCard());
    }

}
