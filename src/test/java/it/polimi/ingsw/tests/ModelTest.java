package it.polimi.ingsw.tests;

import it.polimi.ingsw.Utils.Enums.GameMode;
import it.polimi.ingsw.Model.Model;
import static org.junit.Assert.*;
import org.junit.jupiter.api.Test;


public class ModelTest {
    Model model;

    @Test
    public void gameOverTest(){
        String[] nicknames = new String[2];
        nicknames[0] = "player1";
        nicknames[1] = "player2";

        model = new Model(GameMode.SIMPLE, nicknames, 2);
        model.getBoard().setup();
        assertFalse(model.isGameOver());
    }

    @Test
    public void getterTest(){
        String[] nicknames = new String[2];
        nicknames[0] = "player1";
        nicknames[1] = "player2";
        GameMode m;
        int n;

        model = new Model(GameMode.EXPERT, nicknames, 2);
        model.getBoard().setup();

        m = model.getMode();
        n = model.getNumberOfClouds();
    }

    @Test
    public void getWinnerTest(){
        String[] nicknames = new String[2];
        nicknames[0] = "player1";
        nicknames[1] = "player2";

        model = new Model(GameMode.SIMPLE, nicknames, 2);
        model.getBoard().setup();

        model.getWinner();
        System.out.println(model.getWinner().getNickname());

    }
}
