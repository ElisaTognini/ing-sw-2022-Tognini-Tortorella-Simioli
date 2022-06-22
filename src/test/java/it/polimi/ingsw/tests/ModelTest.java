package it.polimi.ingsw.tests;

import it.polimi.ingsw.Utils.Enums.GameMode;
import it.polimi.ingsw.Model.Model;
import static org.junit.Assert.*;
import org.junit.jupiter.api.Test;

/** Class ModelTest tests ModelTest */

public class ModelTest {
    Model model;

    /** Method gameOverTest checks that method isGameOver returns false right after initialisation */
    @Test
    public void gameOverTest(){
        String[] nicknames = new String[2];
        nicknames[0] = "player1";
        nicknames[1] = "player2";

        model = new Model(GameMode.SIMPLE, nicknames, 2);
        model.getRoundManager().computeTurnOrder(model.getRoundManager().pickFirstPlayerIndex());
        model.getBoard().setup();
        assertFalse(model.isGameOver());
    }

    /** Method getterTest checks that methods getMode and getNumberOfClouds return the correct values. */
    @Test
    public void getterTest(){
        String[] nicknames = new String[2];
        nicknames[0] = "player1";
        nicknames[1] = "player2";
        GameMode m;
        int n;

        model = new Model(GameMode.EXPERT, nicknames, 2);
        model.getRoundManager().computeTurnOrder(model.getRoundManager().pickFirstPlayerIndex());
        model.getBoard().setup();

        m = model.getMode();
        n = model.getNumberOfClouds();

        assertTrue(m.equals(GameMode.EXPERT));
        assertTrue(n == 2);
    }


    /** Method getWinnerTest removes three towers from player1's tower section and then checks if method getWinner
     * returns player1 as winner, since they have the lesser number of towers inside the tower section. */
    @Test
    public void getWinnerTest(){
        String[] nicknames = new String[2];
        nicknames[0] = "player1";
        nicknames[1] = "player2";

        model = new Model(GameMode.SIMPLE, nicknames, 2);
        model.getRoundManager().computeTurnOrder(model.getRoundManager().pickFirstPlayerIndex());
        model.getBoard().setup();

        model.getBoard().getPlayerSchoolBoard("player1").getTowerSection().towersToIsland(3);
        model.getWinner();

        System.out.println(model.getWinner().getNickname());

    }
}
