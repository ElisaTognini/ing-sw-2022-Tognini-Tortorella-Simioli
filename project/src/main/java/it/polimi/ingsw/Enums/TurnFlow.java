package it.polimi.ingsw.Enums;

public enum TurnFlow {
    BEGINS_TURN,
    CARD_PICKED,
    MOVED_STUDENTS,
    //CHOOSE_CHARACTER_CARD,
    PICKED_CLOUD,
}

/*the choice of a character card can happen at any moment during the turn,
* of course it is only legitimate if expert rules are being used (the usage of this enum differentiates with
* the board strategy pattern)*/

