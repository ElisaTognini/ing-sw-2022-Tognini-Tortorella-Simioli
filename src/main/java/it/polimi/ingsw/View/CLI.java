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

    public CLI(){
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

    public void parseActionType(){
        Scanner scanner = new Scanner(System.in);
        int i;
        boolean flag;
        do {
            flag = false;
            System.out.println("\n time to choose an action!\n" +
                    "enter 1 if you would like to pick an assistant card\n" +
                    "enter 2 if you would like to move a student from your entrance to the dining room\n" +
                    "enter 3 if you would like to move a student from your entrance to an island\n" +
                    "enter 4 if you would like to pick a cloud tile\n");
            i = scanner.nextInt();
            if(i <= 0 || i < 4){
                flag = true;
                System.out.println(AnsiColors.ANSI_RED + "invalid action type.\n" + AnsiColors.ANSI_RESET);
            }
        }while(flag == true);
        selectAction(i);
    }

    public void parseActionTypeExpert() {
        Scanner scanner = new Scanner(System.in);
        int i;
        boolean flag;
        do {
            flag = false;
            System.out.println("\n time to choose an action!\n" +
                    "enter 1 if you would like to pick an assistant card\n" +
                    "enter 2 if you would like to move a student from your entrance to the dining room\n" +
                    "enter 3 if you would like to move a student from your entrance to an island\n" +
                    "enter 4 if you would like to pick a cloud tile\n" +
                    "enter 5 if you would like to pick a character card\n");
            i = scanner.nextInt();
            if (i <= 0 || i < 4) {
                flag = true;
                System.out.println(AnsiColors.ANSI_RED + "invalid action type.\n" + AnsiColors.ANSI_RESET);
            }
        } while (flag == true);
        selectAction(i);
    }

    private void selectAction(int i){
        switch(i){
            case 1:
                pickAssistantCard();
                break;
            case 2:
                moveStudentToDR();
                break;
            case 3:
                moveStudentToIsland();
                break;
            case 4:
                pickCloud();
                break;
            case 5:
                pickCharacterCard();
                break;
        }
    }

    /*METHODS FOR ACTION PARSING*/
    public void pickAssistantCard(){
        Scanner scanner = new Scanner(System.in);
        int i;
        boolean flag;
        System.out.println("\nenter the number of the assistant card you would like to pick.");
        do{
            flag = false;
            i = scanner.nextInt();
            if(i < 0 || i > 12){
                flag = true;
                System.out.println(AnsiColors.ANSI_RED + "choose a valid card number!\n" + AnsiColors.ANSI_RESET);
            }
        }while(flag == false);

        setChanged();
        notifyObservers(new AssistantCardMessage(i));
    }

    public void moveStudentToDR(){
        Scanner scanner = new Scanner(System.in);
        String read;
        boolean flag;
        System.out.println("\nenter the color of the student you would like to move");
        do{
            flag = false;
            read = scanner.nextLine();
            read = read.toUpperCase();
            if(!checkValidColor(read)){
                System.out.println("enter a valid color!");
                flag = true;
            }
        }while(!flag);

        setChanged();
        notifyObservers(new MoveStudentToDRMessage(PawnDiscColor.valueOf(read)));
    }

    public void moveStudentToIsland(){
        Scanner scanner = new Scanner(System.in);
        String read;
        int i;
        boolean flag;

        System.out.println("\nenter the id of the island you would like to move your student to.");
        do{
            flag = false;
            i = scanner.nextInt();
            if(i < 0 || i > 12){
                flag = true;
                System.out.println(AnsiColors.ANSI_RED + "choose a valid IslandID\n" + AnsiColors.ANSI_RESET);
            }
        }while(flag == false);

        System.out.println("now enter the color of the student you would like to move.");
        do{
            flag = false;
            read = scanner.nextLine();
            read = read.toUpperCase();
            if(!checkValidColor(read)){
                System.out.println("enter a valid color!");
                flag = true;
            }
        }while(!flag);

        notifyObservers(new MoveStudentToIslandMessage(i, PawnDiscColor.valueOf(read)));
    }

    public void pickCloud(){
        Scanner scanner = new Scanner(System.in);
        int i;
        boolean flag;
        System.out.println("enter the number of the cloud you would like to pick: ");

        do{
            flag = false;
            i = scanner.nextInt();
            if(i < 0 || i > 4){
                flag = true;
                System.out.println(AnsiColors.ANSI_RED + "choose a valid cloud number!\n" + AnsiColors.ANSI_RESET);
            }
        }while(flag == false);

        notifyObservers(new PickCloudMessage(i));
    }

    public void pickCharacterCard(){

    }

    /*utility methods*/
    private boolean checkValidColor(String color){
        if(color.equals("RED") || color.equals("BLUE") || color.equals("GREEN") ||
        color.equals("PINK") || color.equals("YELLOW"))
            return true;
        else
            return false;
    }

    /* this method prints errors in red, basing off the corresponding type of BaseServerMessage received */
    @Override
    public void displayError(BaseServerMessage message){
        System.out.println(AnsiColors.ANSI_RED + "\n" + message.getMessage() + AnsiColors.ANSI_RESET);
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
