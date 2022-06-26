package it.polimi.ingsw.View.GUI;

import it.polimi.ingsw.Utils.Enums.PawnDiscColor;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Class CollectProfessorsController is used to handle character card 2.
 * It allows the player to select the professor(s) they want to take control of for this turn.
 * */
public class CollectProfessorsController {

    @FXML Button doneButton;
    @FXML ImageView yellowProf;
    @FXML ImageView redProf;
    @FXML ImageView blueProf;
    @FXML ImageView greenProf;
    @FXML ImageView pinkProf;

    Set<PawnDiscColor> colorSet = new HashSet<PawnDiscColor>();

    /**
     * Method doneClicked closes the selection stage when "done" button is clicked.
     * */
    public void doneClicked(){
        ArrayList<PawnDiscColor> professors = new ArrayList<>();
        MainGUIController.closeCardStage();
        professors.addAll(colorSet);
        MainGUIController.getParameter().setColorArrayList(professors);
    }

    /**
     * Method yellowProfClicked detects when the yellow professor is clicked, allowing the player to
     * take control of it during the turn in which they have chosen to use character card 2.
     * */
    public void yellowProfClicked(){
        colorSet.add(PawnDiscColor.YELLOW);
    }

    /**
     * Method redProfClicked detects when the red professor is clicked, allowing the player to
     * take control of it during the turn in which they have chosen to use character card 2.
     * */
    public void redProfClicked(){
        colorSet.add(PawnDiscColor.RED);
    }

    /**
     * Method greenProfClicked detects when the green professor is clicked, allowing the player to
     * take control of it during the turn in which they have chosen to use character card 2.
     * */
    public void greenProfClicked(){
        colorSet.add(PawnDiscColor.GREEN);
    }

    /**
     * Method pinkProfClicked detects when the pink professor is clicked, allowing the player to
     * take control of it during the turn in which they have chosen to use character card 2.
     * */
    public void pinkProfClicked(){
        colorSet.add(PawnDiscColor.PINK);
    }

    /**
     * Method blueProfClicked detects when the blue professor is clicked, allowing the player to
     * take control of it during the turn in which they have chosen to use character card 2.
     * */
    public void blueProfClicked(){
        colorSet.add(PawnDiscColor.BLUE);
    }

}
