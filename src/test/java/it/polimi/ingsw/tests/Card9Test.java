package it.polimi.ingsw.tests;

import it.polimi.ingsw.Model.BoardClasses.BoardExpert;
import it.polimi.ingsw.Utils.Enums.GameMode;
import it.polimi.ingsw.Utils.Enums.PawnDiscColor;
import it.polimi.ingsw.Model.Expert.CardManager;
import it.polimi.ingsw.Model.Expert.CharacterCardTemplate;
import it.polimi.ingsw.Model.Expert.Parameter;
import it.polimi.ingsw.Model.Player;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;

/* this card allows to choose a color of student that will add no influence
 *  during the influence calculation of the turn in which the card is played */
public class Card9Test {
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
        cards[0] = manager.returnCard(9);
        cards[1] = manager.returnCard(4);
        cards[2] = manager.returnCard(5);

        board.setExtractedCards(cards);
    }

    @Test
    public void usageTest(){
        initTest();
        Parameter param = new Parameter();
        board.setMotherNaturePosition(5);
        if(board.getPlayerSchoolBoard("player2").getEntrance().isColorAvailable(PawnDiscColor.BLUE)) {
            board.moveStudent(PawnDiscColor.BLUE, "player2", 8);
            if(board.getPlayerSchoolBoard("player2").getEntrance().isColorAvailable(PawnDiscColor.BLUE)) {
                board.moveStudent(PawnDiscColor.BLUE, "player2", 8);
                if (board.getPlayerSchoolBoard("player2").getEntrance().isColorAvailable(PawnDiscColor.BLUE)) {
                    board.moveStudent(PawnDiscColor.BLUE, "player2");
                }
            }
        }
        board.assignProfessors();
        param.setColor(PawnDiscColor.BLUE);
        if(!board.isActionForbidden(9, param, "player2")){
            board.useCard(param, "player2", 9);
            board.conquerIsland();
            board.roundSetup();
        }

        assertEquals(null, board.getIslandList().get(5).getOwner());
    }

    @Test
    public void toStringCardTest(){
        initTest();
        System.out.println(board.getExtractedCards()[0].toStringCard());
    }
}
