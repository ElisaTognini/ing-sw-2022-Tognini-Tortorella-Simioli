package it.polimi.ingsw.tests;

import it.polimi.ingsw.BasicElements.*;
import org.junit.Test;

public class IslandTest {

    private Island islandToTest = new Island(0);
    private Student student;

    @Test
    public void testAddStudent(){
        islandToTest.addStudent(student);
    }

}
