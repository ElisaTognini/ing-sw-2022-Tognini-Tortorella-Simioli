package it.polimi.ingsw.View.GUI;

/**
 * Class ExtraStepsSelectionController is used to handle character card 4.
 * It allows the player who uses this card to choose to move mother nature of either one
 * or two additional islands.
 * */
public class ExtraStepsSelectionController {

    /**
     * Method clickedButtonOne detects when the "1" button is clicked, therefore allowing mother nature
     * to make one additional step.
     * */
    public void clickedButtonOne(){
        MainGUIController.getParameter().setMoves(1);
        MainGUIController.closeCardStage();
    }

    /**
     * Method clickedButtonOne detects when the "2" button is clicked, therefore allowing mother nature
     * to make two additional steps.
     * */
    public void clickedButtonTwo(){
        MainGUIController.getParameter().setMoves(2);
        MainGUIController.closeCardStage();
    }
}
