package it.polimi.ingsw.tests;

import it.polimi.ingsw.BoardClasses.Board;
import it.polimi.ingsw.BoardClasses.BoardExpert;
import it.polimi.ingsw.Controller.BaseActionController;
import it.polimi.ingsw.Controller.Controller;
import it.polimi.ingsw.Controller.ExpertModeController;
import it.polimi.ingsw.Enums.GameMode;
import it.polimi.ingsw.Enums.PawnDiscColor;
import it.polimi.ingsw.Expert.CardManager;
import it.polimi.ingsw.Expert.CharacterCardTemplate;
import it.polimi.ingsw.Expert.Parameter;
import it.polimi.ingsw.Model;
import it.polimi.ingsw.Player;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class ExpertModeControllerTest {

    private Controller controller;
    private Model model;

    @Test
    public void initTest(){
        String[] nicknames = new String[2];
        nicknames[0] = "player1";
        nicknames[1] = "player2";
        model = new Model(GameMode.EXPERT, nicknames, 2);
        controller = new Controller(model);
        controller.getBaseActionController().startGame();
    }

    @RepeatedTest(5)
    public void useCardTest() {
        initTest();
        CharacterCardTemplate[] cards;
        BoardExpert boardExpert = (BoardExpert) model.getBoard();
        CardManager manager = new CardManager(boardExpert);
        Parameter param = new Parameter();

        cards = new CharacterCardTemplate[3];
        cards[0] = manager.returnCard(1);
        cards[1] = manager.returnCard(2);
        cards[2] = manager.returnCard(3);

        boardExpert.setExtractedCards(cards);

        for(int i=0; i<3; i++){
            if(boardExpert.colorAvailableInEntrance("player1", PawnDiscColor.PINK))
                boardExpert.moveStudent(PawnDiscColor.PINK, "player1");
        }

        boardExpert.assignCoin("player1", PawnDiscColor.PINK);

        boardExpert.purchaseCharacterCard("player1", 1);
        param.setColor(PawnDiscColor.YELLOW);
        param.setIslandID(2);

        if (controller.getExpertModeController().useCharacterCard("player1", param, 1)) {
            if (!boardExpert.isActionForbidden(1, param, "player1"))
                boardExpert.useCard(param, "player1", 1);
        }
    }
}
