package it.polimi.ingsw.View.GUI.Components;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class PlayedCardLabel extends Label {

    private String opponentNickname;

    public PlayedCardLabel(String nickname){
        this.opponentNickname = nickname;
        setFont(Font.font("Ink Free", 12));
        setTextFill(new Color(0.5, 1, 0.97, 1));
    }

    public String getOpponentNickname() {
        return opponentNickname;
    }

    public void showPlayedCard(String cardID, String mnMovements){
        setText(opponentNickname + " played card " + cardID + " with power factor " + mnMovements);
    }
}
