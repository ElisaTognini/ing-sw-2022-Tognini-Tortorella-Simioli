package it.polimi.ingsw.tests;

import it.polimi.ingsw.Model.BoardClasses.BoardExpert;
import it.polimi.ingsw.Utils.Enums.GameMode;
import it.polimi.ingsw.Model.Expert.CardManager;
import it.polimi.ingsw.Model.Expert.CharacterCardTemplate;
import it.polimi.ingsw.Model.Expert.Parameter;
import it.polimi.ingsw.Model.Player;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class Card8Test {

    BoardExpert board;
    ArrayList<Player> players;
    CharacterCardTemplate[] cards;

    public void initTest(){
        players = new ArrayList<>();
        players.add(new Player("player1"));
        players.add(new Player("player2"));
        board = new BoardExpert(players, 2, 8, 3, 20, GameMode.EXPERT);
        board.setup();
        CardManager manager = new CardManager(board);

        cards = new CharacterCardTemplate[3];
        cards[0] = manager.returnCard(8);
        cards[1] = manager.returnCard(4);
        cards[2] = manager.returnCard(5);

        board.setExtractedCards(cards);
    }

    /* ? */
    @Test
    public void usageTest(){
        initTest();
        Parameter param = new Parameter();

        if(!board.isActionForbidden(8, param, "player2")){
            board.useCard(param, "player2", 8);
            board.conquerIsland();
            board.roundSetup();
        }
    }

    @Test
    public void toStringCardTest(){
        initTest();
        System.out.println(board.getExtractedCards()[0].toStringCard());
    }
}
