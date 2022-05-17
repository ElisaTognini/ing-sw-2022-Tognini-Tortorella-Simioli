package it.polimi.ingsw.tests;

import it.polimi.ingsw.Model.BoardClasses.BoardExpert;
import it.polimi.ingsw.Controller.Controller;
import it.polimi.ingsw.Utils.Enums.GameMode;
import it.polimi.ingsw.Utils.Enums.PawnDiscColor;
import it.polimi.ingsw.Model.Expert.CardManager;
import it.polimi.ingsw.Model.Expert.CharacterCardTemplate;
import it.polimi.ingsw.Model.Expert.Parameter;
import it.polimi.ingsw.Model.Model;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

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

        param.setColor(PawnDiscColor.YELLOW);
        param.setIslandID(2);

        if (controller.getExpertModeController().useCharacterCard("player1", param, 1)) {
            if (!boardExpert.isActionForbidden(1, param, "player1"))
                boardExpert.useCard(param, "player1", 1);

        }
    }
}
