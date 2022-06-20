package it.polimi.ingsw.View.GUI;

public class ExtraStepsSelectionController {

    public void clickedButtonOne(){
        MainGUIController.getParameter().setMoves(1);
        MainGUIController.closeCardStage();
    }

    public void clickedButtonTwo(){
        MainGUIController.getParameter().setMoves(2);
        MainGUIController.closeCardStage();
    }
}
