package it.polimi.ingsw.BasicElements;

import it.polimi.ingsw.TailoredExceptions.*;

public class CloudTile {

    /*the array stores the three students currently on the cloud
    * the ID is given when the board class instances the cloud*/
    private Student[] studentsOnCloud;
    private final int cloudID;
    private final int numberOfStudents;

    /*the array is initialized with null values to indicate that no students
    * have been added yet*/
    public CloudTile(int cloudID, int numberOfStudents){
        this.cloudID = cloudID;
        this.numberOfStudents = numberOfStudents;

        studentsOnCloud = new Student[numberOfStudents];
        for(int i=0; i<studentsOnCloud.length; i++){
            studentsOnCloud[i] = null;
        }
    }

    /*checks if cloud has students on it - if all positions of the array are NULL, the
    * cloud tile is empty*/
    public boolean isCloudEmpty(){
        for(int i = 0; i<studentsOnCloud.length; i++){
            if(studentsOnCloud[i] != null)
                return false;
        }
        return true;
    }

    /*assigns the references to the students entered as a parameter
    * to the class attribute (this.studentsOnCloud)*/
    public void fillCloud(Student studentsToAdd[]){

        for(int i = 0; i<studentsOnCloud.length ; i++){
            studentsOnCloud[i] = studentsToAdd[i];
        }
    }

    /*copies the current state of the students into a local array that is then
    * returned, and resets the attribute array to null*/
    public Student[] retrieveFromCloud(){
        Student toReturn[] = new Student[numberOfStudents];

        for(int i=0; i<studentsOnCloud.length; i++){
            toReturn[i] = studentsOnCloud[i];
            studentsOnCloud[i] = null;
        }

        return toReturn;
    }
}
