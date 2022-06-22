package it.polimi.ingsw.tests;

import it.polimi.ingsw.Model.BasicElements.Student;
import it.polimi.ingsw.Model.BasicElements.StudentBag;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.*;

/** Class StudentBagTest tests class StudentBag */

public class StudentBagTest {

    private StudentBag bag = new StudentBag(120);

    /** Method testDraw checks if method drawStudent and removeStudent work properly, respectively extracting and
     * removing the student extracted from the bag. For each student extracted, it increases a counter value in order
     * to see if there are 24 students for each color. */
    @Test
    public void testDraw(){

        Student stud;
        int countP=0, countY=0, countG=0, countR=0, countB=0;

        for(int i= bag.availableStudents()-1; i>=0 ; i--){
            stud = bag.drawStudent();
            System.out.print(stud.getColor() + " ");
            System.out.println(i);

            switch(stud.getColor()){
                case RED:
                    countR++;
                    break;
                case YELLOW:
                    countY++;
                    break;
                case GREEN:
                    countG++;
                    break;
                case PINK:
                    countP++;
                    break;
                case BLUE:
                    countB++;
                    break;
            }

        }

        if(bag.availableStudents() == 0)
            System.out.print("all students removed!");

        assertEquals(24, countB);
        assertEquals(24, countP);
        assertEquals(24, countR);
        assertEquals(24, countY);
        assertEquals(24, countG);

    }
}
