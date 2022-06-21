package it.polimi.ingsw.View.GUI;

import it.polimi.ingsw.Utils.Enums.PawnDiscColor;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

public class StudentListSelectionController {

    @FXML ImageView blueStudent;
    @FXML ImageView greenStudent;
    @FXML ImageView pinkStudent;
    @FXML ImageView yellowStudent;
    @FXML ImageView redStudent;
    @FXML Button doneButton;

    ArrayList<PawnDiscColor> colors = new ArrayList<>();

    public void addYellowStudent(){
        colors.add(PawnDiscColor.YELLOW);
        checkForSize();
    }

    public void addPinkStudent(){
        colors.add(PawnDiscColor.PINK);
        checkForSize();
    }

    public void addGreenStudent(){
        colors.add(PawnDiscColor.GREEN);
        checkForSize();
    }

    public void addRedStudent(){
        colors.add(PawnDiscColor.RED);
        checkForSize();
    }

    public void addBlueStudent(){
        colors.add(PawnDiscColor.BLUE);
        checkForSize();
    }

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

    public void doneClicked(){
        if(MainGUIController.getParameter().getColorArrayList() == null){
            MainGUIController.getParameter().setColorArrayList(colors);
        }
        else
            MainGUIController.getParameter().setColorArrayList2(colors);

        MainGUIController.closeCardStage();
    }
}
