package it.polimi.ingsw.tests;

import it.polimi.ingsw.BoardClasses.BoardExpert;
import it.polimi.ingsw.Enums.GameMode;
import it.polimi.ingsw.Enums.PawnDiscColor;
import it.polimi.ingsw.Expert.CardManager;
import it.polimi.ingsw.Expert.CharacterCardTemplate;
import it.polimi.ingsw.Expert.Parameter;
import it.polimi.ingsw.Player;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;

public class Card5Test {
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
        cards[0] = manager.returnCard(1);
        cards[1] = manager.returnCard(4);
        cards[2] = manager.returnCard(5);

        board.setExtractedCards(cards);
    }

    @Test
    public void usageTest(){
        initTest();
        Parameter param = new Parameter();
        /* covers corner case of more than one no entry tile on the same island*/
        for(int i=0; i<4;i++ ) {
            param.setIslandID(3);
            try {
                board.useCard(param, "player1", 5);
            } catch (IndexOutOfBoundsException e) {
                System.out.println("not allowed\n");
            }
        }
        assertFalse(board.checkIfEnoughNoEntryTiles());
    }

    @Test
    public void forbiddenActionTest(){
        /* to be added: corner case test for when all of the no entry tiles have been placed
        *  and also to be checked if they are added back to the board once a player goes on the island */
    }

}
