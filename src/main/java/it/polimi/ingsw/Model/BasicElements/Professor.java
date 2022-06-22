package it.polimi.ingsw.Model.BasicElements;
import it.polimi.ingsw.Utils.Enums.PawnDiscColor;

/** Class Professor contains references to the color of the disk representing the professor and a variable that
 * indicates whether the professor is owned by a player or not. */

public class Professor {

    private PawnDiscColor color;
    private boolean ownedByPlayer;


    /** Constructor Professor creates a new instance of a professor
     *
     * @param color - of type PawnDiscColor - the color of the professor */
    public Professor(PawnDiscColor color) {
        this.color = color;
        this.ownedByPlayer = false;
    }


    /** getter method - getColor returns the color associated to the professor
     *
     * @return PawnDiscColor - the color of the professor */
    public PawnDiscColor getColor() {
        return color;
    }

    /** isOwnedByPlayer returns a boolean value that indicates wheter the professor is owned by a player or not.
     *
     * @return boolean - true if owned, false otherwise */
    public boolean isOwnedByPlayer(){
        return ownedByPlayer;
    }


    /** setter method - setOwnedToTrue sets variable to true when a player takes control of a professor */
    public void setOwnedToTrue(){
        ownedByPlayer = true;
    }

    /** setter method - setOwnedToFalse sets variable to false when a player looses control over a professor,
     * usually because another player took control of it */
    public void setOwnedToFalse(){
        ownedByPlayer = false;
    }
}
