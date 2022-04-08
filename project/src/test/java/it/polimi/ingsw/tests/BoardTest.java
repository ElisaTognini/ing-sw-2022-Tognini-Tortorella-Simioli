package it.polimi.ingsw.tests;
import it.polimi.ingsw.BasicElements.AssistantCard;
import it.polimi.ingsw.BoardClasses.Board;
import it.polimi.ingsw.Enums.GameMode;
import it.polimi.ingsw.Enums.PawnDiscColor;
import it.polimi.ingsw.Player;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.Assert.*;

public class BoardTest {

    ArrayList<Player> players = new ArrayList<>();
    Board boardToTest;

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
        int ip;
        /* at the end of this loop each player will have moved
        * three students on one of the first three islands */
        for(Player p: players){
            ip = 0;
            while(ip < 3){
                for(PawnDiscColor c : PawnDiscColor.values()){
                    if(boardToTest.colorAvailableInEntrance(p.getNickname(), c)){
                        boardToTest.moveStudent(c, p.getNickname(), ip);
                        ip++;
                        if(boardToTest.colorAvailableInEntrance(p.getNickname(), c)){
                            boardToTest.moveStudent(c, p.getNickname());
                        }
                    }
                }
            }
        }

        /* once the students have moved both on islands and on the dining rooms,
        * this method attempts to conquer an island */
        boardToTest.moveMotherNature(5);
        boardToTest.assignProfessors();
        for(Player p: players){
            boardToTest.conquerIsland(p.getNickname());
        }
    }

}
