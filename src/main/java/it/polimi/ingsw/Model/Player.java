package it.polimi.ingsw.Model;

public class Player {

    private String nickname;
    private boolean isWinner;
    private boolean cardPicked;

    public Player(String nickname){
        this.nickname = nickname;
        isWinner = false;
        cardPicked = false;
    }

    public String getNickname(){
        return nickname;
    }


    public boolean getCardPicked(){ return cardPicked; }

    public void setCardPickedToTrue(){ cardPicked = true; }

    public void setCardPickedToFalse(){ cardPicked = false; }

    public void setWinner(){
        isWinner = true;
    }
}
