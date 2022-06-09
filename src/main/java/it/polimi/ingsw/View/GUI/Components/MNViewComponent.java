package it.polimi.ingsw.View.GUI.Components;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MNViewComponent extends ImageView {

    public MNViewComponent(){
        this.setImage(new Image("/mother_nature.png"));
        this.setFitWidth(20);
        this.setFitHeight(20);
    }
}
