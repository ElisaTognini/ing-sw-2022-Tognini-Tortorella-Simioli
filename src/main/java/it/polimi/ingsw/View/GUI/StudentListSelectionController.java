package it.polimi.ingsw.View.GUI;

import it.polimi.ingsw.Utils.Enums.PawnDiscColor;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

/**
 * Class StudentListSelectionController is used to add selected students to an ArrayList, when using a character card
 * that requires it, in order to swap students between two places.
 * */
public class StudentListSelectionController {

    @FXML ImageView blueStudent;
    @FXML ImageView greenStudent;
    @FXML ImageView pinkStudent;
    @FXML ImageView yellowStudent;
    @FXML ImageView redStudent;
    @FXML Button doneButton;

    ArrayList<PawnDiscColor> colors = new ArrayList<>();

    /**
     * Method addYellowStudent adds a yellow student to the student ArrayList.
     * */
    public void addYellowStudent(){
        colors.add(PawnDiscColor.YELLOW);
        checkForSize();
    }

    /**
     * Method addPinkStudent adds a pink student to the student ArrayList.
     * */
    public void addPinkStudent(){
        colors.add(PawnDiscColor.PINK);
        checkForSize();
    }

    /**
     * Method addGreenStudent adds a green student to the student ArrayList.
     * */
    public void addGreenStudent(){
        colors.add(PawnDiscColor.GREEN);
        checkForSize();
    }

    /**
     * Method addRedStudent adds a red student to the student ArrayList.
     * */
    public void addRedStudent(){
        colors.add(PawnDiscColor.RED);
        checkForSize();
    }

    /**
     * Method addBlueStudent adds a blue student to the student ArrayList.
     * */
    public void addBlueStudent(){
        colors.add(PawnDiscColor.BLUE);
        checkForSize();
    }

    /**
     * Method checkForSize is used to ensures that the student ArrayList does not contain more that three students.
     * */
    private void checkForSize(){
        if(colors.size() >= 3){
            if(MainGUIController.getParameter().getColorArrayList() == null){
                MainGUIController.getParameter().setColorArrayList(colors);
            }
            else
                MainGUIController.getParameter().setColorArrayList2(colors);

            MainGUIController.closeCardStage();
        }
    }

    /**
     * Method doneClicked closes the selection stage when "done" button is clicked.
     * */
    public void doneClicked(){
        if(MainGUIController.getParameter().getColorArrayList() == null){
            MainGUIController.getParameter().setColorArrayList(colors);
        }
        else
            MainGUIController.getParameter().setColorArrayList2(colors);

        MainGUIController.closeCardStage();
    }
}
