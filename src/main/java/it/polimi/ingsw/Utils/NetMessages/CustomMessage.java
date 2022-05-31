package it.polimi.ingsw.Utils.NetMessages;

public class CustomMessage {
    public static String welcomeMessage = "Welcome to Eriantys!\n";
    public static String closingConnection = "Connection closed, bye!\n";
    public static String requestNumberOfPlayers = "How many players? [2|3]\n";
    public static String requestGameMode = "In which mode would you like to play? [Simple|Expert]\n";
    public static String askNickname = "Enter your nickname here: ";
    public static String errorNumberOfPlayers = "Number of players must be either 2 or 3, please try again!\n";
    public static String errorGameMode = "Game mode not valid, please try again! [Simple|Expert]\n";
    public static String duplicatedNickname = "This nickname is taken, please enter a new one!\n";
    public static String invalidFormat = "Input format is not valid. Please try again.\n";
    public static String matchStarting = "Match is starting...\n";
    public static String emptyCloudError = "The cloud you picked is empty.\n";
    public static String invalidCloudIDError = "The cloud ID you provided is not valid.\n";
    public static String turnFlowError = "You cannot perform this action at this time in your turn.\n";
    public static String notYourTurnError = "You are not the player currently in turn.\n";
    public static String cardAlreadyPlayedError = "You cannot play an assistant card that has already been chosen in" +
            " this round by another player.\n";
    public static String cardNotPresentError = "The card you chose is not in your deck, please choose another one.\n";
    public static String fullDRError = "Your dining room is full for the color chosen.\n";
    public static String lastRound = "Now playing your last round for this match!\n";
    public static String colorNotAvailableError = "No student of the color you chose are available in your entrance.\n";
    public static String allStudentsMovedError = "You already moved three students.\n";
    public static String wrongFormat = "The parameter sent was of incorrect format.\n";
    public static String notEnoughCoinsError = "You do not have enough coins for this character card.\n";

    public static String charCardNotExtractedError = "This character card does not appear to have been extracted.\n";
    public static String isLastRound = "This is the last round for this match!\n";
    public static String startNewRound = "A new round is starting!\n";
    public static String actionForbiddenError = "The action you requested cannot be performed!\n";
    public static String cardPlayed = " Card played: ";
}