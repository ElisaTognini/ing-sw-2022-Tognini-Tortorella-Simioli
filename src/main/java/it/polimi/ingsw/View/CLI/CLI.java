package it.polimi.ingsw.View.CLI;

import it.polimi.ingsw.Utils.NetMessages.*;
import java.util.*;

public class CLI extends View implements Observer {

    private Parser parser;
    private Thread thread;
    private String[][] board;
    private String nickname;
    private String currentPlayer;
    private ArrayList<String> cardsPlayed = new ArrayList<>();

    public CLI(){
        parser = new Parser();
        thread = new Thread(parser);
        System.out.println(AnsiColors.MAGENTA + AnsiColors.COOL_TITLE + AnsiColors.ANSI_RESET);
    }

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

    private void parseNumberOfPlayers() {
        Scanner scanner = new Scanner(System.in);
        int i = scanner.nextInt();
        BaseUserMessage message = new BaseUserMessage();
        message.setNumberOfPlayers(i);
        setChanged();
        notifyObservers(message);
    }

    private void parseGameMode(){
        Scanner scanner = new Scanner(System.in);
        String mode = scanner.nextLine();
        BaseUserMessage message = new BaseUserMessage();
        message.setGameMode(mode);
        setChanged();
        notifyObservers(message);
    }

    private void parseNickname(){
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();
        BaseUserMessage message = new BaseUserMessage();
        message.setNickname(name);
        setChanged();
        notifyObservers(message);
    }

    public Parser getParser(){
        return parser;
    }


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

    public void buildNewBoardExpert(ExpertViewUpdateMessage message){
        System.out.println("------------EXPERT FEATURES------------");
        System.out.print("ISLANDS WITH NO ENTRY TILES: ");
        StringBuilder stringBuilder = new StringBuilder();
        for (Integer islandID : message.getIslandsNETiles()) {
            stringBuilder.append(String.valueOf(islandID));
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

    public void printCoinCounter(ArrayList<String> coinCounters){
        System.out.println("COINS ");
        for(String counter : coinCounters){
            String[] c = counter.split(" ");
            if(Objects.equals(c[1], String.valueOf(1))) System.out.println(c[0] + " has " + c[1] + " coin");
            else System.out.println(c[0] + " has " + c[1] + " coins");
        }
    }

    /* this method prints errors in red, basing off the corresponding type of BaseServerMessage received */
    @Override
    public void displayError(BaseServerMessage message){
        System.out.println(AnsiColors.ANSI_RED + "\n" + message.getMessage() + AnsiColors.ANSI_RESET);
        if(message.getMessage().equals(CustomMessage.closingConnection)){
            //socket gets closed
        }
    }

    @Override
    public void displayNewRoundMessage(NewRoundMessage message){
        cardsPlayed.clear();
        System.out.println(CustomMessage.startNewRound);
    }

    @Override
    public void displayTurnChange(TurnChangeMessage message){
        System.out.println("Now playing " + message.getCurrentPlayer());
        currentPlayer = message.getCurrentPlayer();
    }

    @Override
    public void displayEndOfGame(EndGameMessage message){
        System.out.println("Game over! Congrats to " + message.getWinner() + " who won the game");
    }

    @Override
    public void updateGameBoard(ViewUpdateMessage message){
        buildNewBoard(message);
    }

    @Override
    public void updateGameBoardExpert(ExpertViewUpdateMessage message){
        buildNewBoardExpert(message);
    }

    @Override
    public void setMode(GameModeMessage mode){
        parser.setMode(mode.getMode());
        thread.start();
    }

    @Override
    public void setNickname(NicknameMessage nick){
        this.nickname = nick.getNickname();
    }

    @Override
    public void setPlayedCard(PlayedCardMessage message){
        String stringBuilder = message.getOwner() + " " +
                "played card " + message.getCardID() +
                " which has power factor " + message.getPowerFactor() +
                "\n";

        cardsPlayed.add(stringBuilder);
    }
}
