package it.polimi.ingsw.tests;

import it.polimi.ingsw.Utils.Enums.PawnDiscColor;
import it.polimi.ingsw.Model.BasicElements.Island;
import it.polimi.ingsw.Model.BasicElements.Student;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.*;

/** Class IslandTest tests class Island */

public class IslandTest {

    private Island islandToTest = new Island(0);
    private Student student;
    private int influence;


    /** Method testStudentFunctionalities adds twelve red students to the island and calls method getInfluenceByColor
     * on that island for the influence. Finally, it prints value of attributes influence and islandToTest to screen. */
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

    /** Method testHostsMotherNature checks if value of attribute hostsMotherNature is correctly set to false
     * after initialisation. */
    @Test
    public void testHostsMotherNature(){
        assertEquals(false, islandToTest.getHost());
    }
}
