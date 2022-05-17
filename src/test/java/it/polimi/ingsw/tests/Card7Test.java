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

/* the player can choose up to three students on this card to switch with three in their entrance*/
public class Card7Test {

    BoardExpert board;
    ArrayList<Player> players;
    CharacterCardTemplate[] cards;

    @Test
    public void initTest(){
        players = new ArrayList<>();
        players.add(new Player("player1"));
        players.add(new Player("player2"));
        board = new BoardExpert(players, 2, 8, 3, 20, GameMode.EXPERT);
        board.setup();
        CardManager manager = new CardManager(board);

        cards = new CharacterCardTemplate[3];
        cards[0] = manager.returnCard(7);
        cards[1] = manager.returnCard(4);
        cards[2] = manager.returnCard(5);

        board.setExtractedCards(cards);
    }

    @RepeatedTest(10)
    public void usageTest(){
        initTest();
        Parameter param = new Parameter();
        ArrayList<PawnDiscColor> colors1 = new ArrayList<>();
        ArrayList<PawnDiscColor> colors2 = new ArrayList<>();
        System.out.println("TEST: card needs to have 1 green and 2 blue");
        colors1.add(PawnDiscColor.GREEN);
        colors1.add(PawnDiscColor.BLUE);
        colors1.add(PawnDiscColor.BLUE);
        param.setColorArrayList(colors1);
        System.out.println("entrance needs to have 1 yellow, 1 pink, 1 red");
        colors2.add(PawnDiscColor.YELLOW);
        colors2.add(PawnDiscColor.PINK);
        colors2.add(PawnDiscColor.RED);
        param.setColorArrayList2(colors2);

        System.out.println(cards[0]);
        System.out.println(board.getPlayerSchoolBoard("player2").getEntrance());
        if(!board.isActionForbidden(7 ,param, "player2")){
            System.out.println("action permitted.");
            board.useCard(param, "player2", 7);
            System.out.println("after action:");
            System.out.println(cards[0]);
            System.out.println(board.getPlayerSchoolBoard("player2").getEntrance());
        }
        else{
            System.out.println("not allowed to use this card: not enough students of requested colors.");
        }
    }

}
