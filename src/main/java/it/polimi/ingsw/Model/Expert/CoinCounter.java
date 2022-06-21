package it.polimi.ingsw.Model.Expert;
import it.polimi.ingsw.Model.Player;

/** Class CoinCounter works as storage for the coins collected by the player throughout the game, indeed it contains
 * a reference to the player who owns it and a variable for the number of coins. */

public class CoinCounter {

    private Player owner;
    private int counter;

    /** Constructor CoinCounter creates a new instance of coin counter for the player passed as parameter and initializes
     * variable counter to 1 because each players starts the game with one coin. */
    public CoinCounter(Player owner){
        this.owner = owner;
        counter = 1;
    }


    /** getter method - Method getOwner returns the owner of this counter
     *
     * @return Player - owner */
    public Player getOwner(){
        return owner;
    }


    /** getter method - Method getCoins returns the number of coins inside this counter
     *
     * @return int - value of attribute counter */
    public int getCoins(){
        return counter;
    }

    /** Method addCoins increases value of attribute counter. */
    public void addCoin(){
        counter++;
    }

    /** Method purchase decreases value of attribute counter by the cost of the character card
     *
     * @param cost of type int - cost of the character card */
    public void purchase(int cost){
        this.counter -= cost;
    }


    /** Method checkIfEnoughCoins checks if player has enough coins to purchase the character card
     *
     * @param cost of type int - cost of the character card
     *
     * @return boolean - true if counter has enough coins, false otherwise*/
    public boolean checkIfEnoughCoins(int cost){
        return counter >= cost;
    }

    /** Method toString builds a String containing all the info stored in this class
     *
     *  @return String - FORMAT: player owner's nickname and number of coins (value of attribute counter), each
     *  separated by a blank space .
     *  */
    @Override
    public String toString(){
        return owner.getNickname() + " " + counter;
    }
}
