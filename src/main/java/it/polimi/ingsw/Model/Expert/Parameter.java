package it.polimi.ingsw.Model.Expert;

import it.polimi.ingsw.Utils.Enums.PawnDiscColor;

import java.util.ArrayList;
/* utility class for useCard method in the character cards */
public class Parameter {
    private int islandID;
    private PawnDiscColor color;
    private ArrayList<PawnDiscColor> colorArrayList;
    private ArrayList<PawnDiscColor> colorArrayList2;
    private int moves;

    public void setIslandID(int islandID){
        this.islandID = islandID;
    }
    public void setColor(PawnDiscColor color) {this.color = color;}
    public void setColorArrayList(ArrayList<PawnDiscColor> colors){ this.colorArrayList = colors;}
    public void setColorArrayList2(ArrayList<PawnDiscColor> colors){ this.colorArrayList2 = colors;}
    public void setMoves(int m){ this.moves = m;}

    public int getIslandID(){
        return islandID;
    }
    public PawnDiscColor getColor() {return color;}
    public ArrayList<PawnDiscColor> getColorArrayList(){ return colorArrayList;}
    public ArrayList<PawnDiscColor> getColorArrayList2(){ return colorArrayList2;}
    public int getMoves(){ return moves; }
}
