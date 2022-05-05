package it.polimi.ingsw.tests;
import it.polimi.ingsw.BoardClasses.BoardExpert;
import it.polimi.ingsw.Enums.GameMode;
import it.polimi.ingsw.Enums.PawnDiscColor;
import it.polimi.ingsw.Expert.CardManager;
import it.polimi.ingsw.Expert.CharacterCardTemplate;
import it.polimi.ingsw.Expert.Parameter;
import it.polimi.ingsw.Player;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.Assert.*;

/* in setup, draw 4 students and place them on this card
 *  take 1 student from this card and place it in your dining room.
 *  then, take a new student from the bag and place it on this card */
public class Card11Test {
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
        cards[0] = manager.returnCard(11);
        cards[1] = manager.returnCard(1);
        cards[2] = manager.returnCard(4);

        board.setExtractedCards(cards);
    }

    @RepeatedTest(10)
    public void usageTest(){
        initTest();

        Parameter param = new Parameter();
        param.setColor(PawnDiscColor.YELLOW);

        System.out.println(board.getPlayerSchoolBoard("player1").getDiningRoom());
        if(!board.isActionForbidden(11, param, "player1")){
            board.useCard(param, "player1", 11);
            System.out.println(board.getPlayerSchoolBoard("player1").getDiningRoom());
        }
        else
        {
            System.out.println("action not permitted");
        }
    }

    /*to test: last three students remaining with an empty bag.*/

}
