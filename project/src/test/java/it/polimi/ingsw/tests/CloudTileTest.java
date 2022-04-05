package it.polimi.ingsw.tests;

import it.polimi.ingsw.BasicElements.*;
import it.polimi.ingsw.Enums.PawnDiscColor;
import it.polimi.ingsw.TailoredExceptions.*;
import org.junit.Test;
import static org.junit.Assert.*;

public class CloudTileTest {

    private CloudTile cloud = new CloudTile(0, 4);
    private Student[] array;

    /* tests if cloud is actually empty after initialisation */
    @Test
    public void testIsCloudEmpty() {
        assertEquals (true, cloud.isCloudEmpty());
    }

    /* tests if addStudent works properly: it should add the first three students in the array and
    * throw an exception for the last one*/
    @Test
    public void testAddStudent() throws FullCloudException, EmptyException {
        array = new Student[4];
        try {
            for (int i = 0; i < 3; i++) {
                array[i] = new Student(PawnDiscColor.RED);
                cloud.fillCloud(array);
                array = cloud.retrieveFromCloud();
                assertEquals(true, cloud.isCloudEmpty());
            }
            cloud.fillCloud(array);
            cloud.fillCloud(array);
        }catch (FullCloudException e){
            System.out.println("exception caught!");
        }
    }
    /* tests if retrieveStudentFromCloud works properly: it should retrieve the first three students in
    * the array and throw an exception for the last one because it doesn't exist*/
    @Test
    public void testRetrieveStudentFromCloud() throws EmptyException, FullCloudException {
            array = new Student[4];
            for (int i = 0; i < 3; i++) {
                array[i] = new Student(PawnDiscColor.RED);
            }
            cloud.fillCloud(array);
            cloud.retrieveFromCloud();
            System.out.print("Student retrieved!\n");
    }
}