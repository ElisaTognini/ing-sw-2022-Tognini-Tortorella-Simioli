package it.polimi.ingsw.View.GUI;

import it.polimi.ingsw.Utils.Enums.PawnDiscColor;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import javax.swing.text.html.ImageView;
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
        MainGUIController.closeCardStage();
        MainGUIController.getParameter().getColorArrayList().addAll(colorSet);
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
