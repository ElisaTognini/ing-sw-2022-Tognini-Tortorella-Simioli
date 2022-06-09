package it.polimi.ingsw.View.GUI.Components;

import it.polimi.ingsw.Utils.Enums.PawnDiscColor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

public class StudentViewComponent extends ImageView {
    private PawnDiscColor color;

    public StudentViewComponent(PawnDiscColor studentColor){
        this.color = studentColor;
        colorSetter(color);
    }

    private void colorSetter(PawnDiscColor color){
        switch(color){
            case YELLOW:
                this.setImage(new Image("/student_yellow.png"));
                break;
            case GREEN:
                this.setImage(new Image("/student_green.png"));
                break;
            case RED:
                this.setImage(new Image("/student_red.png"));
                break;
            case PINK:
                this.setImage(new Image("/student_pink.png"));
                break;
            case BLUE:
                this.setImage(new Image("/student_blue.png"));
                break;
        }
        this.setFitHeight(25);
        this.setFitWidth(25);
        this.setStyle("-fx-border-color: #FFFFFF");
    }
}
