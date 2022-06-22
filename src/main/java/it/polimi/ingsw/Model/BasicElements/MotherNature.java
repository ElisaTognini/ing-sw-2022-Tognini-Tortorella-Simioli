package it.polimi.ingsw.Model.BasicElements;

/** Class MotherNature contains a reference to the island mother nature is during a turn/round */

public class MotherNature {

    private int position;


    /** getter method - getPosition return the island ID of the island mother nature is currently on
     *
     * @return int - mother nature's position*/
    public int getPosition() {
        return position;
    }


    /** setter method - setPosition sets mother nature position to a given island ID
     *
     * @param position - of type int - mother nature's next position */
    public void setPosition(int position) {
        this.position = position;
    }

    /** Method toString builds a String containing all the info stored in this class
     *
     * @return String - FORMAT: String value of mother nature's position */
    @Override
    public String toString(){
        return String.valueOf(position);
    }
}
