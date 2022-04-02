package it.polimi.ingsw.tests;

import it.polimi.ingsw.BasicElements.*;
import it.polimi.ingsw.Enums.PawnDiscColor;
import org.junit.Test;
import static org.junit.Assert.*;

public class IslandTest {

    private Island islandToTest = new Island(0);
    private Student student;
    private int influence;


    @Test
    public void testStudentFunctionalities(){
        for(int i=0; i<12; i++) {
            student = new Student(PawnDiscColor.RED);
            islandToTest.addStudent(student);
        }
        influence = islandToTest.getInfluenceByColor(PawnDiscColor.RED);
        System.out.println(influence);
    }

    @Test
    public void testHostsMotherNature(){
        assertEquals(true, islandToTest.getHost());
        assertEquals(true, islandToTest.checkForMotherNature());
        assertEquals(false, islandToTest.getHost());
        assertEquals(false, islandToTest.checkForMotherNature());
    }

}
