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

import static org.junit.Assert.assertEquals;

/** Class Card12Test tests class Card12*/

public class Card12Test {

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
        cards[0] = manager.returnCard(12);
        cards[1] = manager.returnCard(1);
        cards[2] = manager.returnCard(4);

        board.setExtractedCards(cards);
    }

    /** Method usageTest tests the usage of the card and its impact on the game: firstly, each player moves a student
     three students of selected color (or as many as they have, if there are less than three of that color in the
     entrance); then, if action is not forbidden, it calls for useCard.
     **/
    @RepeatedTest(5)
    public void usageTest(){
        initTest();
        int i = 3;
        Parameter param = new Parameter();
        param.setColor(PawnDiscColor.BLUE);

        for(Player p : players){
            while(i > 0) {
                if (board.getPlayerSchoolBoard(p.getNickname()).getEntrance().isColorAvailable(PawnDiscColor.BLUE)) {
                    board.moveStudent(PawnDiscColor.BLUE, p.getNickname());
                }
                i--;
            }
            i = 0;
        }

        if(!board.isActionForbidden(12, param, "player2"))
            board.useCard(param, "player2", 12);

        for(Player p : players){
            assertEquals(0, board.getPlayerSchoolBoard(p.getNickname()).getDiningRoom().influenceForProf(PawnDiscColor.BLUE));
        }
    }

    /** Method toStringCardTest checks if toStringCard prints the correct info about the card selected. */
    @Test
    public void toStringCardTest(){
        initTest();
        System.out.println(board.getExtractedCards()[0].toStringCard());
    }

}
