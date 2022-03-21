package it.polimi.ingsw;

import it.polimi.ingsw.BasicElements.AssistantCard;
import it.polimi.ingsw.Enums.*;
import it.polimi.ingsw.SchoolBoardClasses.SchoolBoard;

public class Player {

    private String nickname;
    boolean isWinner;
    private AssistantCard[] deck;
    private int numberOfCoins; /*only used in expert mode*/
    private SchoolBoard schoolBoard;

    public Player(String nickname){
        this.nickname = nickname;
        isWinner = false;
        numberOfCoins = 0;
        schoolBoard = new SchoolBoard();
    }

    /*this method allows player to choose which wizard to have
    * as an assistant regarding the cards*/
    public Wizards choosesWizard(){

    }

    /*operations on the deck will be performed so that the
    * player is able to choose which card to play*/
    public AssistantCard chooseAssistantCard(){

    }

    /*moves students from entrance to either island or dining room*/
}
