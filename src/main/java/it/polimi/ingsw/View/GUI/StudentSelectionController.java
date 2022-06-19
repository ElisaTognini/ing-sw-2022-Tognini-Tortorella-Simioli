package it.polimi.ingsw.View.GUI;

import it.polimi.ingsw.Utils.Enums.PawnDiscColor;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

public class StudentSelectionController {

    @FXML ImageView blueStudent;
    @FXML ImageView redStudent;
    @FXML ImageView pinkStudent;
    @FXML ImageView greenStudent;
    @FXML ImageView yellowStudent;

    public void selectBlueStudent(){
        MainGUIController.getParameter().setColor(PawnDiscColor.BLUE);
    }
    public void selectRedStudent(){
        MainGUIController.getParameter().setColor(PawnDiscColor.RED);
    }
    public void selectPinkStudent(){
        MainGUIController.getParameter().setColor(PawnDiscColor.PINK);
    }
    public void selectGreenStudent(){
        MainGUIController.getParameter().setColor(PawnDiscColor.GREEN);
    }
    public void selectYellowStudent(){
        MainGUIController.getParameter().setColor(PawnDiscColor.YELLOW);
    }
    public void doneClicked(){
        MainGUIController.closeCardStage();
    }
}
