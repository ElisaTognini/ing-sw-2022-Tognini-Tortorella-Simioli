package it.polimi.ingsw.View.GUI;

import it.polimi.ingsw.Utils.Enums.PawnDiscColor;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

/**
 * Class StudentSelectionController is used to select student colors to add as parameters
 * when some character cards are used.
 * */
public class StudentSelectionController {

    @FXML ImageView blueStudent;
    @FXML ImageView redStudent;
    @FXML ImageView pinkStudent;
    @FXML ImageView greenStudent;
    @FXML ImageView yellowStudent;
    @FXML Button doneButton;

    /**
     * Method selectBlueStudent detects when the blue student is clicked, passing it as parameter to the character card.
     * */
    public void selectBlueStudent(){
        MainGUIController.getParameter().setColor(PawnDiscColor.BLUE);
        doneButton.setVisible(true);

    }

    /**
     * Method selectRedStudent detects when the red student is clicked, passing it as parameter to the character card.
     * */
    public void selectRedStudent(){
        MainGUIController.getParameter().setColor(PawnDiscColor.RED);
        doneButton.setVisible(true);
    }

    /**
     * Method selectPinkStudent detects when the pink student is clicked, passing it as parameter to the character card.
     * */
    public void selectPinkStudent(){
        MainGUIController.getParameter().setColor(PawnDiscColor.PINK);
        doneButton.setVisible(true);
    }

    /**
     * Method selectGreenStudent detects when the green student is clicked, passing it as parameter to the character card.
     * */
    public void selectGreenStudent(){
        MainGUIController.getParameter().setColor(PawnDiscColor.GREEN);
        doneButton.setVisible(true);
    }

    /**
     * Method selectYellowStudent detects when the yellow student is clicked, passing it as parameter to the character card.
     * */
    public void selectYellowStudent(){
        MainGUIController.getParameter().setColor(PawnDiscColor.YELLOW);
        doneButton.setVisible(true);
    }

    /**
     * Method doneClicked closes the selection stage when "done" button is clicked.
     * */
    public void doneClicked(){
        MainGUIController.closeCardStage();
    }
}
