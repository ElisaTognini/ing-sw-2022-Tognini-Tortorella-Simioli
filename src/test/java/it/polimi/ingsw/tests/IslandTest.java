package it.polimi.ingsw.tests;

import it.polimi.ingsw.Utils.Enums.PawnDiscColor;
import it.polimi.ingsw.Model.BasicElements.Island;
import it.polimi.ingsw.Model.BasicElements.Student;
import org.junit.jupiter.api.Test;
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
        System.out.println(islandToTest);
    }

    @Test
    public void testHostsMotherNature(){
        assertEquals(false, islandToTest.getHost());
        assertEquals(false, islandToTest.getHost());
    }

}
