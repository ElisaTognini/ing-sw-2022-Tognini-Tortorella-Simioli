package it.polimi.ingsw.View.GUI.Components;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

public class IslandViewComponent extends GridPane {
    public IslandViewComponent(){
        this.setVgap(2);
        this.setHgap(2);
        this.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

            }
        });
    }

}
