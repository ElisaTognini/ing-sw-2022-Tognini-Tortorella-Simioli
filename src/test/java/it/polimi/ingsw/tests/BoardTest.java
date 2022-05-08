package it.polimi.ingsw.tests;
import it.polimi.ingsw.BasicElements.AssistantCard;
import it.polimi.ingsw.BoardClasses.Board;
import it.polimi.ingsw.Enums.GameMode;
import it.polimi.ingsw.Enums.PawnDiscColor;
import it.polimi.ingsw.Model;
import it.polimi.ingsw.Player;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.Assert.*;

public class BoardTest {

    ArrayList<Player> players = new ArrayList<>();
    Board boardToTest;
    Model model;

    @Test
    public void playAssistantCardTest(){
        players.add(new Player("player1"));
        players.add(new Player("player2"));
        boardToTest = new Board(players, 2, 8,
                3, 7, GameMode.SIMPLE);
        boardToTest.setup();
        AssistantCard card = new AssistantCard(1, 1, players.get(0));
        card = boardToTest.playAssistantCard(1, "player1");
        assertEquals(1, card.getAssistantCardID());
        assertEquals(1, card.getMotherNatureMovements());
    }

    @Test
    /* moving students from entrance to dining room */
    public void moveStudentTest(){
        players.add(new Player("player1"));
        players.add(new Player("player2"));
        boardToTest = new Board(players, 2, 8,
                3, 7, GameMode.SIMPLE);
        boardToTest.setup();
        if(boardToTest.colorAvailableInEntrance("player2", PawnDiscColor.BLUE)){
            boardToTest.moveStudent(PawnDiscColor.BLUE, "player2");
        }
    }

    @Test
    public void moveStudentToIslandTest(){
        players.add(new Player("player1"));
        players.add(new Player("player2"));
        boardToTest = new Board(players, 2, 8,
                3, 7, GameMode.SIMPLE);
        boardToTest.setup();
        if(boardToTest.colorAvailableInEntrance("player1", PawnDiscColor.GREEN)){
            boardToTest.moveStudent(PawnDiscColor.GREEN, players.get(0).getNickname(), 4);
        }
    }

    @Test
    public void pickCloudTileTest(){
        players.add(new Player("player1"));
        players.add(new Player("player2"));
        players.add(new Player("player3"));
        boardToTest = new Board(players, 3, 6,
                4, 9, GameMode.SIMPLE);
        boardToTest.setup();
        for(PawnDiscColor c : PawnDiscColor.values()){
            if(boardToTest.colorAvailableInEntrance("player3", c))
                boardToTest.moveStudent(c, "player3");
        }
        if(!boardToTest.getPlayerSchoolBoard(players.get(2).getNickname()).getEntrance().isEntranceFull())
            boardToTest.chooseCloudTile("player3", 2);
    }

    @Test
    public void moveMotherNatureTest(){
        int initial_pos;
        int final_pos;
        players.add(new Player("player1"));
        players.add(new Player("player2"));
        players.add(new Player("player3"));
        boardToTest = new Board(players, 3, 6,
                4, 9, GameMode.SIMPLE);
        boardToTest.setup();
        initial_pos = boardToTest.getMotherNaturePosition();
        boardToTest.moveMotherNature(4);
        final_pos = (4+initial_pos)%boardToTest.getIslandList().size();
        assertEquals(final_pos, boardToTest.getMotherNaturePosition());
    }

    @Test
    /* test for the assignProfessors method */
    public void assignProfessorsTest() {
        players.add(new Player("player1"));
        players.add(new Player("player2"));
        players.add(new Player("player3"));
        boardToTest = new Board(players, 3, 6,
                4, 6, GameMode.SIMPLE);
        boardToTest.setup();
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

    /* this test attempts to simulate a turn to test the way the various methods interact
    * with each other, testing the way an island can be conquered based on the way mother nature moves,
    * not integrating the assistant card drawing which is tested along with the turn manager class */
    @Test
    public void turnFacSimileTest(){
        players.add(new Player("player1"));
        players.add(new Player("player2"));
        players.add(new Player("player3"));
        boardToTest = new Board(players, 3, 6,
                4, 9, GameMode.SIMPLE);
        boardToTest.setup();

        /* simulated turn (partial)*/
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

    /* more structured game-phase tests will be possible when testing the
    * Model class and controller classes*/
    /* out of bounds exception is thrown, i still need to understand where  */
    @Test
    public void repeatedConqueringTest() {
        players.add(new Player("player1"));
        players.add(new Player("player2"));
        boardToTest = new Board(players, 2, 8,
                3, 30, GameMode.SIMPLE);
        boardToTest.setup();
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

            boardToTest.checkForMerge("player2");
            boardToTest.moveMotherNature(1);
        }
    }

    @RepeatedTest(20)
    /* handles corner cases for conquering islands */
    public void conquerTest() {
        players.add(new Player("player1"));
        players.add(new Player("player2"));
        boardToTest = new Board(players, 2, 8,
                3, 30, GameMode.SIMPLE);
        boardToTest.setup();

        /* tests the conquering of empty islands */
        boardToTest.getMotherNature().setPosition((boardToTest.getMotherNaturePosition() + 6) % 12);
        System.out.println("is island empty? " + boardToTest.getIslandList().get(boardToTest.getMotherNaturePosition()).checkIfIslandIsEmpty());
        boardToTest.conquerIsland();

        assertEquals(false, boardToTest.getIslandList().get(boardToTest.getMotherNaturePosition()).checkIfConquered());

        /* checks the conquering of an island when two players hold the same influence - unconquered island */
        /* if the island has not yet been conquered and there is a tie between players regarding the influence,
         * the island is conquered by the current player (nickname in the conquerIsland parameters)*/
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

        /*checking if a conquered island with the same influence doesn't change owner*/
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
        System.out.println("conqueror is " + boardToTest.getIslandList().get(boardToTest.getMotherNaturePosition()).getOwner().getNickname());
    }

}
