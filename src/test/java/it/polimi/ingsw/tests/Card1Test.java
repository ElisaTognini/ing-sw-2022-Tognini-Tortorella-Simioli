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

/** Class Card1Test tests class Card1*/

public class Card1Test {

    BoardExpert board;
    ArrayList<Player> players;

    /** Method initTest tests the initialisation of the card */
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
        cards[1] = manager.returnCard(2);
        cards[2] = manager.returnCard(3);

        board.setExtractedCards(cards);
    }


    /** Method usageTest tests the usage of the card and its impact on the game, calling isActionForbidden to make sure
     * that the action is not forbidden, that the parameters are valid. */
    @RepeatedTest(5)
    public void usageTest(){
        initTest();
        Parameter param = new Parameter();
        param.setColor(PawnDiscColor.PINK);
        param.setIslandID(2);
        if(!board.isActionForbidden(1, param, "player1")) {
            board.useCard(param, "player1", 1);
        }
        else
            System.out.println("No pink students on card");
    }


    /** Method toStringCardTest checks if toStringCard prints the correct info about the card selected. */
    @Test
    public void toStringCardTest(){
        initTest();
        System.out.println(board.getExtractedCards()[0].toStringCard());
    }

}
