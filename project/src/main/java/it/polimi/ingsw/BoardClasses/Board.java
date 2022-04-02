package it.polimi.ingsw.BoardClasses;

import it.polimi.ingsw.BasicElements.*;
import it.polimi.ingsw.Enums.*;
import it.polimi.ingsw.Player;
import it.polimi.ingsw.SchoolBoardClasses.SchoolBoard;
import it.polimi.ingsw.TailoredExceptions.*;

import java.util.ArrayList;
import java.util.Random;

public class Board {

    private ArrayList<Player> players;
    private ArrayList<SchoolBoard> schoolBoards;
    private ArrayList<Island> islands;
    private StudentBag studentBag;
    private ArrayList<CloudTile> clouds;
    private ArrayList<Professor> professors;
    private MotherNature motherNature;
    private ArrayList<AssistantCardDeck> decks;
    private final int numberOfClouds;
    private final int numberOfTowers;
    private final int studentsOnClouds;
    private final GameMode mode;
    private TowerColor tokenColor;
    private final int studentsInEntrance;

    /* numberOfClouds and numberOfTowers will be computed by the Model class
    *  based on the number of players in the lobby */
    public Board(ArrayList<Player> players, int numberOfClouds, int numberOfTowers, int studentsOnClouds,
                 int studentsInEntrance,GameMode mode){
        this.players = new ArrayList<>();
        this.players.addAll(players);
        this.schoolBoards = new ArrayList<>();
        this.islands = new ArrayList<>();
        this.professors = new ArrayList<>();
        this.clouds = new ArrayList<>();
        this.decks = new ArrayList<>();
        this.motherNature = new MotherNature();
        this.numberOfClouds = numberOfClouds;
        this.numberOfTowers = numberOfTowers;
        this.studentsOnClouds = studentsOnClouds;
        this.mode = mode;
        this.studentsInEntrance = studentsInEntrance;
    }

    public void setup() throws EmptyException, FullCloudException{
        this.studentBag = new StudentBag(10);
        placeIslands();
        placeMotherNature();
        placeClouds();
        placeStudents();
        studentBag = new StudentBag(120);
        studentsOnClouds();
        initializeProfessors();
        initializeSchoolBoards();
        initializeDecks();
    }

    public void roundSetup() throws EmptyException, FullCloudException{
        studentsOnClouds();
    }

    /* THESE METHODS ARE CALLED IN RESPONSE TO ACTIONS OF THE PLAYER */

    public AssistantCard playAssistantCard(int cardID, String nickname) throws InvalidCardActionException{
        for(AssistantCardDeck d : decks){
            if(d.getOwner().getNickname().equals(nickname)){
                return d.drawCard(cardID);
            }
        }
        return null;
    }

    /* this method moves a student from the entrance to a specified island */
    public void moveStudent(PawnDiscColor color, String nickname, int islandID) throws EmptyException{
        for(SchoolBoard sb : schoolBoards){
            if(sb.getOwner().getNickname().equals(nickname)){
                islands.get(islandID).addStudent(sb.getEntrance().removeStudent(color));
                break;
            }
        }
    }

    /* this method moves a student from the entrance to the dining room */
    public void moveStudent(PawnDiscColor color, String nickname) throws EmptyException{
        for(SchoolBoard sb : schoolBoards) {
            if (sb.getOwner().getNickname().equals(nickname)) {
                sb.getDiningRoom().addStudent(sb.getEntrance().removeStudent(color));
            }
        }
    }

    public void chooseCloudTile(String nickname, int cloudID) throws EmptyException{
        Student[] students;
        students = clouds.get(cloudID).retrieveFromCloud();
        for(SchoolBoard sb : schoolBoards){
            if(sb.getOwner().getNickname().equals(nickname)){
                for(int i = 0; i < studentsOnClouds; i++){
                    sb.getEntrance().addStudent(students[i]);
                }
            }
        }
    }

    /* END OF PLAYER-ACTION METHODS */


    /* THESE METHODS ACT ON THE CONSEQUENCES OF THE PLAYER'S ACTIONS */

    public void moveMotherNature(int movements){
        int position = motherNature.getPosition();
        islands.get(position).setHostsToFalse();
        motherNature.setPosition((motherNature.getPosition() + movements) % 12);
        islands.get(motherNature.getPosition()).setHostsToTrue();
    }

    public void assignProfessors(){
        int maxInfluence = 0;
        int temp = 0;
        String maxPlayer = "";
        for(PawnDiscColor c : PawnDiscColor.values()){
            for(SchoolBoard sb : schoolBoards){
                temp = sb.getDiningRoom().influenceForProf(c);
                if(temp > maxInfluence){
                    maxInfluence = temp;
                    maxPlayer = sb.getOwner().getNickname();
                }
            }
            for(SchoolBoard sb : schoolBoards){
                if(!sb.getOwner().getNickname().equals(maxPlayer)){
                    sb.getProfessorTable().removeProfessor(c);
                }
                else
                    sb.getProfessorTable().addProfessor(c);
            }
        }
    }

    public void conquerIsland(String nickname){
        for(Professor p : professors){

        }
    }


    /* END OF METHODS FOLLOWING THE PLAYER'S ACTIONS */

    /* SERVICE METHODS FOR INITIALIZATION */

    private void placeIslands(){
        for(int i = 0; i < 12; i++){
            islands.add(new Island(i));
        }
    }

    /* this method generates a random index indicating the island on which mother nature will be initially placed */
    private void placeMotherNature(){
        Random rand = new Random();
        int randomIndex = rand.nextInt(islands.size());
        islands.get(randomIndex).setHostsToTrue();
        motherNature.setPosition(randomIndex);
    }

    /* this method initializes the clouds ArrayList, the size of which is based on the number of players */
    private void placeClouds(){
        for(int i = 0; i < numberOfClouds; i++){
            clouds.add(new CloudTile(i, studentsOnClouds));
        }
    }

    /* this method places the initial ten students on the available islands
    *  (those that don't host mother nature or are opposite to the island that does) */
    private void placeStudents() throws EmptyException {
        for(Island i : islands){
            if(!i.getHost() || !(i.getIslandID() % 6 == motherNature.getPosition() % 6)){
                i.addStudent(studentBag.drawStudent());
            }
        }
    }

    private void studentsOnClouds() throws EmptyException, FullCloudException {
        Student[] students = new Student[studentsOnClouds];
        for(CloudTile c : clouds){
            for(int i = 0; i < studentsOnClouds; i++){
                students[i] = studentBag.drawStudent();
            }
            c.fillCloud(students);
        }
    }

    private void initializeSchoolBoards() throws EmptyException{
        for(int i=0; i<players.size(); i++){
            schoolBoards.add(new SchoolBoard(numberOfTowers, tokenColor.convert(i), mode, players.get(i)));
            for(int j = 0; j<studentsInEntrance; j++){
                schoolBoards.get(i).getEntrance().addStudent(studentBag.drawStudent());
            }
        }
    }

    private void initializeProfessors(){
        for(PawnDiscColor c : PawnDiscColor.values()){
            professors.add(new Professor(c));
        }
    }

    private void initializeDecks(){
        for(Player p : players){
            decks.add(new AssistantCardDeck(p));
        }
    }

    /* END OF SERVICE METHODS FOR INITIALIZATION */

}
