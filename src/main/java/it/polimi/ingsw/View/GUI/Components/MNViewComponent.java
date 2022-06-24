package it.polimi.ingsw.View.GUI.Components;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Class MNViewComponent is used to handle mother nature in GUI.
 *
 * @see ImageView
 * */
public class MNViewComponent extends ImageView {

    /**
     * Constructor MNViewComponent creates a new MNViewComponent instance.
     * */
    public MNViewComponent(){
        this.setImage(new Image("/mother_nature.png"));
        this.setFitWidth(20);
        this.setFitHeight(20);
    }
}
