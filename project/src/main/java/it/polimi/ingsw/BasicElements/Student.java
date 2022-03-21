package it.polimi.ingsw.BasicElements;

import it.polimi.ingsw.Enums.PawnDiscColor;

public class Student {

    private PawnDiscColor color;

    public Student(PawnDiscColor color) {
        this.color = color;
    }

    public PawnDiscColor getColor(){
        return this.color;
    }

}
