package it.polimi.ingsw.View.GUI.Components;

import it.polimi.ingsw.Utils.Enums.TowerColor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class TowerViewComponent extends ImageView {

    TowerColor color;

    public TowerViewComponent(TowerColor color){
        //add tower image & resize
        this.color = color;
        setTowerImage(color);
        this.setFitHeight(40);
        this.setFitWidth(30);
    }

    private void setTowerImage(TowerColor color){
        switch (color){
            case WHITE:
                this.setImage(new Image("/white_tower.png"));
                break;
            case BLACK:
                this.setImage(new Image("/black_tower.png"));
                break;
            case GREY:
                this.setImage(new Image("/grey_tower.png"));
                break;
        }
    }
}
