package it.polimi.ingsw.tests;

/* choose a type of student: every player (including yourself) must return 3 students of that type
 *  from their dining room to the bag. If any player has fewer than 3 students of that type,
 *  return as many students as they have */

import it.polimi.ingsw.Model.BoardClasses.BoardExpert;
import it.polimi.ingsw.Utils.Enums.GameMode;
import it.polimi.ingsw.Utils.Enums.PawnDiscColor;
import it.polimi.ingsw.Model.Expert.CardManager;
import it.polimi.ingsw.Model.Expert.CharacterCardTemplate;
import it.polimi.ingsw.Model.Expert.Parameter;
import it.polimi.ingsw.Model.Player;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

public class Card12Test {

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
        cards[0] = manager.returnCard(12);
        cards[1] = manager.returnCard(1);
        cards[2] = manager.returnCard(4);

        board.setExtractedCards(cards);
    }

    @Test
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

    }

    @Test
    public void toStringCardTest(){
        initTest();
        System.out.println(board.getExtractedCards()[0].toStringCard());
    }

}
