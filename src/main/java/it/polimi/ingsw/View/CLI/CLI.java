package it.polimi.ingsw.View.CLI;

import it.polimi.ingsw.Utils.NetMessages.*;
import java.util.*;

/** class CLI displays current game state, errors, changes in turn and starting of matches,
 * as well as all the game components, via command line */

public class CLI extends View implements Observer {

    private Parser parser;
    private Thread thread;
    private String[][] board;
    private String nickname;
    private String currentPlayer;
    private ArrayList<String> cardsPlayed = new ArrayList<>();

    /** constructor for class CLI, sets up parser to run on a parallel thread and prints
     * game logo
     * @see Parser*/
    public CLI(){
        parser = new Parser();
        thread = new Thread(parser);
        System.out.println(AnsiColors.MAGENTA + AnsiColors.COOL_TITLE + AnsiColors.ANSI_RESET);
    }

    /** override for super class View's displaySetupMessage method.
     * This method coordinates the setup phase of the game by requesting information
     * based on the received message.
     * @see View*/
    @Override
    public void displaySetupMessage(SetupServerMessage message){
        if (message.getSetupServerMessage().equals(CustomMessage.welcomeMessage)) {
            System.out.println(AnsiColors.BLUE + message.getSetupServerMessage() + AnsiColors.ANSI_RESET);
        }
        else if(message.getSetupServerMessage().equals(CustomMessage.requestNumberOfPlayers)){
            System.out.println("\n" + message.getSetupServerMessage());
            parseNumberOfPlayers();
        }
        else if(message.getSetupServerMessage().equals(CustomMessage.requestGameMode)){
            System.out.println("\n" + message.getSetupServerMessage());
            parseGameMode();
        }
        else if(message.getSetupServerMessage().equals(CustomMessage.askNickname)){
            System.out.println(AnsiColors.ANSI_YELLOW + "\n" + message.getSetupServerMessage() + AnsiColors.ANSI_RESET);
            parseNickname();
        }
    }

    /** this method is used to parse the number of players of the next match. It is only called
     * if the player is the first to connect to an empty lobby, as it is evoked in response to
     * a server message via View class's update method. It then notifies NetworkHandler class
     *      * so that the message containing the requested information can be sent.
     * @see View
     * @see Observer
     * @see Observable*/
    private void parseNumberOfPlayers() {
        Scanner scanner = new Scanner(System.in);
        int i = scanner.nextInt();
        BaseUserMessage message = new BaseUserMessage();
        message.setNumberOfPlayers(i);
        setChanged();
        notifyObservers(message);
    }

    /**this method is used to parse the GameMode of the next match. It is only called
     * if the player is the first to connect to an empty lobby, as it is evoked in response to
     * a server message via View class's update method. It then notifies NetworkHandler class
     * so that the message containing the requested information can be sent.
     * @see View
     * @see Observer
     * @see Observable*/
    private void parseGameMode(){
        Scanner scanner = new Scanner(System.in);
        String mode = scanner.nextLine();
        BaseUserMessage message = new BaseUserMessage();
        message.setGameMode(mode);
        setChanged();
        notifyObservers(message);
    }

    /** this method requests the player's nickname, and it then notifies NetworkHandler class
     * so that the message containing the requested information can be sent.
     * @see Observable*/
    private void parseNickname(){
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();
        BaseUserMessage message = new BaseUserMessage();
        message.setNickname(name);
        setChanged();
        notifyObservers(message);
    }

    /** getter method for local reference to parser
     * @return parser of type Parser - user's parser
     * @see Parser*/
    public Parser getParser(){
        return parser;
    }

    /** method prints via command line interface the elements regarding islands,
     * decks and mother nature's position.
     * @param message of type ViewUpdateMessage - information about whole game state*/
    public void buildNewBoard(ViewUpdateMessage message) {
        String header = "ISLANDS\n";
        header += AnsiColors.formatDiv("a------b------b-------b--------b------b-----b---------b--------c\n");
        header += AnsiColors.formatRow("|  ID  | BLUE | GREEN | YELLOW | PINK | RED |  OWNER  | TOWERS |\n");
        header += AnsiColors.formatDiv("d------e------e-------e--------e------e-----e---------e--------f\n");
        System.out.print(header);

        for (String island : message.getIslands()) {
            String owner;
            String towers = String.valueOf(0);
            String[] y = island.split(" ");
            if(y.length < 7) owner = "none";
            else{
                owner = y[6];
                towers = y[7];
            }
            String str1 = String.format("| %4s | %4s | %5s | %6s | %4s | %3s | %7s | %6s |", y[0], y[1], y[2], y[3], y[4], y[5], owner, towers);
            System.out.print(String.format(str1) + "\n");
        }
        System.out.println(AnsiColors.formatDiv("g------h------h-------h--------h------h-----h---------h--------i"));
        System.out.print("\n");
        System.out.println("Mother Nature is currently on island: " + message.getMnPosition());
        System.out.print("\n");
        printClouds(message);
        System.out.print("\n");
        printDeck(message.getDecks());
        System.out.print("\n");
        printSchoolboard(message);
        System.out.print("\n");
        System.out.println("ASSISTANT CARD PLAYED: ");
        for(String s : cardsPlayed){
            System.out.print(s);
        }
        System.out.print("\n");
    }

    /** this method prints via CLI the students on clouds and their IDs
     * @param message of type ViewUpdateMessage - information about whole game state*/
        public void printClouds(ViewUpdateMessage message){
            System.out.println("CLOUDS: ");
            for(String cloud: message.getClouds()){
                StringBuilder stringBuilder = new StringBuilder();
                String[] c = cloud.split(" ");
                for(int i = 0; i<c.length; i++){
                    stringBuilder.append("| ").append(c[i]).append(" ");
                }
                stringBuilder.append("| ");
                System.out.println(stringBuilder);
            }
        }

        /** this method prints the player's deck and all information regarding owned
         * assistant cards
         * @param decks of type ArrayList<String> - match's decks*/
        public void printDeck(ArrayList<String> decks){
            System.out.println("Assistant cards in your deck:");
            for(String deck: decks){
                String[] d = deck.split(" ");
                if(d[0].equals(nickname)){
                    StringBuilder stringBuilder1 = new StringBuilder();
                    StringBuilder stringBuilder2 = new StringBuilder();
                    stringBuilder1.append("card ID:                 ");
                    stringBuilder2.append("Mother Nature movements: ");

                    for(int i = 1; i<d.length; i++){
                        if(i%2==0) stringBuilder2.append("| ").append(d[i]).append(" ");
                        else stringBuilder1.append("| ").append(d[i]).append(" ");
                    }
                    stringBuilder1.append("| \n");
                    stringBuilder2.append(" | \n");
                    stringBuilder1.append(stringBuilder2);
                    System.out.println(stringBuilder1);
                }
            }
            System.out.println("current player: " + currentPlayer);
    }

    /** this method prints all school boards of players participating in the
     * match.
     * @param message of type ViewUpdateMessage - information about whole game state*/
    public void printSchoolboard(ViewUpdateMessage message) {

        for (String sb : message.getSchoolboards()) {

            String[] s = sb.split("\n");

            System.out.println(s[0] + "'S SCHOOLBOARD");

            String header = "ENTRANCE\n";
            header += AnsiColors.formatDiv("a------b-------b--------b------b-----c\n");
            header += AnsiColors.formatRow("| BLUE | GREEN | YELLOW | PINK | RED |\n");
            header += AnsiColors.formatDiv("d------e-------e--------e------e-----f\n");
            System.out.print(header);

            String[] e = s[1].split(" ");
            String str1 = String.format("| %4s | %5s | %6s | %4s | %3s |", e[2], e[3], e[1], e[0], e[4]);
            System.out.print(String.format(str1) + "\n");
            System.out.println(AnsiColors.formatDiv("g------h-------h--------h------h-----i"));

            header = "DINING ROOM\n";
            header += AnsiColors.formatDiv("a------b-------b--------b------b-----c\n");
            header += AnsiColors.formatRow("| BLUE | GREEN | YELLOW | PINK | RED |\n");
            header += AnsiColors.formatDiv("d------e-------e--------e------e-----f\n");
            System.out.print(header);

            e = s[2].split(" ");
            str1 = String.format("| %4s | %5s | %6s | %4s | %3s |", e[2], e[3], e[1], e[0], e[4]);
            System.out.print(String.format(str1) + "\n");
            System.out.println(AnsiColors.formatDiv("g------h-------h--------h------h-----i"));

            System.out.println("PROFESSORS: ");
            StringBuilder stringBuilder = new StringBuilder();
            e = s[3].split(" ");
            for (String value : e) {
                stringBuilder.append("| ").append(value).append(" ");
            }
            stringBuilder.append("| ");
            System.out.println(stringBuilder);

            System.out.println("TOWER SECTION: ");
            stringBuilder.setLength(0);
            e = s[4].split(" ");
            for (String value : e) {
                stringBuilder.append("| ").append(value).append(" ");
            }
            stringBuilder.append("| ");
            System.out.println(stringBuilder);
            System.out.print("\n");
        }
    }

    /** this method prints expert features (character cards, coins and no entry tiles on islands)
     * as well as calling buildNewBoard method so that base features can also be displayed
     * @param message of type ExpertViewUpdateMessage - information about whole game state including expert features*/
    public void buildNewBoardExpert(ExpertViewUpdateMessage message){
        System.out.println("------------EXPERT FEATURES------------");
        System.out.print("ISLANDS WITH NO ENTRY TILES: ");
        StringBuilder stringBuilder = new StringBuilder();
        for (Integer islandID : message.getIslandsNETiles()) {
            stringBuilder.append(String.valueOf(islandID)).append(" |");
            stringBuilder.append(" ");
        }
        System.out.println(stringBuilder);
        System.out.println("\n");
        printCoinCounter(message.getCoinCounters());
        System.out.println("\n");
        printExtractedCards(message.getExtractedCharCards());
        System.out.println("\n");
        buildNewBoard(message.getViewUpdate_base());

    }

    /** method prints all information about extracted character cards
     * (description, cost, and other elements if present) on command line.
     * @param extractedCards of type ArrayList<String> - all information about the cards*/
    public void printExtractedCards(ArrayList<String> extractedCards){
        StringBuilder str = null;
        ArrayList<String> descriptions = new ArrayList<>();
        String header = "CHARACTER CARDS\n";
        header += AnsiColors.formatDiv("a------b------b------------------------------------------c\n");
        header += AnsiColors.formatRow("|  ID  | COST |      students on card (if required)      |\n");
        header += AnsiColors.formatDiv("d------e------e------------------------------------------f\n");
        System.out.print(header);
        for(String card: extractedCards) {
            String[] c = card.split("-");
            descriptions.add(c[2]);
            str = new StringBuilder();
            if (c.length > 3) {
                for (int i = 3; i < c.length; i++) str.append(c[i]).append(" ");
            } else
                str = new StringBuilder(" ");
            String str1 = String.format("| %4s | %4s | %40s |", c[0], c[1], str);
            System.out.print(String.format(str1) + "\n");
            str.setLength(0);
            }
        System.out.println(AnsiColors.formatDiv("g------h------h------------------------------------------i\n"));
        System.out.println("Descriptions: ");
        for(String d : descriptions) System.out.println("- " + d);
    }

    /** this method prints all information about all players and the amount of coins they own.
     * @param coinCounters of type ArrayList<String> contains all information about coins and their owners*/
    public void printCoinCounter(ArrayList<String> coinCounters){
        System.out.println("COINS ");
        for(String counter : coinCounters){
            String[] c = counter.split(" ");
            if(Objects.equals(c[1], String.valueOf(1))) System.out.println(c[0] + " has " + c[1] + " coin");
            else System.out.println(c[0] + " has " + c[1] + " coins");
        }
    }

    /** this method prints player's rule
     * misuse and errors in red, basing off the corresponding type of BaseServerMessage received
     * It overrides super class's method displayError.
     * @param message of type BaseServerMessage contains the type of error*/
    @Override
    public void displayError(BaseServerMessage message){
        System.out.println(AnsiColors.ANSI_RED + "\n" + message.getMessage() + AnsiColors.ANSI_RESET);
        if(message.getMessage().equals(CustomMessage.closingConnection)){
            System.out.println("thank you for playing!");
            System.exit(0);
        }
    }

    /** this message prints a notification for the start of a new round
     * on CLI interface. It overrides super class's method displayNewRoundMessage.
     * @param message of type NewRoundMessage contains the message printed on screen*/
    @Override
    public void displayNewRoundMessage(NewRoundMessage message){
        cardsPlayed.clear();
        System.out.println(CustomMessage.startNewRound);
    }


    /** this method prints a notification for the start of a new round
     * on CLI interface. It overrides super class's method displayTurnChange.
     * @param message of type TurnChangeMessage contains the message printed on screen*/
    @Override
    public void displayTurnChange(TurnChangeMessage message){
        System.out.println("Now playing " + message.getCurrentPlayer());
        currentPlayer = message.getCurrentPlayer();
    }

    /** this method shows the victory screen when the game is over and displays the
     * name of the winner on CLI interface.
     *It overrides super class's displayEndOfGame method.
     * @param message of type EndGameMessage contains the nickname of the winner of this match*/
    @Override
    public void displayEndOfGame(EndGameMessage message){
        System.out.println("Game over! Congrats to " + message.getWinner() + " who won the game");
    }

    /** this method prints a new representation of the current state of the gameboard,
     * it overrides super class's updateGameBoard method.
     * @param message of type ViewUpdateMessage - information about whole game state
     * */
    @Override
    public void updateGameBoard(ViewUpdateMessage message){
        buildNewBoard(message);
    }

    /** this method prints a new representation of the current state of the gameboard,
     * including expert features.
     * it overrides super class's updateGameBoardExpert method.
     * @param message of type ExpertViewUpdateMessage - information about whole game state
     * */
    @Override
    public void updateGameBoardExpert(ExpertViewUpdateMessage message){
        buildNewBoardExpert(message);
    }

    /** this method stores the gameMode which allows the methods of this class
     * to differentiate information to display based on the game mode of this match.
     * it overrides super class's setMode method
     * @param mode of type GameMode - the game mode for this match*/
    @Override
    public void setMode(GameModeMessage mode){
        parser.setMode(mode.getMode());
        thread.start();
    }

    /** this method stores the player's nickname to aid with
     * printing everything correctly.
     * it overrides super class's setNickname method
     * @param nick of type NicknameMessage contains this player's nickname*/
    @Override
    public void setNickname(NicknameMessage nick){
        this.nickname = nick.getNickname();
    }

    /** this message stores the assistant cards played for this round which are then displayed so that
     * all players can predict Mother Nature's path.
     * it overrides super class's setPlayedCard method
     * @param message of type PlayedCardMessage contains information about played assstant card*/
    @Override
    public void setPlayedCard(PlayedCardMessage message){
        String stringBuilder = message.getOwner() + " " +
                "played card " + message.getCardID() +
                " which has power factor " + message.getPowerFactor() +
                "\n";

        cardsPlayed.add(stringBuilder);
    }
}
