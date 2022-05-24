package it.polimi.ingsw.View;

import it.polimi.ingsw.Utils.NetMessages.*;
import java.util.*;

public class CLI extends View implements Observer {

    private Parser parser;
    private Thread thread;
    private String[][] board;
    private String nickname;

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
        System.out.println("\nMother Nature is currently on island: " + message.getMnPosition());

        printClouds(message);

        printDeck(message.getDecks());

        printSchoolboard(message);

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
                    stringBuilder1.append("card ID: " );
                    stringBuilder2.append("Mother Nature movements: ");

                    for(int i = 1; i<d.length; i++){
                        if(i%2==0) stringBuilder2.append("| ").append(d[i]).append(" ");
                        else stringBuilder1.append("| ").append(d[i]).append(" ");
                    }
                    stringBuilder1.append("| \n");
                    stringBuilder2.append("| \n");
                    stringBuilder1.append(stringBuilder2);
                    System.out.println(stringBuilder1);
                }
            }
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
            e = s[4].split(" ");
            for (String value : e) {
                stringBuilder.append("| ").append(value).append(" ");
            }
            stringBuilder.append("| ");
            System.out.println(stringBuilder);
        }

        System.out.println("\n");
    }

    public void buildNewBoardExpert(ExpertViewUpdateMessage message){
        System.out.println("EXPERT FEATURES:\n");
        System.out.println(" - Islands with no entry tiles: ");
        StringBuilder stringBuilder = new StringBuilder();
        for (String island : message.getViewUpdate_base().getIslands()) {
            String[] i = island.split(" ");
            if(i.length == 8) stringBuilder.append("| ").append(i[7]).append(" ");
        }
        printCoinCounter(message.getCoinCounters());

        buildNewBoard(message.getViewUpdate_base());

    }

    public void printCoinCounter(ArrayList<String> coinCounters){
        System.out.println(" - Coins: ");
        for(String counter : coinCounters){
            String[] c = counter.split(" ");
            if(Objects.equals(c[1], String.valueOf(1))) System.out.println(c[0] + " has " + c[1] + "coin");
            else System.out.println(c[0] + " has " + c[1] + "coins");
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
        System.out.println(CustomMessage.startNewRound);
    }

    @Override
    public void displayTurnChange(TurnChangeMessage message){
        System.out.println("Your turn is over. Now playing " + message.getCurrentPlayer() );
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
}
