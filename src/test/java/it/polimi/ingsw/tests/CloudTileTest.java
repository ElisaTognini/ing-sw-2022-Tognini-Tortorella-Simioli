package it.polimi.ingsw.tests;

import it.polimi.ingsw.Utils.Enums.PawnDiscColor;
import it.polimi.ingsw.Model.BasicElements.CloudTile;
import it.polimi.ingsw.Model.BasicElements.Student;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.*;

/** Class CloudTileTest test class CloudTile */

public class CloudTileTest {

    private CloudTile cloud = new CloudTile(0, 4);
    private Student[] array;

    /** Method testIsCloudEmpty tests  the cloud is actually empty after initialisation */
    @Test
    public void testIsCloudEmpty() {
        assertEquals (true, cloud.isCloudEmpty());
    }


    /** Method testAddStudent tests if addStudent works properly: it should add the first three students
     * in the array and throw an exception for the last one*/
    @Test
    public void testAddStudent(){
        array = new Student[4];
            for (int i = 0; i < 3; i++) {
                array[i] = new Student(PawnDiscColor.RED);
                cloud.fillCloud(array);
                array = cloud.retrieveFromCloud();
                assertEquals(true, cloud.isCloudEmpty());
            }
            cloud.fillCloud(array);
            cloud.fillCloud(array);
    }


    /** Method testRetrieveStudent tests if retrieveStudentFromCloud works properly: it should retrieve the first
     * three students in the array and throws an exception for the last one because it doesn't exist. */
    @Test
    public void testRetrieveStudentFromCloud(){
            array = new Student[4];
            for (int i = 0; i < 3; i++) {
                array[i] = new Student(PawnDiscColor.RED);
            }
            cloud.fillCloud(array);
            cloud.retrieveFromCloud();
            System.out.print("Student retrieved!\n");
    }
}