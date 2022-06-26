package it.polimi.ingsw.tests;

import it.polimi.ingsw.Model.BoardClasses.BoardExpert;
import it.polimi.ingsw.Utils.Enums.GameMode;
import it.polimi.ingsw.Model.Expert.CardManager;
import it.polimi.ingsw.Model.Expert.CharacterCardTemplate;
import it.polimi.ingsw.Model.Expert.Parameter;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Utils.Enums.PawnDiscColor;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/** Class Card3Test tests class Card3*/

public class Card3Test {

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
        cards[0] = manager.returnCard(3);
        cards[1] = manager.returnCard(1);
        cards[2] = manager.returnCard(4);

        board.setExtractedCards(cards);
    }


    /** Method usageTest tests the usage of the card and its impact on the game
     *
     * @throws IllegalArgumentException e if the format of the argument passed is wrong. */
    @RepeatedTest(5)
    public void usageTest(){
        initTest();
        Parameter param = new Parameter();
        param.setIslandID(3);
        for(PawnDiscColor c: PawnDiscColor.values()) {
            if (board.getSchoolBoards().get(0).getEntrance().isColorAvailable(c)) {
                board.moveStudent(c, "player1");
            }
        }
        board.useCard(param, "player1", 3);
        if(board.getIslandList().get(3).getOwner() != null)
            assertTrue(board.getIslandList().get(3).getOwner().equals(board.getSchoolBoards().get(0).getOwner()));
    }


    /** Method forbiddenActionTest passes an array list of colors as parameter and then calls for isActionForbidden
     * to check if parameters are correct.
     *
     * @throws IllegalArgumentException e if the format of the argument passed is wrong. */
    @RepeatedTest(5)
    public void forbiddenActionTest(){
        initTest();
        Parameter param = new Parameter();
        param.setIslandID(3);

        assertFalse(board.isActionForbidden(3, param, "player2"));

        try{
            board.isActionForbidden(3, "ciao", "player2");
        } catch (IllegalArgumentException e){
            System.out.println("Exception caught! Illegal argument passed");
        }
    }


    /** Method toStringCardTest checks if toStringCard prints the correct info about the card selected. */
    @Test
    public void toStringCardTest(){
        initTest();
        System.out.println(board.getExtractedCards()[0].toStringCard());
    }

}
