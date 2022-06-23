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

/* you may switch up to 2 students between your entrance and your dining room */
public class Card10Test {
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

        //in the actual game there will always be three different cards
        cards = new CharacterCardTemplate[3];
        cards[0] = manager.returnCard(10);
        cards[1] = manager.returnCard(1);
        cards[2] = manager.returnCard(4);

        board.setExtractedCards(cards);
    }

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

    @Test
    public void toStringCardTest(){
        initTest();
        System.out.println(board.getExtractedCards()[0].toStringCard());
    }

}
