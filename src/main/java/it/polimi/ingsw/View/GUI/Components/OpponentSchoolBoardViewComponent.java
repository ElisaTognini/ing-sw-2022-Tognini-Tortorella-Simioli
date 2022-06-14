package it.polimi.ingsw.View.GUI.Components;

import it.polimi.ingsw.Utils.Enums.PawnDiscColor;
import it.polimi.ingsw.Utils.Enums.TowerColor;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class OpponentSchoolBoardViewComponent extends GridPane {

    private Label nickLabel = new Label();
    private String nickname;
    private Label pinkStudents = new Label();
    private Label blueStudents = new Label();
    private Label greenStudents = new Label();
    private Label redStudents = new Label();
    private Label yellowStudents = new Label();
    private Label towerNumber = new Label();

    public OpponentSchoolBoardViewComponent(String nickname){
        this.nickname = nickname;
        setPadding(new Insets(5, 8, 5, 8));
        setHgap(4);
        setVgap(3);
        this.setBackground(new Background(new BackgroundFill(Color.rgb(122, 101, 230, 0.5), new CornerRadii(0), new Insets(0))));
        nickLabel.setText(nickname);
        nickLabel.setFont(Font.font("ink free", 20));
        pinkStudents.setFont(Font.font("ink free", 13));
        blueStudents.setFont(Font.font("ink free", 13));
        redStudents.setFont(Font.font("ink free", 13));
        yellowStudents.setFont(Font.font("ink free", 13));
        greenStudents.setFont(Font.font("ink free", 13));
        towerNumber.setFont(Font.font("ink free", 13));
        add(nickLabel, 0, 0);
        StudentViewComponent placeHolder = new StudentViewComponent(PawnDiscColor.PINK, "opponent");
        placeHolder.setFitHeight(15);
        placeHolder.setFitWidth(15);
        add(placeHolder, 0, 1);
        add(pinkStudents, 1, 1);
        placeHolder = new StudentViewComponent(PawnDiscColor.BLUE, "opponent");
        placeHolder.setFitHeight(15);
        placeHolder.setFitWidth(15);
        add(placeHolder, 0, 2);
        add(blueStudents, 1, 2);
        placeHolder = new StudentViewComponent(PawnDiscColor.GREEN, "opponent");
        placeHolder.setFitHeight(15);
        placeHolder.setFitWidth(15);
        add(placeHolder, 0, 3);
        add(greenStudents, 1, 3);
        placeHolder = new StudentViewComponent(PawnDiscColor.RED, "opponent");
        placeHolder.setFitHeight(15);
        placeHolder.setFitWidth(15);
        add(placeHolder, 0, 4);
        add(redStudents, 1, 4);
        placeHolder = new StudentViewComponent(PawnDiscColor.YELLOW, "opponent");
        placeHolder.setFitHeight(15);
        placeHolder.setFitWidth(15);
        add(placeHolder, 0, 5);
        add(yellowStudents, 1, 5);
        Label tower = new Label("Towers: ");
        tower.setFont(Font.font("ink free", 12));
        add(tower, 0, 6);
        add(towerNumber, 1, 6);
    }

    public String getNickname() {
        return nickname;
    }

    public void showPinkStudents(String entr, String dr){
        pinkStudents.setText(" entrance: " + entr + " dining room: " + dr);
    }

    public void showBlueStudents(String entr, String dr){
        blueStudents.setText(" entrance: " + entr + " dining room: " + dr);
    }

    public void showGreenStudents(String entr, String dr){
        greenStudents.setText(" entrance: " + entr + " dining room: " + dr);
    }

    public void showYellowStudents(String entr, String dr){
        yellowStudents.setText(" entrance: " + entr + " dining room: " + dr);
    }

    public void showRedStudents(String entr, String dr){
        redStudents.setText(" entrance: " + entr + " dining room: " + dr);
    }

    public void showTowerNumber(String num){
        towerNumber.setText(num);
    }
}
