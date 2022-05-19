package it.polimi.ingsw.View;

import it.polimi.ingsw.Client.Client;
import it.polimi.ingsw.Utils.NetMessages.*;

import java.rmi.ServerError;
import java.util.ArrayList;
import java.util.Observer;
import java.util.Scanner;

public class CLI extends View implements Observer {

    public CLI(){
        /* will print cool title!! */
        System.out.println(AnsiColors.MAGENTA + AnsiColors.COOL_TITLE + AnsiColors.ANSI_RESET);
    }

    @Override
    public void displaySetupMessage(SetupServerMessage message){
        if (message.getSetupServerMessage().equals(CustomMessage.welcomeMessage)) {
            System.out.println(AnsiColors.BLUE + message.getSetupServerMessage() + AnsiColors.ANSI_RESET);
        }
        else if(message.getSetupServerMessage().equals(CustomMessage.requestNumberOfPlayers)){
            System.out.println(message.getSetupServerMessage());
            parseNumberOfPlayers();
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

    /* this method prints errors in red, basing off the corresponding type of BaseServerMessage received */
    @Override
    public void displayError(BaseServerMessage message){
        System.err.println(message.getMessage());
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
}
