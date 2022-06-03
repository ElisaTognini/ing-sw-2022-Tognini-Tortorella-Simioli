package it.polimi.ingsw.View.CLI;

import it.polimi.ingsw.Model.Expert.Parameter;
import it.polimi.ingsw.Utils.Enums.PawnDiscColor;

import java.util.ArrayList;
import java.util.Scanner;

public class ExpertParameterParser {

    public Parameter parseParameter(int cardID){
        Parameter param = new Parameter();
        switch(cardID){
            case 1:
                System.out.println("enter the island you would like to move your student to: ");
                param.setIslandID(parseNewInt());
                System.out.println("now enter its color: ");
                param.setColor(parseNewColor());
                break;
            case 2:
                System.out.println("enter the colors of the professors you'd like to control, " +
                        "separated by a SPACE.");
                param.setColorArrayList(parseCard2());
                break;
            case 3:
                System.out.println("enter the island you would like to resolve: ");
                param.setIslandID(parseNewInt());
                break;
            case 8:
                /*no effects caused by parameter*/
                break;
            case 4:
                System.out.println("enter a number of positions (1 or 2) you would like to add to Mother Nature's path: ");
                param.setMoves(parseNewInt());
                break;
            case 5:
                System.out.println("enter the island you would like to place your no entry tile to: ");
                param.setIslandID(parseNewInt());
                break;
            case 6:
                System.out.println("enter the island that will have no tower influence: ");
                param.setIslandID(parseNewInt());
                break;
            case 7:
                ArrayList<PawnDiscColor> studentsOnEntrance = new ArrayList<>();
                ArrayList<PawnDiscColor> studentsOnCard = new ArrayList<>();
                System.out.println("enter the color of the three students on your entrance you would like to switch: ");
                for(int i = 0; i < 3; i++){
                    studentsOnEntrance.add(parseNewColor());
                }
                System.out.println("now for the students on this card you would like on your entrance - choose wisely!");
                for(int i = 0; i < 3; i++){
                    studentsOnCard.add(parseNewColor());
                }
                param.setColorArrayList2(studentsOnEntrance);
                param.setColorArrayList(studentsOnCard);
                break;
            case 9:
                System.out.println("choose a color!");
                param.setColor(parseNewColor());
                break;
            case 10:
                ArrayList<PawnDiscColor> studentsOnDR = new ArrayList<>();
                ArrayList<PawnDiscColor> studentsOnEntr = new ArrayList<>();
                System.out.println("enter the color of the two students on your entrance you would like to switch: ");
                for(int i = 0; i < 2; i++){
                    studentsOnEntr.add(parseNewColor());
                }
                System.out.println("now for the students on your dining room you would like on your entrance - choose wisely!");
                for(int i = 0; i < 2; i++){
                    studentsOnDR.add(parseNewColor());
                }
                param.setColorArrayList(studentsOnEntr);
                param.setColorArrayList(studentsOnDR);
                break;
            case 11:
                System.out.println("enter the color of the student you would like on your dining room");
                param.setColor(parseNewColor());
                break;
            case 12:
                System.out.println("choose a color for the students to retrieve for everyone!");
                param.setColor(parseNewColor());
                break;
        }

        return param;
    }

    public int parseNewInt(){
        Scanner scanner = new Scanner(System.in);
        String i = "0";
        int toRet;
        boolean flag = false;
        do{
            try{
                flag = false;
                i = scanner.nextLine();
                if(!(i.matches("\\d+"))){
                    System.out.println("enter a valid integer!");
                    flag = true;
                }
            }
            catch (Exception e) {
                System.err.println("enter validly formatted integer!");
            }
        }while(flag);

        toRet = Integer.parseInt(i);
        return toRet;
    }

    public PawnDiscColor parseNewColor(){
        Scanner scanner = new Scanner(System.in);
        String read;
        boolean flag;

        while(true){
            try{
                do {
                    flag = false;

                    read = scanner.nextLine();
                    read = read.toUpperCase();

                    if (!(checkColorValidity(read))){
                        System.err.println("enter a valid color!");
                        flag = true;
                    }
                }while (flag);
                break;
            }catch(Exception e){
                System.err.println("enter a valid color!");
            }
        }

        return PawnDiscColor.valueOf(read);
    }

    private ArrayList<PawnDiscColor> parseCard2(){
        Scanner scanner = new Scanner(System.in);
        ArrayList<PawnDiscColor> colors = new ArrayList<>();
        String read;
        String[] splitRead;
        boolean flag = false;

        do{
            try {
                flag = false;
                read = scanner.nextLine();
                splitRead = read.split(" ");
                for(String s : splitRead){
                    if(checkColorValidity(s.toUpperCase())){
                        colors.add(PawnDiscColor.valueOf(s.toUpperCase()));
                    }
                    else{
                        flag = true;
                        System.err.println("enter valid colors!");
                    }
                }

            }catch (Exception e){
                System.err.println("use a valid format!");
            }

        }while(flag);

        return colors;
    }

    private boolean checkColorValidity(String color){
        if(color.equals("RED") || color.equals("BLUE") || color.equals("GREEN") ||
                color.equals("PINK") || color.equals("YELLOW"))
            return true;
        else
            return false;
    }

}
