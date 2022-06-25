package it.polimi.ingsw.tests;
import it.polimi.ingsw.Model.BasicElements.AssistantCard;
import it.polimi.ingsw.Model.BoardClasses.Board;
import it.polimi.ingsw.Utils.Enums.GameMode;
import it.polimi.ingsw.Utils.Enums.PawnDiscColor;
import it.polimi.ingsw.Model.Model;
import it.polimi.ingsw.Model.Player;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.Assert.*;

/** Class BoardTest tests class BoardTest. */

public class BoardTest {

    ArrayList<Player> players = new ArrayList<>();
    Board boardToTest;
    Model model;

    /** Method playAssistantCardTest tests the playing of an assistant card and checks if the value of the card match
     * the ones stored in local variable card.
     *
     * */
    @Test
    public void playAssistantCardTest(){
        players.add(new Player("player1"));
        players.add(new Player("player2"));
        boardToTest = new Board(players, 2, 8,
                3, 7, GameMode.SIMPLE);
        try {
            boardToTest.setup();
        }catch (Exception e){}

        AssistantCard card = new AssistantCard(1, 1, players.get(0));
        card = boardToTest.playAssistantCard(1, "player1");
        assertEquals(1, card.getAssistantCardID());
        assertEquals(1, card.getMotherNatureMovements());
    }


    /** Method moveStudentTest tests moving of a student from the entrance to the dining room. */
    @Test
    public void moveStudentTest(){
        players.add(new Player("player1"));
        players.add(new Player("player2"));
        boardToTest = new Board(players, 2, 8,
                3, 7, GameMode.SIMPLE);
        try {
            boardToTest.setup();
        }catch (Exception e){}

        if(boardToTest.colorAvailableInEntrance("player2", PawnDiscColor.BLUE)){
            boardToTest.moveStudent(PawnDiscColor.BLUE, "player2");
            assertTrue(boardToTest.getSchoolBoards().get(1).getDiningRoom().influenceForProf(PawnDiscColor.BLUE) == 1);
        }
    }


    /** Method moveStudentTest tests moving of a student from the entrance to an island. */
    @Test
    public void moveStudentToIslandTest(){
        int i = 0;
        players.add(new Player("player1"));
        players.add(new Player("player2"));
        boardToTest = new Board(players, 2, 8,
                3, 7, GameMode.SIMPLE);
        try {
            boardToTest.setup();
        }catch (Exception e){}

        i = boardToTest.getIslandList().get(4).getInfluenceByColor(PawnDiscColor.GREEN);

        if(boardToTest.colorAvailableInEntrance("player1", PawnDiscColor.GREEN)){
            boardToTest.moveStudent(PawnDiscColor.GREEN, players.get(0).getNickname(), 4);
            i = i + boardToTest.getIslandList().get(4).getInfluenceByColor(PawnDiscColor.GREEN);
            assertTrue(i >= 1);
        }
    }


    /** Method pickCloudTileTest tests the choosing of a cloud tile. */
    @Test
    public void pickCloudTileTest(){
        players.add(new Player("player1"));
        players.add(new Player("player2"));
        players.add(new Player("player3"));
        boardToTest = new Board(players, 3, 6,
                4, 9, GameMode.SIMPLE);
        int i = 0;
        try {
            boardToTest.setup();
        }catch (Exception e){}
        do {
            for (PawnDiscColor c : PawnDiscColor.values()) {
                if (boardToTest.colorAvailableInEntrance("player3", c))
                    boardToTest.moveStudent(c, "player3");
                i++;
            }
        }while(i<3);
        if(!boardToTest.getPlayerSchoolBoard(players.get(2).getNickname()).getEntrance().isEntranceFull())
            boardToTest.chooseCloudTile("player3", 2);
    }


    /** Method moveMotherNatureTest tests the moving of mother nature: it calls for moveMotherNature and assigns it
     * n movements, then checks if the module operation computes correctly. */
    @Test
    public void moveMotherNatureTest(){
        int initial_pos;
        int final_pos;
        players.add(new Player("player1"));
        players.add(new Player("player2"));
        players.add(new Player("player3"));
        boardToTest = new Board(players, 3, 6,
                4, 9, GameMode.SIMPLE);
        try {
            boardToTest.setup();
        }catch (Exception e){}
        initial_pos = boardToTest.getMotherNaturePosition();
        boardToTest.moveMotherNature(4);
        final_pos = (4+initial_pos)%boardToTest.getIslandList().size();
        assertEquals(final_pos, boardToTest.getMotherNaturePosition());
    }

    /** Method assignProfessorsTest method tests the assigning of the professors */
    @Test
    public void assignProfessorsTest() {
        players.add(new Player("player1"));
        players.add(new Player("player2"));
        players.add(new Player("player3"));
        boardToTest = new Board(players, 3, 6,
                4, 6, GameMode.SIMPLE);
        try {
            boardToTest.setup();
        }catch (Exception e){}
        for (PawnDiscColor c : PawnDiscColor.values()) {
            if (boardToTest.colorAvailableInEntrance("player2", c)) {
                boardToTest.moveStudent(c, "player2");
            }
        }
        boardToTest.assignProfessors();
        for (PawnDiscColor c : PawnDiscColor.values()) {
            System.out.print("player 2: " + c + "\t");
            System.out.println(boardToTest.getPlayerSchoolBoard("player2").getDiningRoom().influenceForProf(c));
        }
        System.out.println(boardToTest.getPlayerSchoolBoard("player2").getProfessorTable());
    }

    /** Method turnFacSimileTest attempts to simulate a turn to test the way the various methods interact
    * with each other, testing the way an island can be conquered based on the way mother nature moves,
    * not integrating the assistant card drawing which is tested along with the turn manager class */
    @Test
    public void turnFacSimileTest(){
        players.add(new Player("player1"));
        players.add(new Player("player2"));
        players.add(new Player("player3"));
        boardToTest = new Board(players, 3, 6,
                4, 9, GameMode.SIMPLE);
        try {
            boardToTest.setup();
        }catch (Exception e){}

        int ip = 0;
        while(ip < 3){
            for(PawnDiscColor c : PawnDiscColor.values()){
                if(boardToTest.colorAvailableInEntrance("player2", c)) {
                    boardToTest.moveStudent(c, "player2", boardToTest.getMotherNaturePosition());
                    ip++;
                }
                if(boardToTest.colorAvailableInEntrance("player2", c)){
                    boardToTest.moveStudent(c,"player2");
                }
            }
        }
        boardToTest.assignProfessors();
        System.out.println(boardToTest.getPlayerSchoolBoard("player2").getProfessorTable());
        boardToTest.conquerIsland();
        if(boardToTest.getIslandList().get(boardToTest.getMotherNaturePosition()).checkIfConquered())
            System.out.println("player 2 conquered the " + boardToTest.getMotherNaturePosition() + " island!");
    }

    /** Method repeatedConqueringTest simulates a player's turn where they move students both to the island and to the
     * their dining room, then conquers the island and checks if there's merging to do. */
    @Test
    public void repeatedConqueringTest() {
        players.add(new Player("player1"));
        players.add(new Player("player2"));
        boardToTest = new Board(players, 2, 8,
                3, 30, GameMode.SIMPLE);
        try {
            boardToTest.setup();
        }catch (Exception e){}
        boardToTest.getMotherNature().setPosition(11);
        int ip = 0;
        for (int i = 0; i < 3; i++) {
            while (ip < 3) {
                for (PawnDiscColor c : PawnDiscColor.values()) {
                    if (boardToTest.colorAvailableInEntrance("player2", c)) {
                        boardToTest.moveStudent(c, "player2", boardToTest.getMotherNaturePosition());
                        ip++;
                    }
                    if (boardToTest.colorAvailableInEntrance("player2", c)) {
                        boardToTest.moveStudent(c, "player2");
                    }
                }
            }
            boardToTest.assignProfessors();
            System.out.println(boardToTest.getPlayerSchoolBoard("player2").getProfessorTable());
            boardToTest.conquerIsland();
            if (boardToTest.getIslandList().get(boardToTest.getMotherNaturePosition()).checkIfConquered())
                System.out.println("player 2 conquered the " + boardToTest.getMotherNaturePosition() + " island!");

            boardToTest.checkForMerge();
            boardToTest.moveMotherNature(1);
        }
    }


    /** Method conquerTest tests the conquering of an island: firstly, it tests the conquering of empty islands,
     *  handling the conquering of an island when two players hold the same influence, leaving the island
     *  unconquered.
     *  If the island has not yet been conquered and there is a tie between players regarding the influence,
     *  the island is conquered by the current player (nickname in the conquerIsland parameters)*/
    @RepeatedTest(20)
    public void conquerTest() {
        players.add(new Player("player1"));
        players.add(new Player("player2"));
        boardToTest = new Board(players, 2, 8,
                3, 30, GameMode.SIMPLE);
        try {
            boardToTest.setup();
        }catch (Exception e){}

        boardToTest.getMotherNature().setPosition((boardToTest.getMotherNaturePosition() + 6) % 12);
        System.out.println("is island empty? " + boardToTest.getIslandList().get(boardToTest.getMotherNaturePosition()).checkIfIslandIsEmpty());
        boardToTest.conquerIsland();

        assertEquals(false, boardToTest.getIslandList().get(boardToTest.getMotherNaturePosition()).checkIfConquered());

        for (int i = 0; i < 2; i++) {
            try {
                boardToTest.moveStudent(PawnDiscColor.YELLOW, "player1", boardToTest.getMotherNaturePosition());
                boardToTest.moveStudent(PawnDiscColor.YELLOW, "player1");
                boardToTest.moveStudent(PawnDiscColor.PINK, "player2", boardToTest.getMotherNaturePosition());
                boardToTest.moveStudent(PawnDiscColor.PINK, "player2");
            }catch(IndexOutOfBoundsException e){
                System.out.println("not enough pink or yellow students in entrance");
            }
        }
        boardToTest.assignProfessors();
        System.out.println(boardToTest.getPlayerSchoolBoard("player2").getProfessorTable());

        boardToTest.conquerIsland();
        try{
            boardToTest.getIslandList().get(boardToTest.getMotherNaturePosition()).getOwner().getNickname();
        }catch(NullPointerException e){
            System.out.println("island wasn't conquered!");
        }

        try {
            boardToTest.moveStudent(PawnDiscColor.YELLOW, "player1", boardToTest.getMotherNaturePosition());
            boardToTest.moveStudent(PawnDiscColor.YELLOW, "player1");
        }catch(IndexOutOfBoundsException e){
            System.out.println("not enough yellow students");
        }

        boardToTest.assignProfessors();
        boardToTest.conquerIsland();
        if(boardToTest.getIslandList().get(boardToTest.getMotherNaturePosition()).getOwner() != null)
            assertEquals("player1", boardToTest.getIslandList().get(boardToTest.getMotherNaturePosition()).getOwner().getNickname());
        else
            System.out.println("island " + boardToTest.getMotherNaturePosition() + " was not conquered");

        try {
            boardToTest.moveStudent(PawnDiscColor.PINK, "player2", boardToTest.getMotherNaturePosition());
            boardToTest.moveStudent(PawnDiscColor.PINK, "player2");
        }catch(IndexOutOfBoundsException e){
            System.out.println("not enough pink students");
        }
        boardToTest.assignProfessors();
        try {
            boardToTest.conquerIsland();
        }catch(NullPointerException e){
            System.out.println(e.getMessage());
        }
        try {
            System.out.println("conqueror is " + boardToTest.getIslandList().get(boardToTest.getMotherNaturePosition()).getOwner().getNickname());
        }catch(Exception e){
            System.out.println("island not conquered!\n");
        }
    }
}
