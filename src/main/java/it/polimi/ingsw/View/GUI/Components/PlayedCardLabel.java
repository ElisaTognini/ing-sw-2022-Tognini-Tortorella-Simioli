package it.polimi.ingsw.View.GUI.Components;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * Class PlayedCardLabel is used to show which assistant card has been chosen by each player in every turn.
 * A new label appears every time a player chooses an assistant card.
 *
 * @see Label
 * */
public class PlayedCardLabel extends Label {

    private String opponentNickname;

    /**
     * Constructor PlayedCardLabel creates a new PlayedCardLabel instance, associating tis label
     * to the player whose nickname is given as parameter.
     *
     * @param nickname of type String - nickname of the player who chose an assistant card.
     * */
    public PlayedCardLabel(String nickname){
        this.opponentNickname = nickname;
        setFont(Font.font("Ink Free", 12));
        setTextFill(new Color(0.5, 1, 0.97, 1));
    }

    /**
     * Method showPlayedCard sets text in this label, indicating the nickname of the player and the id of the
     * assistant card they have chosen, in addition to its power factor.
     *
     * @param cardID of type String - assistant card id.
     * @param mnMovements of type String - assistant card power factor, indicating the number of steps
     *                    that mother nature will take according to the chosen assistant card.
     * */
    public void showPlayedCard(String cardID, String mnMovements){
        setText(opponentNickname + " played card " + cardID + " with power factor " + mnMovements);
    }

    /**
     * Getter method getOpponentNickname returns the nickname of the opponent who just chose an assistant card.
     *
     * @return String - opponent player nickname.
     * */
    public String getOpponentNickname() {
        return opponentNickname;
    }
}
