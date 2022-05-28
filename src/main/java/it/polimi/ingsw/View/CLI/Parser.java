package it.polimi.ingsw.View.CLI;

import it.polimi.ingsw.Client.ActionMessages.*;
import it.polimi.ingsw.Utils.Enums.GameMode;
import it.polimi.ingsw.Utils.Enums.PawnDiscColor;

import java.util.Observable;
import java.util.Scanner;

public class Parser extends Observable implements Runnable{

    GameMode mode;

    @Override
    public void run() {
       while(true){
           if(mode.equals(GameMode.SIMPLE)){
               parseActionType();
           }
           else if(mode.equals(GameMode.EXPERT)){
               parseActionTypeExpert();
           }
       }
    }

    public void setMode(GameMode mode){
        this.mode = mode;
    }

    public synchronized void parseActionTypeExpert() {
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
            if (i <= 0 || i > 5) {
                flag = true;
                System.out.println(AnsiColors.ANSI_RED + "invalid action type.\n" + AnsiColors.ANSI_RESET);
            }
        } while (flag == true);
        selectAction(i);
    }


    public synchronized void parseActionType(){
        Scanner scanner = new Scanner(System.in);
        int i;
        boolean flag;
        try {
            do {
                flag = false;
                System.out.println("\n time to choose an action!\n" +
                        "enter 1 if you would like to pick an assistant card\n" +
                        "enter 2 if you would like to move a student from your entrance to the dining room\n" +
                        "enter 3 if you would like to move a student from your entrance to an island\n" +
                        "enter 4 if you would like to pick a cloud tile\n");
                i = scanner.nextInt();
                if (i <= 0 || i > 4) {
                    flag = true;
                    System.out.println(AnsiColors.ANSI_RED + "invalid action type.\n" + AnsiColors.ANSI_RESET);
                }
            } while (flag == true);
            selectAction(i);
        }catch(Exception e){
            System.err.println("please use a valid format!\n");
        }
    }


    private synchronized void selectAction(int i){
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
    public synchronized void pickAssistantCard(){
        Scanner scanner = new Scanner(System.in);
        int i;
        boolean flag;
        System.out.println("\nenter the number of the assistant card you would like to pick.");
        do{
            flag = false;
            i = scanner.nextInt();
            if(i < 0 || i > 10){
                flag = true;
                System.out.println(AnsiColors.ANSI_RED + "choose a valid card number!\n" + AnsiColors.ANSI_RESET);
            }
        }while(flag == true);

        setChanged();
        notifyObservers(new AssistantCardMessage(i));
    }

    public synchronized void moveStudentToDR(){
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
        }while(flag);

        setChanged();
        notifyObservers(new MoveStudentToDRMessage(PawnDiscColor.valueOf(read)));
    }

    public synchronized void moveStudentToIsland(){
        Scanner scanner = new Scanner(System.in);
        String read;
        int i;
        boolean flag;

        System.out.println("\nenter the id of the island you would like to move your student to.");
        try {
            do {
                flag = false;
                i = scanner.nextInt();
                if (i < 0 || i > 11) {
                    flag = true;
                    System.out.println(AnsiColors.ANSI_RED + "choose a valid IslandID\n" + AnsiColors.ANSI_RESET);
                }
            } while (flag == true);
        }catch(Exception e){
            System.err.println("please write a correct island identifier!\n");
            return;
        }

        System.out.println("now enter the color of the student you would like to move.");
        do{
            flag = false;
            scanner = new Scanner(System.in);
            read = scanner.nextLine();
            read = read.toUpperCase();
            if(!checkValidColor(read)){
                System.out.println("enter a valid color!");
                flag = true;
            }
        }while(flag);

        setChanged();
        notifyObservers(new MoveStudentToIslandMessage(i, PawnDiscColor.valueOf(read)));
    }

    public synchronized void pickCloud(){
        Scanner scanner = new Scanner(System.in);
        int i;
        boolean flag;
        System.out.println("enter the number of the cloud you would like to pick: ");

        try {
            do {
                flag = false;
                i = scanner.nextInt();
                if (i < 0 || i > 2) {
                    flag = true;
                    System.out.println(AnsiColors.ANSI_RED + "choose a valid cloud number!\n" + AnsiColors.ANSI_RESET);
                }
            } while (flag == true);

            setChanged();
            notifyObservers(new PickCloudMessage(i));
        }catch(Exception e){
            System.err.println("enter validly formatted cloud identifier!");
        }
    }

    public synchronized void pickCharacterCard(){
        Scanner scanner = new Scanner(System.in);
        int i;
        boolean flag;
        CharacterCardMessage message = new CharacterCardMessage();
        ExpertParameterParser cardParamParser = new ExpertParameterParser();
        System.out.println("choose the character card you would like to use: ");
        try{
            do{
                flag = false;
                i = scanner.nextInt();
                if(i < 1 || i > 12 )
                    flag = true;

            }while(flag == true);
        }catch(Exception e){
            System.err.println("enter a valid Character Card identifier!");
            return;
        }

        message.setCardID(i);
        message.setParam(cardParamParser.parseParameter(i));

        setChanged();
        notifyObservers(message);

    }

    /*utility methods*/
    private synchronized boolean checkValidColor(String color){
        if(color.equals("RED") || color.equals("BLUE") || color.equals("GREEN") ||
                color.equals("PINK") || color.equals("YELLOW"))
            return true;
        else
            return false;
    }


}
