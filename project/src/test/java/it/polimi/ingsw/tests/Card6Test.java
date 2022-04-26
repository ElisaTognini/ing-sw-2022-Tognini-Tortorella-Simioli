package it.polimi.ingsw.tests;

import it.polimi.ingsw.BoardClasses.BoardExpert;
import it.polimi.ingsw.Enums.GameMode;
import it.polimi.ingsw.Expert.CardManager;
import it.polimi.ingsw.Expert.CharacterCardTemplate;
import it.polimi.ingsw.Expert.Parameter;
import it.polimi.ingsw.Player;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertFalse;

public class Card6Test {
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
        cards[0] = manager.returnCard(6);
        cards[1] = manager.returnCard(4);
        cards[2] = manager.returnCard(5);

        board.setExtractedCards(cards);
    }

    @Test
    public void usageTest(){
        initTest();
        Parameter param = new Parameter();
        /* aggiungere le torri per controllare se funziona la carta */
        param.setIslandID(8);
        try {
            board.useCard(param, "player1", 5);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("not allowed\n");
        }
    }
}
