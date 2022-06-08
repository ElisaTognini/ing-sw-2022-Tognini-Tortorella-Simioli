package it.polimi.ingsw.View.GUI.Components;

import it.polimi.ingsw.Utils.Enums.PawnDiscColor;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

public class Student extends Circle {
    private PawnDiscColor color;

    public Student(PawnDiscColor studentColor){
        this.color = studentColor;
        colorSetter(color);
    }

    private void colorSetter(PawnDiscColor color){
        switch(color){
            case YELLOW:
                this.setFill(new ImagePattern(new Image("/student_yellow.png")));
                break;
            case GREEN:
                this.setFill(new ImagePattern(new Image("/student_green.png")));
                break;
            case RED:
                this.setFill(new ImagePattern(new Image("/student_red.png")));
                break;
            case PINK:
                this.setFill(new ImagePattern(new Image("/student_pink.png")));
                break;
            case BLUE:
                this.setFill(new ImagePattern(new Image("/student_blue.png")));
                break;
        }

    }
}
