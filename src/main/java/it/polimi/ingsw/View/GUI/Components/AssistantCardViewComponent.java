package it.polimi.ingsw.View.GUI.Components;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Class AssistantCardViewComponent is used to handle assistant cards in GUI.
 *
 * @see ImageView
 * */

public class AssistantCardViewComponent extends ImageView {

    private int cardID;

    /**
     * Constructor AssistantCardViewComponent creates a new AssistantCardViewComponent instance.
     *
     * @param id of type int - assistant card id.
     * */
    public AssistantCardViewComponent(int id){
        this.cardID = id;
        cardSetter(id);
        this.setOnMouseEntered(mouseEvent -> {
            setFitHeight(170);
            setFitWidth(135);
            toFront();
        });
        this.setOnMouseExited(mouseEvent -> {
            setFitHeight(110);
            setFitWidth(75);
        });
    }

    /**
     * Method cardSetter associates an image to each assistant card, based on the parameter indicating the id of the card.
     *
     * @param id of type int - assistant card id.
     * */
    public void cardSetter(int id){
        switch(id){
            case 1:
                this.setImage(new Image("/Assistente1.png"));
                break;
            case 2:
                this.setImage(new Image("/Assistente2.png"));
                break;
            case 3:
                this.setImage(new Image("/Assistente3.png"));
                break;
            case 4:
                this.setImage(new Image("/Assistente4.png"));
                break;
            case 5:
                this.setImage(new Image("/Assistente5.png"));
                break;
            case 6:
                this.setImage(new Image("/Assistente6.png"));
                break;
            case 7:
                this.setImage(new Image("/Assistente7.png"));
                break;
            case 8:
                this.setImage(new Image("/Assistente8.png"));
                break;
            case 9:
                this.setImage(new Image("/Assistente9.png"));
                break;
            case 10:
                this.setImage(new Image("/Assistente10.png"));
                break;
        }

        this.setFitHeight(110);
        this.setFitWidth(75);
    }

    /**
     * Getter method getCardID returns the id of this assistant card.
     *
     * @return int - assistant card id.
     * */
    public int getCardID() {
        return cardID;
    }

}
