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
import java.util.ArrayList;
import java.util.Locale;
import java.util.Observer;
import java.util.Scanner;

public class CLI extends View implements Observer {

    private Parser parser;
    private Thread thread;

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
    public void setMode(GameModeMessage mode){
        parser.setMode(mode.getMode());
        thread.start();
    }
}
