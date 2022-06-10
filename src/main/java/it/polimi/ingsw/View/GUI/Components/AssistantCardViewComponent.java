package it.polimi.ingsw.View.GUI.Components;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class AssistantCardViewComponent extends ImageView {

    private int cardID;

    public AssistantCardViewComponent(int id){
        this.cardID = id;
        cardSetter(id);
    }

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
        this.setFitHeight(70);
        this.setFitWidth(35);
    }

}
