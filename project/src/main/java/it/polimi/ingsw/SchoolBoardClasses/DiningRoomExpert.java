package it.polimi.ingsw.SchoolBoardClasses;

import it.polimi.ingsw.Enums.PawnDiscColor;
import it.polimi.ingsw.BasicElements.Student;

public class DiningRoomExpert extends DiningRoom {

    /* Collect the five sets of students into a map that uses the PawnDiscColor enum as key and
    the HashSet of students as value
     */

    @Override
    public void addStudent(Student myStudent) throws IndexOutOfBoundsException {
        super.addStudent(myStudent);
       // if(checkIfMod3(myStudent.getColor())) assignCoin(Game.getCurrentPlayer());
    }

    public boolean checkIfMod3(PawnDiscColor color){
        boolean toRet = false;
        switch (color){

            case RED:
                if(redStudentsInDiningRoom.size()%3==0) toRet = true;
                break;
            case YELLOW:
                if(yellowStudentsInDiningRoom.size()%3==0) toRet = true;
                break;
            case PINK:
                if(pinkStudentsInDiningRoom.size()%3==0) toRet = true;
                break;
            case BLUE:
                if(blueStudentsInDiningRoom.size()%3==0) toRet = true;
                break;
            case GREEN:
                if(greenStudentsInDiningRoom.size()%3==0) toRet = true;
                break;
        }
        return toRet;
    }
}
