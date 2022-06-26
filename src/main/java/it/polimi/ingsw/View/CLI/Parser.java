package it.polimi.ingsw.View.CLI;

import it.polimi.ingsw.Client.ActionMessages.*;
import it.polimi.ingsw.Utils.Enums.GameMode;
import it.polimi.ingsw.Utils.Enums.PawnDiscColor;

import java.util.Observable;
import java.util.Scanner;

/** class parser listens for keyboard input from user on CLI. This class
 * is kept running on a parallel thread so that both updates from server and user's keyboard
 * can be accommodated with no synchronization conflicts.
 * @see Runnable
 * @see java.util.Observable */

public class Parser extends Observable implements Runnable{

    GameMode mode;

    /** override for Runnable's run method, this implementation continuously
     * requests user to perform an action.
     * @see Runnable*/
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

    /** this method stores game mode so that correct information can be requested
     * to the user.
     * @param mode of type GameMode - game mode of this match*/
    public void setMode(GameMode mode){
        this.mode = mode;
    }

    /** this method prints on CLI the pattern that user must follow to use the
     * interface. It then requests an int that represents the action that the user
     * wants to perform, which includes expert features.
     * Once that is acquired, the requested action is attempted.*/
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

    /** this method prints on CLI the pattern that user must follow to use the
     * interface. It then requests an int that represents the action that the user
     * wants to perform. Once that is acquired, the requested action is attempted.*/
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

    /** this method performs an action based on user's request.
     * @param i of type int - identifier of action to perform*/
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

    /** method requests identifier of assistant card that user attempts to play.
     * Once that is acquired, a simple format check for a valid number (between 1 and 10)
     * is made before Observers are notified and message can be sent.
     * @see Observable
     * @see AssistantCardMessage*/
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

    /** method requests color of student to be moved from entrance to dining room.
     * A simple format check for color validity is made before Observers are
     * notified so that the action message can be sent.
     * @see Observable
     * @see MoveStudentToDRMessage*/
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

    /** this method requests both the color of the student to be moved from entrance
     * and the ID of the island that the student will be moved to. Simple checks
     * for valid color and island identifier are made before Observers are notified
     * and action message is therefore sent.
     * @see Observable
     * @see MoveStudentToIslandMessage*/
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

    /** this method requests the id of the cloud that user wants to choose at the end
     * of their turn. Once the integer is acquired, simple validity checks are performed
     * and then Observers are notified so that action message can be sent.
     * @see Observable
     * @see PickCloudMessage*/
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

    /** this method requests an id of a character card (expert feature) to the user.
     * Once the id is acquired, simple checks are performed (integer must be between 1 and 12)
     * before action message is sent by notifying Observers.
     * @see Observable
     * @see CharacterCardMessage*/
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

    /** utility method to perform color validity check
     * @return boolean - true if color is valid*/
    private synchronized boolean checkValidColor(String color){
        if(color.equals("RED") || color.equals("BLUE") || color.equals("GREEN") ||
                color.equals("PINK") || color.equals("YELLOW"))
            return true;
        else
            return false;
    }


}
