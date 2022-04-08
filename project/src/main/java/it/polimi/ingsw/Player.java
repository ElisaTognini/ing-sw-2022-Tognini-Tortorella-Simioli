package it.polimi.ingsw;

import it.polimi.ingsw.BasicElements.AssistantCard;
import it.polimi.ingsw.Enums.*;
import it.polimi.ingsw.SchoolBoardClasses.SchoolBoard;

public class Player {

    private String nickname;
    private boolean isWinner;
    private int numberOfCoins; /*only used in expert mode*/
    private Wizards wizard;
    private boolean cardPicked;

    public Player(String nickname){
        this.nickname = nickname;
        isWinner = false;
        numberOfCoins = 0;
        cardPicked = false;
    }

    public String getNickname(){
        return nickname;
    }

    public Wizards getWizard(){
        return wizard;
    }

    public boolean getCardPicked(){ return cardPicked; }

    public void setCardPickedToTrue(){ cardPicked = true; }

    public void setCardPickedToFalse(){ cardPicked = false; }
}
