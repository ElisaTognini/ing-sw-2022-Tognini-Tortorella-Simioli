package it.polimi.ingsw.AppClasses;

import it.polimi.ingsw.View.CLI.MainCLI;
import it.polimi.ingsw.View.GUI.GUI;

import java.util.Scanner;

public class ClientApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String choice;
        GUI gui = new GUI();
        MainCLI cli = new MainCLI();

        System.out.println("enter GUI or CLI:");

        do{
            choice = sc.nextLine();
            if(!choice.equalsIgnoreCase("cli") && !choice.equalsIgnoreCase("gui")){
                System.out.println("type CLI or GUI!");
            }
        }while(!choice.equalsIgnoreCase("cli") && !choice.equalsIgnoreCase("gui"));

        if(choice.equalsIgnoreCase("gui")){
            gui.main(args);
        }
        else if(choice.equalsIgnoreCase("CLI")){
            cli.main(args);
        }

    }
}
