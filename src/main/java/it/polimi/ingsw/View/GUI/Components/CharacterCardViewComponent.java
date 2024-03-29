package it.polimi.ingsw.View.GUI.Components;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Class CharacterCardViewComponent is used to handle character cards in GUI.
 *
 * @see ImageView
 * */
public class CharacterCardViewComponent extends ImageView {

    private int id;

    /**
     * Constructor CharacterCardViewComponent creates a new CharacterCardViewComponent instance.
     *
     * @param id of type int - character card id.
     * */
    public CharacterCardViewComponent(int id){
        this.id = id;
        setCharImage(id);
        setOnMouseEntered(mouseEvent -> {
            setFitHeight(140);
            setFitWidth(105);
        });
        setOnMouseExited(mouseEvent -> {
            setFitHeight(120);
            setFitWidth(85);
        });
    }

    /**
     * Method setCharImage associates an image to each character card,
     * based on the parameter indicating the id of the card.
     *
     * @param id of type int - character card id.
     * */
    private void setCharImage(int id) {
        switch (id){
            case 1:
                this.setImage(new Image("/CarteTOT_front.jpg"));
                break;
            case 2:
                this.setImage(new Image("/CarteTOT_front12.jpg"));
                break;
            case 3:
                this.setImage(new Image("/CarteTOT_front2.jpg"));
                break;
            case 4:
                this.setImage(new Image("/CarteTOT_front3.jpg"));
                break;
            case 5:
                this.setImage(new Image("/CarteTOT_front4.jpg"));
                break;
            case 6:
                this.setImage(new Image("/CarteTOT_front5.jpg"));
                break;
            case 7:
                this.setImage(new Image("/CarteTOT_front6.jpg"));
                break;
            case 8:
                this.setImage(new Image("/CarteTOT_front7.jpg"));
                break;
            case 9:
                this.setImage(new Image("/CarteTOT_front8.jpg"));
                break;
            case 10:
                this.setImage(new Image("/CarteTOT_front9.jpg"));
                break;
            case 11:
                this.setImage(new Image("/CarteTOT_front10.jpg"));
                break;
            case 12:
                this.setImage(new Image("/CarteTOT_front11.jpg"));
                break;
        }
        this.setFitHeight(120);
        this.setFitWidth(85);
    }

    /**
     * Getter method getCardID returns the id of this character card.
     *
     * @return int - character card id.
     * */
    public int getCardId() {
        return id;
    }
}
