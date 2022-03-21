package it.polimi.ingsw.BasicElements;

import it.polimi.ingsw.TailoredExceptions.*;

public class CloudTile {

    /*the array stores the three students currently on the cloud
    * the ID is given when the board class instances the cloud*/
    Student[] studentsOnCloud;
    int cloudID;

    /*the array is initialized with null values to indicate that no students
    * have been added yet*/
    public CloudTile(int cloudID){
        this.cloudID = cloudID;

        studentsOnCloud = new Student[3];
        for(int i=0; i<studentsOnCloud.length; i++){
            studentsOnCloud[i] = null;
        }
    }

    /*checks if cloud has students on it - if all positions of the array are NULL, the
    * cloud tile is empty*/
    private boolean isCloudEmpty(){
        for(int i = 0; i<studentsOnCloud.length; i++){
            if(studentsOnCloud[i] != null)
                return false;
        }
        return true;
    }

    /*assigns the references to the students entered as a parameter
    * to the class attribute (this.studentsOnCloud)*/
    public void fillCloud(Student studentsToAdd[]) throws FullCloudException{

        if(isCloudEmpty() == false){
            throw new FullCloudException();
        }

        for(int i = 0; i<studentsOnCloud.length ; i++){
            studentsOnCloud[i] = studentsToAdd[i];
        }
    }

    /*copies the current state of the students into a local array that is then
    * returned, and resets the attribute array to null*/
    public Student[] retrieveFromCloud() throws EmptyException{
        Student toReturn[] = new Student[3];

        if(isCloudEmpty()){
            throw new EmptyException();
        }

        for(int i=0; i<studentsOnCloud.length; i++){
            toReturn[i] = studentsOnCloud[i];
            studentsOnCloud[i] = null;
        }

        return toReturn;
    }
}
