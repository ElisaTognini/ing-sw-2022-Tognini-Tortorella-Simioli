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

/** Class Card2Test tests class Card2*/

public class Card2Test {

    BoardExpert board;
    ArrayList<Player> players;

    /** Method initTest tests the initialisation of the cards */
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
        cards[0] = manager.returnCard(2);
        cards[1] = manager.returnCard(1);
        cards[2] = manager.returnCard(4);

        board.setExtractedCards(cards);
    }

    /** Method usageTest tests multiple usages of the card and its impact on the game, calling isActionForbidden to make sure
     * that the action is not forbidden, that the parameters are valid.
     *
     * @throws IllegalArgumentException e if the format of the argument passed is wrong. */
    @Test
    public void usageTest(){
        initTest();
        if(board.getPlayerSchoolBoard("player1").getEntrance().getColorAvailability(PawnDiscColor.PINK) >=2) {
            board.moveStudent(PawnDiscColor.PINK, "player1");
            board.moveStudent(PawnDiscColor.PINK, "player1", board.getMotherNaturePosition());
        }
        board.assignProfessors();
        Parameter param = new Parameter();
        ArrayList<PawnDiscColor> colors = new ArrayList<>();
        colors.add(PawnDiscColor.GREEN);
        colors.add(PawnDiscColor.BLUE);
        colors.add(PawnDiscColor.PINK);
        param.setColorArrayList(colors);
        System.out.println(board.getPlayerSchoolBoard("player2").getProfessorTable());
        try{
            board.useCard(param, "player2", 2);
            System.out.println(board.getPlayerSchoolBoard("player2").getProfessorTable());
        } catch(IllegalArgumentException e){
            System.out.println("Wrong format");
        }
        board.assignProfessors();
        board.conquerIsland();
        System.out.println(board.getPlayerSchoolBoard("player2").getProfessorTable());


        colors = new ArrayList<>();
        colors.add(PawnDiscColor.RED);
        colors.add(PawnDiscColor.YELLOW);
        param.setColorArrayList(colors);
        System.out.println(board.getPlayerSchoolBoard("player2").getProfessorTable());
        try{
            board.useCard(param, "player2", 2);
            System.out.println(board.getPlayerSchoolBoard("player2").getProfessorTable());
        } catch(IllegalArgumentException e){
            System.out.println("Wrong format");
        }
        board.roundSetup();
        System.out.println(board.getPlayerSchoolBoard("player2").getProfessorTable());
    }

    /** Method exceptionTest tests if method useCard correctly throws an exception if the format passed as parameter
     * is not valid
     *
     * @throws IllegalArgumentException e if the format of the argument passed is wrong. */
    @Test
    public void exceptionTest(){
        initTest();
        try {
            board.useCard("ciao", "player2", 2);
        }catch(IllegalArgumentException e){
            System.out.println("error");
        }
    }


    /** Method forbiddenActionTest passes an array list of colors as parameter and then calls for isActionForbidden
     * to check if parameters are correct.*/
    @Test
    public void forbiddenActionTest(){
        initTest();
        Parameter param = new Parameter();
        ArrayList<PawnDiscColor> colors = new ArrayList<>();
        colors.add(PawnDiscColor.GREEN);
        colors.add(PawnDiscColor.BLUE);
        colors.add(PawnDiscColor.PINK);
        param.setColorArrayList(colors);

        assertFalse(board.isActionForbidden(2, param, "player2"));
    }


    /** Method toStringCardTest checks if toStringCard prints the correct info about the card selected. */
    @Test
    public void toStringCardTest(){
        initTest();
        System.out.println(board.getExtractedCards()[0].toStringCard());
    }

}
