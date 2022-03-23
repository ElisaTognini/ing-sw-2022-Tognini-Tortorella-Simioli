package it.polimi.ingsw.Enums;

public enum TurnFlow {
    BEGINS_TURN,
    PICKS_ASSISTANT_CARD,
    MOVES_STUDENTS,
    CHOOSE_CHARACTER_CARD,
    PICKS_CLOUD,
}

/*the choice of a character card can happen at any moment during the turn,
* of course it is only legitimate if expert rules are being used (the usage of this enum differentiates with
* the board strategy pattern)*/

