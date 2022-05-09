package it.polimi.ingsw.Expert;

import it.polimi.ingsw.Player;

public class CoinCounter {

    private Player owner;
    private int counter;

    public CoinCounter(Player owner){
        this.owner = owner;
        counter = 1;
    }

    public Player getOwner(){
        return owner;
    }

    public int getCoins(){
        return counter;
    }

    public void addCoin(){
        counter++;
    }

    public void purchase(int cost){
        this.counter -= cost;
    }

    public boolean checkIfEnoughCoins(int cost){
        if(counter >= cost){
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    /*FORMAT Owner numberOfCoins*/
    public String toString(){
        return owner.getNickname() + " " + counter;
    }
}
