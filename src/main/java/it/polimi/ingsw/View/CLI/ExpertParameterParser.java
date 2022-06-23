package it.polimi.ingsw.View.CLI;

import it.polimi.ingsw.Model.Expert.Parameter;
import it.polimi.ingsw.Utils.Enums.PawnDiscColor;

import java.util.ArrayList;
import java.util.Scanner;

/** support class to Parser, which requests information from user
 * and is responsible for its correct acquisition*/
public class ExpertParameterParser {

    /** this method requests information to enact character cards' effects
     * to the user; it uses a switch-case construct to differentiate the requests
     * and print custom dialog for each different card, and stores all user input
     * in a Parameter object
     * @param cardID of type int - the id of the card for which input is needed
     * @return of type Parameter - all information retrieved is stored here
     * @see Parameter*/

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

    /** this method requests a new integer to the user, and ensures its correct
     * parsing.
     * @return of type int - the integer acquired via CLI*/
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

    /** this method requests a String to the player, checks its validity within
     * the PawnDiscColor enum and ensures an actual color is given.
     * @return of type PawnDiscColor - the pawn color requested to user
     * @see PawnDiscColor*/

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

    /** method specific to parsing all the professor colors for the correct use
     * of character card 2. It ensures colors are expressed in a valid format
     * before returning them in an ArrayList.
     * @see it.polimi.ingsw.Model.Expert.Card2*/
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

    /** utility method to perform color validity check
     * @return boolean - true if color is valid*/
    private boolean checkColorValidity(String color){
        if(color.equals("RED") || color.equals("BLUE") || color.equals("GREEN") ||
                color.equals("PINK") || color.equals("YELLOW"))
            return true;
        else
            return false;
    }

}
