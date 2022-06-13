package it.polimi.ingsw.Model.BasicElements;

/** Class CloudTile contains references to an array of students present on the cloud; at the beginning of the game, the
 * array is initialised with null values to indicate that no students have been added yet, since they will be
 * added and replaced at the beginning and at the end of each round. The number of students on each island is variable,
 * based on the number of players currently playing the game, as well as the number of the clouds present in the game.
 * Each cloud has an ID, given by the Board class when it instances the clouds. */

import java.util.Arrays;

public class CloudTile {

    private Student[] studentsOnCloud;
    private final int cloudID;
    private final int numberOfStudents;

/** Constructor CloudTile creates a new instance of a cloud
 *
 * @param cloudID - of type int - the number identifying the cloud
 * @param numberOfStudents - of type int - the number of students on the island, computed according to the
 * number of players*/
    public CloudTile(int cloudID, int numberOfStudents) {
        this.cloudID = cloudID;
        this.numberOfStudents = numberOfStudents;

        studentsOnCloud = new Student[numberOfStudents];
        Arrays.fill(studentsOnCloud, null);
    }


    /** Method isCloudEmpty checks if the cloud has students on it by iterating through the array of students on the
    * cloud: if all the cells of the array are null, the cloud is indeed empty.
    *
    * @return boolean - the result of the iteration: true if the cloud is empty, false otherwise */
    public boolean isCloudEmpty() {
        for (Student student : studentsOnCloud) {
            if (student != null)
                return false;
        }
        return true;
    }


    /** Method fillCloud loads the array of students declared on the cloud with references to the actual student
    * extracted from the student bag
    *
    * @param studentsToAdd - of type Student[] - array of students to be added to the cloud */
    public void fillCloud(Student[] studentsToAdd) {
        System.arraycopy(studentsToAdd, 0, studentsOnCloud, 0, studentsOnCloud.length);
    }


    /** Method retrieveFromCloud copies the current state of the students into a local array to be returned, in order
    * to have the students removed from the cloud and then added (with another method) to the entrance of the player
    * who chose the cloud. The cloud is now empty, ready to be refilled with students at the beginning of a new round.
    *
    * @return Student[] - array of students to be removed from the island*/
    public Student[] retrieveFromCloud() {
        Student[] toReturn = new Student[numberOfStudents];

        for (int i = 0; i < studentsOnCloud.length; i++) {
            toReturn[i] = studentsOnCloud[i];
            studentsOnCloud[i] = null;
        }

        return toReturn;
    }


    /** Method toString builds a String containing all the info stored in this class
     *
     * @return String - FORMAT: cloudID, student1, student2 and student3, each separated by a blank space if
     * there are students on the cloud, otherwise String will contain only the cloud ID. */
    @Override
    public String toString(){
        StringBuilder toRet;
        toRet = new StringBuilder(String.valueOf(cloudID));
        if(!isCloudEmpty()){
            for(Student s : studentsOnCloud){
                toRet.append(" ").append(s.getColor());
            }
        }
        return toRet.toString();
    }
}
