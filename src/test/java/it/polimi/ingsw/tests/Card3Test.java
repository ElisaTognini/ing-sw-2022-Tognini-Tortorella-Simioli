package it.polimi.ingsw.tests;

import it.polimi.ingsw.Model.BoardClasses.BoardExpert;
import it.polimi.ingsw.Utils.Enums.GameMode;
import it.polimi.ingsw.Model.Expert.CardManager;
import it.polimi.ingsw.Model.Expert.CharacterCardTemplate;
import it.polimi.ingsw.Model.Expert.Parameter;
import it.polimi.ingsw.Model.Player;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.Assert.assertFalse;

public class Card3Test {

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

        //in the actual game there will always be three different cards
        cards = new CharacterCardTemplate[3];
        cards[0] = manager.returnCard(3);
        cards[1] = manager.returnCard(1);
        cards[2] = manager.returnCard(4);

        board.setExtractedCards(cards);
    }

    @Test
    public void usageTest(){
        initTest();
        Parameter param = new Parameter();
        param.setIslandID(3);
            board.useCard(param, "player1", 3);
    }

    @Test
    public void forbiddenActionTest(){
        initTest();
        Parameter param = new Parameter();
        param.setIslandID(3);

        assertFalse(board.isActionForbidden(3, param, "player2"));
    }

    @Test
    public void toStringCardTest(){
        initTest();
        System.out.println(board.getExtractedCards()[0].toStringCard());
    }

}
