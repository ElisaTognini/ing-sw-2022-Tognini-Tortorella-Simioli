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
import static org.junit.Assert.*;
import java.util.ArrayList;

/** Class Card10Test tests class Card10*/

public class Card10Test {
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
        cards[0] = manager.returnCard(10);
        cards[1] = manager.returnCard(1);
        cards[2] = manager.returnCard(4);

        board.setExtractedCards(cards);
    }

    /** Method usageTest tests the usage of the card and its impact on the game: it adds three colors for each array
     * list of colors, which represent the students to swap, then, if the action is not forbidden, proceeds to swap them
     * and finally prints to screen the entrance and the students now on the card.
     *
     * @throws IndexOutOfBoundsException e if there are no students available of selected color in the entrance. */
    @RepeatedTest(10)
    public void usageTest(){
        initTest();
        Parameter param = new Parameter();
        ArrayList<PawnDiscColor> colors1 = new ArrayList<>();
        ArrayList<PawnDiscColor> colors2 = new ArrayList<>();

        colors1.add(PawnDiscColor.YELLOW);
        colors1.add(PawnDiscColor.YELLOW);

        colors2.add(PawnDiscColor.GREEN);
        colors2.add(PawnDiscColor.BLUE);

        param.setColorArrayList(colors1);
        param.setColorArrayList2(colors2);

        System.out.println("needs two yellow in entrance and 1 green, 1 blue in dining room");

        try{
            board.moveStudent(PawnDiscColor.BLUE, "player1");
        }catch(IndexOutOfBoundsException e){}

        try{
            board.moveStudent(PawnDiscColor.GREEN, "player1");
        }catch(IndexOutOfBoundsException e){}

        System.out.println("TEST");
        System.out.println(board.getPlayerSchoolBoard("player1").getEntrance());
        System.out.println(board.getPlayerSchoolBoard("player1").getDiningRoom());

        if(!board.isActionForbidden(10, param, "player1")){
            board.useCard(param, "player1", 10);
            System.out.println(board.getPlayerSchoolBoard("player1").getEntrance());
            System.out.println(board.getPlayerSchoolBoard("player1").getDiningRoom());
        }else{
            System.out.println("action not permitted");
        }

    }

    /** Method forbiddenActionTest checks if the player passed the correct parameters in input and if the two
     * array lists of colors are of the same size.
     *
     *@throws IndexOutOfBoundsException e if there are no students available of selected color in the entrance. */
    @Test
    public void forbiddenActionsTest(){
        initTest();
        Parameter param = new Parameter();
        ArrayList<PawnDiscColor> colors1 = new ArrayList<>();
        ArrayList<PawnDiscColor> colors2 = new ArrayList<>();

        colors1.add(PawnDiscColor.YELLOW);
        colors1.add(PawnDiscColor.YELLOW);

        colors2.add(PawnDiscColor.GREEN);

        try{
            board.moveStudent(PawnDiscColor.GREEN, "player1");
        }catch(IndexOutOfBoundsException e){}

        param.setColorArrayList(colors1);
        param.setColorArrayList2(colors2);

        assertTrue(board.isActionForbidden(10, param, "player1"));

        colors2.add(PawnDiscColor.YELLOW);
        colors1.add(PawnDiscColor.YELLOW);

        param.setColorArrayList(colors1);

        assertTrue(board.isActionForbidden(10, param, "player1"));
    }

    /** Method toStringCardTest checks if toStringCard prints the correct info about the card selected. */
    @Test
    public void toStringCardTest(){
        initTest();
        System.out.println(board.getExtractedCards()[0].toStringCard());
    }

}
