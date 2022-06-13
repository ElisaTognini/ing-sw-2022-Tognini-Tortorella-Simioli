package it.polimi.ingsw.Model.BasicElements;

import it.polimi.ingsw.Utils.Enums.PawnDiscColor;

/** Class Student contains reference to its color */

public class Student {

    private PawnDiscColor color;

    /** Constructor Student creates a new instance of a student of a given color
     *
     * @param color - of type PawnDiscColor - the color of the student */
    public Student(PawnDiscColor color) {
        this.color = color;
    }


    /** getter method - getColor returns the color of a student
     *
     * @return PawnDiscColor - the color of the student */
    public PawnDiscColor getColor(){
        return this.color;
    }

}
