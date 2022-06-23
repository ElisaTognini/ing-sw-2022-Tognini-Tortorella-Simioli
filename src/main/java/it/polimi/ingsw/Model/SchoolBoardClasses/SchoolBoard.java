package it.polimi.ingsw.Model.SchoolBoardClasses;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Utils.Enums.TowerColor;

/** Class SchoolBoard represents the player's school board and contains references to all the classes composing the
 * actual school board, entrance, dining room, professor table and tower section, and to the player who owns it. */

public class SchoolBoard {

    private Entrance entrance;
    private DiningRoom diningRoom;
    private ProfessorTable professorTable;
    private TowerSection towerSection;
    private Player owner;
    private boolean modifiedTable;

    /** Constructor SchoolBoard creates a new instance of a school board, based on the number of students to add in
     * the entrance, the towers to put in the tower section, the color of the towers and the player who is going to
     * own the school board.
     *
     * @param studentsInEntrance of type int - number of students in the entrance
     * @param numberOfTowers of type int - number of towers in the tower section
     * @param towerColor of type TowerColor - the color of the towers associated to the player
     * @param owner of type Player - the owner of the school board */
    public SchoolBoard(int studentsInEntrance, int numberOfTowers, TowerColor towerColor, Player owner){
        entrance = new Entrance(studentsInEntrance);
        professorTable = new ProfessorTable();
        towerSection = new TowerSection(numberOfTowers, towerColor);
        diningRoom = new DiningRoom();
        this.owner = owner;
        modifiedTable = false;
    }


    /** getter method - Method getOwner returns the owner of the school board
     *
     * @return Player - player who owns the school board */
    public Player getOwner(){
        return owner;
    }


    /** getter method - Method getEntrance returns the entrance of the school board
     *
     * @return Entrance - the entrance of the school board */
    public Entrance getEntrance(){
        return entrance;
    }


    /** getter method - Method getDiningRoom returns the dining room of the school board
     *
     * @return DiningRoom - the dining room of the school board */
    public DiningRoom getDiningRoom(){
        return diningRoom;
    }


    /** getter method - Method getProfessorTable returns the professor table of the school board
     *
     * @return ProfessorTable - the professor table of the school board */
    public ProfessorTable getProfessorTable() {
        return professorTable;
    }


    /** getter method - Method getTowerSection returns the tower section of the school board
     *
     * @return TowerSection - the tower section of the school board */
    public TowerSection getTowerSection(){
        return towerSection;
    }


    /** setter method - Method setModifiedTable sets the value of boolean attribute modifiedTable to true if the
     * player plays character card 2 */
    public void setModifiedTable(){
        modifiedTable = true;
    }


    /** setter method - Method resetModifiedTable sets the flag set if player plays character card 2, in order to
     * reset the professor's table previous state */
    public void resetModifiedTable(){modifiedTable = false;}


    /** getter method - Method getModifiedTable returns the value of boolean attribute modifiedTable
     *
     * @return boolean - true if a player played character card 2, false otherwise */
    public boolean getModifiedTable(){return modifiedTable;}

    /**
     * setter method setProfessorTable sets the professor table of this schoolboard ad the one that has been
     * passed as parameter.
     *
     * @param professorTable of type ProfessorTable - professor table.
     * */
    public void setProfessorTable(ProfessorTable professorTable){
        this.professorTable = professorTable;
    }


    /** Method toString builds a String containing all the info stored in this class
     *
     *  @return String - FORMAT: the owner of the school board, entrance, dining room, professor table and
     *  tower section, each separated by a blank space.
     *  */
    @Override
    public String toString(){
        return owner.getNickname() + "\n" +
                entrance.toString() + "\n" +
                diningRoom.toString() + "\n" +
                professorTable.toString() + "\n" +
                towerSection.toString();
    }
}
