package it.polimi.ingsw.Model.BasicElements;

import it.polimi.ingsw.Utils.Enums.PawnDiscColor;

public class Student {

    private PawnDiscColor color;

    public Student(PawnDiscColor color) {
        this.color = color;
    }

    public PawnDiscColor getColor(){
        return this.color;
    }

}
