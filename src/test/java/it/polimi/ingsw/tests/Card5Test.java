package it.polimi.ingsw.tests;

import it.polimi.ingsw.Model.BoardClasses.BoardExpert;
import it.polimi.ingsw.Utils.Enums.GameMode;
import it.polimi.ingsw.Model.Expert.CardManager;
import it.polimi.ingsw.Model.Expert.CharacterCardTemplate;
import it.polimi.ingsw.Model.Expert.Parameter;
import it.polimi.ingsw.Model.Player;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;

/** Class Card4Test tests class Card4 */

public class Card5Test {
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
        cards[0] = manager.returnCard(1);
        cards[1] = manager.returnCard(4);
        cards[2] = manager.returnCard(5);

        board.setExtractedCards(cards);
    }

    /** Method usageTest tests the usage of the card and its impact on the game;
     * it covers corner case of more than one no entry tile on the same island and checks if the number of no entry tiles
     * on the board decrease accordingly to the usage of the no entry tiles.
     *
     * @throws IndexOutOfBoundsException e if the integer passed does not correspond to an ID of an existent island.
     **/
    @Test
    public void usageTest(){
        initTest();
        Parameter param = new Parameter();

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


    /** Method forbiddenActionTest sets the ID of the island where the no entry tiles will be added and then tries to
     * add one to said island 7 times, the first four times it should go successfully, then the other three times action
     * will be forbidden because there are no no entry tiles available on the board.
     * Then, it sets the island ID to an invalid value to see if method isActionForbidden works properly for the island
     * ID too.
     *
     **/
    @Test
    public void forbiddenActionTest(){
        initTest();
        Parameter param = new Parameter();
        param.setIslandID(2);

        for(int i = 0; i < 7; i++){
            if(!board.isActionForbidden(5, param, "player1"))
            {
                board.useCard(param, "player1", 5);
                System.out.println("NE tile placed.");
            }
            else{
                System.out.println("out of NE tiles");
            }
        }

        param.setIslandID(13);
        assertTrue(board.isActionForbidden(5, param, "player1"));
    }


    /** Method NEtileRetrievalTest checks if no entry tiles are retrieved correctly when the card is used. */
    @Test
    public void NEtileRetrievalTest(){
        forbiddenActionTest();
        System.out.println("we have " + board.getIslandList().get(2).getNumberOfNEtiles() + " NE tiles");
        board.setMotherNaturePosition(2);
        board.conquerIsland();
        assertEquals(3, board.getIslandList().get(2).getNumberOfNEtiles());
        board.conquerIsland();
        assertEquals(2, board.getIslandList().get(2).getNumberOfNEtiles());
    }

    /** Method toStringCardTest checks if toStringCard prints the correct info about the card selected. */
    @Test
    public void toStringCardTest(){
        initTest();
        System.out.println(board.getExtractedCards()[2].toStringCard());
    }

}
