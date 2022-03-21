package it.polimi.ingsw;

import it.polimi.ingsw.BasicElements.Student;
import it.polimi.ingsw.BasicElements.StudentBag;
import org.junit.Test;
import static org.junit.Assert.*;

public class StudentBagTest {

    private StudentBag bag = new StudentBag();

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
