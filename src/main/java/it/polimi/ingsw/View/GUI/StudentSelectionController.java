package it.polimi.ingsw.View.GUI;

import it.polimi.ingsw.Utils.Enums.PawnDiscColor;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

public class StudentSelectionController {

    @FXML ImageView blueStudent;
    @FXML ImageView redStudent;
    @FXML ImageView pinkStudent;
    @FXML ImageView greenStudent;
    @FXML ImageView yellowStudent;
    @FXML Button doneButton;

    public void selectBlueStudent(){
        MainGUIController.getParameter().setColor(PawnDiscColor.BLUE);
        doneButton.setVisible(true);

    }
    public void selectRedStudent(){
        MainGUIController.getParameter().setColor(PawnDiscColor.RED);
        doneButton.setVisible(true);
    }
    public void selectPinkStudent(){
        MainGUIController.getParameter().setColor(PawnDiscColor.PINK);
        doneButton.setVisible(true);
    }
    public void selectGreenStudent(){
        MainGUIController.getParameter().setColor(PawnDiscColor.GREEN);
        doneButton.setVisible(true);

    }
    public void selectYellowStudent(){
        MainGUIController.getParameter().setColor(PawnDiscColor.YELLOW);
        doneButton.setVisible(true);
    }
    public void doneClicked(){
        MainGUIController.closeCardStage();
    }
}
