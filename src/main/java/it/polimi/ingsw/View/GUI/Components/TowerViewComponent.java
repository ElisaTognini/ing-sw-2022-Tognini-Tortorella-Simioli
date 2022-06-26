package it.polimi.ingsw.View.GUI.Components;

import it.polimi.ingsw.Utils.Enums.TowerColor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Class TowerViewComponent is used to handle towers in GUI.
 *
 * @see ImageView
 * */
public class TowerViewComponent extends ImageView {

    TowerColor color;

    /**
     * Constructor TowerViewComponent creates a new TowerViewComponent instance, specifying
     * the color of the tower.
     *
     * @param color of type TowerColor - tower color.
     * */
    public TowerViewComponent(TowerColor color){
        //add tower image & resize
        this.color = color;
        setTowerImage(color);
        this.setFitHeight(40);
        this.setFitWidth(30);
    }

    /**
     * Method setTowerImage assigns to a tower an image with its color,
     * which is passed as parameter.
     *
     * @param color of type TowerColor - tower color.
     * */
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
