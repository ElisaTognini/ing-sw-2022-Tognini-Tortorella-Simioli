package it.polimi.ingsw.View.GUI.Components;

import it.polimi.ingsw.Utils.Enums.PawnDiscColor;
import it.polimi.ingsw.View.GUI.MainGUIController;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

public class StudentViewComponent extends ImageView {
    private PawnDiscColor color;
    private String source;

    public StudentViewComponent(PawnDiscColor studentColor, String source){
        this.color = studentColor;
        this.source = source;
        colorSetter(color);
        this.setOnMouseEntered(mouseEvent -> {
            setFitHeight(30);
            setFitWidth(30);

        });
        this.setOnMouseExited(mouseEvent -> {
            setFitHeight(15);
            setFitWidth(15);
        });
        this.setOnMouseClicked(mouseEvent -> {
            MainGUIController.setColor(studentColor);
            MainGUIController.setStudentSource(source);
        });
    }

    private void colorSetter(PawnDiscColor color){
        this.setStyle("-fx-border-color: #FFFFFF");
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
        this.setFitHeight(15);
        this.setFitWidth(15);
        this.setStyle("-fx-border-color: #FFFFFF");
    }
}
