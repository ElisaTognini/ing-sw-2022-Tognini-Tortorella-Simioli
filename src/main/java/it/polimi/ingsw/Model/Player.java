package it.polimi.ingsw.Model;

/** Class Player contains the player's nickname and three boolean attributes which are dynamically changed if the player
 * wins the game, when they have picked a card or a cloud, respectively. */
public class Player {

    private String nickname;
    private boolean isWinner;
    private boolean cardPicked;
    private boolean cloudPicked;

    /** Constructor Player creates a new instance of a player taking the nickname in input
     *
     * @param nickname of type String - player's nickname */
    public Player(String nickname){
        this.nickname = nickname;
        isWinner = false;
        cardPicked = false;
        cloudPicked = false;
    }

    /** setter method - Method setCloudPicked sets value of attribute cloudPicked as the one taken as parameter
     *
     * @param cloudPicked of type boolean */
    public void setCloudPicked(boolean cloudPicked) {
        this.cloudPicked = cloudPicked;
    }

    /** getter method - Method getNickname returns this player's nickname
     *
     * @return String - player's nickname*/
    public String getNickname(){
        return nickname;
    }


    /** getter method - Method getCardPicked returns value of boolean attribute cardPicked
     *
     * @return boolean - true if the player has picked a card, false otherwise */
    public boolean getCardPicked(){ return cardPicked; }


    /** setter method - method setCardPickedToTrue sets value of boolean attribute cardPicked to true */
    public void setCardPickedToTrue(){ cardPicked = true; }


    /** setter method - method setCardPickedToFalse sets value of boolean attribute cardPicked to false */
    public void setCardPickedToFalse(){ cardPicked = false; }


    /** setter method - Method setWinner sets value of boolean attribute isWinner to true */
    public void setWinner(){
        isWinner = true;
    }
}
