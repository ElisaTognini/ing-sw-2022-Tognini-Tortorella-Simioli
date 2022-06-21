package it.polimi.ingsw.View.GUI;

import it.polimi.ingsw.Utils.Enums.PawnDiscColor;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class CollectProfessorsController {

    @FXML Button doneButton;
    @FXML ImageView yellowProf;
    @FXML ImageView redProf;
    @FXML ImageView blueProf;
    @FXML ImageView greenProf;
    @FXML ImageView pinkProf;

    Set<PawnDiscColor> colorSet = new HashSet<PawnDiscColor>();

    public void doneClicked(){
        ArrayList<PawnDiscColor> professors = new ArrayList<>();
        MainGUIController.closeCardStage();
        professors.addAll(colorSet);
        MainGUIController.getParameter().setColorArrayList(professors);
    }

    public void yellowProfClicked(){
        colorSet.add(PawnDiscColor.YELLOW);
    }

    public void redProfClicked(){
        colorSet.add(PawnDiscColor.RED);
    }

    public void greenProfClicked(){
        colorSet.add(PawnDiscColor.GREEN);
    }

    public void pinkProfClicked(){
        colorSet.add(PawnDiscColor.PINK);
    }

    public void blueProfClicked(){
        colorSet.add(PawnDiscColor.BLUE);
    }

}
