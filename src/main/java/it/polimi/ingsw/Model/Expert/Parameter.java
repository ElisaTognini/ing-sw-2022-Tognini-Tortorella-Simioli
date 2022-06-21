package it.polimi.ingsw.Model.Expert;
import it.polimi.ingsw.Utils.Enums.PawnDiscColor;

import java.io.Serializable;
import java.util.ArrayList;

/** Class Parameter is a utility class for useCard method in the character cards: it contains attributes islandID and moves,
 *  color and two array lists of colors and sets their values according to the parameters required by the character
 *  card played.*/

public class Parameter implements Serializable {
    private int islandID;
    private PawnDiscColor color;
    private ArrayList<PawnDiscColor> colorArrayList;
    private ArrayList<PawnDiscColor> colorArrayList2;
    private int moves;


    /** Method setIslandID sets the island id passed as parameter as the value of attribute islandID in this class and
     * raises the flag set to true.
     *
     * @param islandID of type int - island ID */
    public void setIslandID(int islandID){
        this.islandID = islandID;
    }


    /** Method setColor sets the color passed as parameter as the value of attribute color in this class
     *
     * @param color of type PawnDiscColor - color */
    public void setColor(PawnDiscColor color) {this.color = color;}


    /** Method setColorArrayList sets the colors passed as parameter as the value of attribute colorArrayList in this
     * class.
     *
     * @param colors of type PawnDiscColor - array list of colors */
    public void setColorArrayList(ArrayList<PawnDiscColor> colors){ this.colorArrayList = colors;}


    /** Method setColorArrayList2 sets the colors passed as parameter as the value of attribute colorArrayList2 in this
     * class.
     *
     * @param colors of type PawnDiscColor - array list of colors */
    public void setColorArrayList2(ArrayList<PawnDiscColor> colors){ this.colorArrayList2 = colors;}


    /** Method setMoves sets the moves passed as parameter as the value of attribute m in this class.
     *
     * @param m of type int - additional moves */
    public void setMoves(int m){ this.moves = m;}


    /** getter method - Method getIslandID returns the value of attribute islandID in this class
     *
     * @return int - the id of the island */
    public int getIslandID(){
        return islandID;
    }


    /** getter method - Method getColor returns value of attribute color in this class
     *
     * @return PawnDiscColor - color */
    public PawnDiscColor getColor() {return color;}


    /** getter method - Method getColorArrayList returns value of attribute colorArrayList in this class
     *
     * @return ArrayList - array list of colors */
    public ArrayList<PawnDiscColor> getColorArrayList(){ return colorArrayList;}


    /** getter method - Method getColorArrayList2 returns value of attribute colorArrayList2 in this class
     *
     * @return ArrayList - array list of colors */
    public ArrayList<PawnDiscColor> getColorArrayList2(){ return colorArrayList2;}


    /** getter method - Method getMoves returns the value of attribute moves in this class
     *
     * @return int - moves */
    public int getMoves(){ return moves; }

}
