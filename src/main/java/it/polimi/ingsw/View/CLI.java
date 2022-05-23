package it.polimi.ingsw.View;

import it.polimi.ingsw.Client.ActionMessages.AssistantCardMessage;
import it.polimi.ingsw.Client.ActionMessages.MoveStudentToDRMessage;
import it.polimi.ingsw.Client.ActionMessages.MoveStudentToIslandMessage;
import it.polimi.ingsw.Client.ActionMessages.PickCloudMessage;
import it.polimi.ingsw.Client.Client;
import it.polimi.ingsw.Model.BasicElements.AssistantCard;
import it.polimi.ingsw.Utils.Enums.PawnDiscColor;
import it.polimi.ingsw.Utils.NetMessages.*;

import java.rmi.ServerError;
import java.util.*;

public class CLI extends View implements Observer {

    private Parser parser;
    private Thread thread;
    private String[][] board;
    private final int columns = 9;
    private int rows;

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
        if(i == 2) rows = 24;
        else rows = 25;
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


    public void buildNewBoard() {
        rows = 24;
        int clouds = 2;
        if (rows > 26) clouds = 3;
        board = new String[rows][columns];
        board[0][4] = "BOARD";
        board[1][4] = "ISLANDS";
        for (int i = 2; i < 20; i = i + 15) {
            for (int j = 0; j < columns; j++) {
                if (j == 4) board[i][j] = "STUDENTS";
            }
            board[i + 1][0] = "ID";
            board[i + 1][1] = "BLUE";
            board[i + 1][2] = "GREEN";
            board[i + 1][3] = "YELLOW";
            board[i + 1][4] = "PINK";
            board[i + 1][5] = "RED";
        }
        board[3][6] = "OWNER";
        board[3][7] = "TOWERS";
        board[3][8] = "NO-ENTRY-TILES";
        for (int i = 4; i < 16; i++) {
            board[i][0] = String.valueOf(i - 4);
            for (int j = 1; j < columns; j++) {
                if (j == 6) board[i][j] = "none";
                else board[i][j] = String.valueOf(0);
            }
        }
        board[16][4] = "CLOUDS";
        int k = 18 + clouds;
        for (int i = 19; i <= (k + clouds); i++) {
            board[i][0] = String.valueOf(i - 19);
            for (int j = 1; j < 5; j++) {
                board[i][j] = String.valueOf(0);
            }
        }
        for (int i = k + 1; i < rows; i++){
            for (int j = 0; j < columns; j++) {
                if (i == k + 1 && j == 4) board[i][j] = "MOTHER-NATURE";
                else if (i == k + 2 && j == 4) board[i][j] = "POSITION";
                else if (i == k + 3 && j == 4) board[i][j] = String.valueOf(0);
                else board[i][j] = null;
            }
        }
        for(int f = 0; f<rows; f++){
            for (int j = 0; j<columns; j++){
                if(board[f][j] == null) System.out.print(String.format("%-5s%-15s|", "", "--"));
                else System.out.print(String.format("%-5s%-15s|", "", board[f][j]));

            }
            System.out.print("\n");
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
        buildNewBoard();
    }

    @Override
    public void setMode(GameModeMessage mode){
        parser.setMode(mode.getMode());
        thread.start();
    }
}
