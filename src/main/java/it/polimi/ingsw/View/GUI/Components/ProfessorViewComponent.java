package it.polimi.ingsw.View.GUI.Components;

import it.polimi.ingsw.Utils.Enums.PawnDiscColor;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Class ProfessorViewComponent is used to handle professor pawns in GUI.
 *
 * @see ImageView
 * */
public class ProfessorViewComponent extends ImageView {

    /**
     * Constructor ProfessorViewComponent creates a new ProfessorViewComponent instance.
     *
     * @param color of type PawnDiscColor - professor color.
     * */
    public ProfessorViewComponent(PawnDiscColor color){
        setColor(color);
        setFitWidth(20);
        setFitHeight(20);
    }

    /**
     * Method setColor sets a given color to a professor pawn.
     *
     * @param color of type PawnDiscColor - professor color.
     * */
    public void setColor(PawnDiscColor color){
        switch (color) {
            case PINK:
                this.setImage(new Image("/teacher_pink.png"));
                break;
            case BLUE:
                this.setImage(new Image("/teacher_blue.png"));
                break;
            case YELLOW:
                this.setImage(new Image("/teacher_yellow.png"));
                break;
            case GREEN:
                this.setImage(new Image("/teacher_green.png"));
                break;
            case RED:
                this.setImage(new Image("/teacher_red.png"));
                break;
        }
    }
}
