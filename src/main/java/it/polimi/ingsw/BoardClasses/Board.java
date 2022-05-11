package it.polimi.ingsw.BoardClasses;

import it.polimi.ingsw.BasicElements.*;
import it.polimi.ingsw.Enums.*;
import it.polimi.ingsw.Player;
import it.polimi.ingsw.SchoolBoardClasses.SchoolBoard;
import it.polimi.ingsw.Server.ViewUpdateMessageWrapper;

import java.util.*;

public class Board extends Observable{

    protected ArrayList<Player> players;
    protected ArrayList<SchoolBoard> schoolBoards;
    protected ArrayList<Island> islands;
    protected StudentBag studentBag;
    protected ArrayList<CloudTile> clouds;
    protected ArrayList<Professor> professors;
    protected MotherNature motherNature;
    protected ArrayList<AssistantCardDeck> decks;
    protected final int numberOfClouds;
    protected final int numberOfTowers;
    protected final int studentsOnClouds;
    protected final GameMode mode;
    protected final int studentsInEntrance;
    protected boolean lastRound;
    protected boolean isGameOver;

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
        this.lastRound = false;
        this.isGameOver = false;
    }

    public void setup() {
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
        /* send all players the setup board via the MessageWrapper */
        if(mode.equals(GameMode.SIMPLE))
            notifyObservers(ActionType.SETUP);
    }

    public void roundSetup(){
        studentsOnClouds();
        if(mode.equals(GameMode.SIMPLE))
            notifyObservers(ActionType.ROUND_SETUP);
    }

    /* THESE METHODS ARE CALLED IN RESPONSE TO ACTIONS OF THE PLAYER */

    public AssistantCard playAssistantCard(int cardID, String nickname){
        for(AssistantCardDeck d : decks){
            if(d.getOwner().getNickname().equals(nickname)){
                d.getOwner().setCardPickedToTrue();
                return d.drawCard(cardID);
            }
        }
        return null;
    }

    /* this method moves a student from the entrance to a specified island */
    public void moveStudent(PawnDiscColor color, String nickname, int islandID){
        for(SchoolBoard sb : schoolBoards){
            if(sb.getOwner().getNickname().equals(nickname)){
                islands.get(islandID).addStudent(sb.getEntrance().removeStudent(color));
                notifyObservers(ActionType.ADD_STUDENT_ISLAND);
                break;
            }
        }
    }

    /* this method moves a student from the entrance to the dining room */
    public void moveStudent(PawnDiscColor color, String nickname){
        for(SchoolBoard sb : schoolBoards) {
            if (sb.getOwner().getNickname().equals(nickname)) {
                sb.getDiningRoom().addStudent(sb.getEntrance().removeStudent(color));
                notifyObservers(ActionType.ADD_STUDENT_DR);
            }
        }
    }

    public void chooseCloudTile(String nickname, int cloudID){
        Student[] students;
        students = clouds.get(cloudID).retrieveFromCloud();
        for(SchoolBoard sb : schoolBoards){
            if(sb.getOwner().getNickname().equals(nickname)){
                for(int i = 0; i < studentsOnClouds; i++){
                    sb.getEntrance().addStudent(students[i]);
                }
            }
        }
        notifyObservers(ActionType.EMPTY_CLOUD);
    }

    /* END OF PLAYER-ACTION METHODS */


    /* THESE METHODS ACT ON THE CONSEQUENCES OF THE PLAYER'S ACTIONS */

    public void moveMotherNature(int movements){
        int position = motherNature.getPosition();
        islands.get(position).setHostsToFalse();
        motherNature.setPosition((motherNature.getPosition() + movements) % islands.size());
        islands.get(motherNature.getPosition()).setHostsToTrue();
        notifyObservers(ActionType.MOVE_MN);
    }


    /* this method runs through the possible colors and
    * - initializes player and influence to default values
    *  - iterates schoolboards and finds the player with the max influence of the current color
    *  - assigns the professor to the player with the maximum influence of said color
    * corner case: if two players have the same influence for a color, who gets the professor? needs rule clarification */
    public void assignProfessors(){
        int maxInfluence = 0;
        int temp = 0;
        String maxPlayer = "";
        for(PawnDiscColor c : PawnDiscColor.values()){
            maxPlayer = "";
            maxInfluence = 0;
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
        notifyObservers(ActionType.ASSIGN_PROFESSORS);
    }

    /*  if two players have the same influence over an unconquered island, the island will not be
    * conquered. If two players hold the same influence over a conquered island, the island
    * will not change its owner.*/
    public void conquerIsland() {
        int[] sum = new int[players.size()];
        int i = 0;
        int maxInfluence;
        boolean deuce = false;
        Player conqueror = null;
        /*if at the end of the method the conqueror is still null, nobody conquers*/
        for (SchoolBoard sb : schoolBoards) {
            sum[i] = 0;
            for (PawnDiscColor color : PawnDiscColor.values()) {
                if (sb.getProfessorTable().hasProfessor(color)) {
                    sum[i] = sum[i] + islands.get(motherNature.getPosition()).getInfluenceByColor(color);
                }
                if (islands.get(motherNature.getPosition()).checkIfConquered()) {
                    if (islands.get(motherNature.getPosition()).getOwner().getNickname().equals(sb.getOwner().getNickname())) {
                        sum[i] = sum[i] + islands.get(motherNature.getPosition()).getNumberOfTowers();
                    }
                }
            }
            i++;
        }

        /* decides who conquers the island */
        i = 0;
        maxInfluence = 0;
        for(SchoolBoard sb : schoolBoards){
            if(sum[i] > maxInfluence){
                maxInfluence = sum[i];
                conqueror = sb.getOwner();
                deuce = false;
            }
            else if(sum[i] == maxInfluence && maxInfluence != 0 ){
                deuce = true;
            }
            i++;
        }

        if(deuce || conqueror == null){
            return;
        }

        if(islands.get(motherNature.getPosition()).checkIfConquered()){
            if(islands.get(motherNature.getPosition()).getOwner().equals(conqueror)){
                return;
            }
            //returning towers to old owner
            getPlayerSchoolBoard(islands.get(motherNature.getPosition()).getOwner().getNickname()).getTowerSection().returnTowers(
                    islands.get(motherNature.getPosition()).getNumberOfTowers());
                    notifyObservers(ActionType.REMOVE_TOWER);
            //retrieving towers from conqueror's schoolboard
            if(!(getPlayerSchoolBoard(conqueror.getNickname()).getTowerSection().getNumberOfTowers() <
                islands.get(motherNature.getPosition()).getNumberOfTowers())) {
                getPlayerSchoolBoard(conqueror.getNickname()).getTowerSection().towersToIsland(islands.get(motherNature.getPosition()).getNumberOfTowers());
            }else{
                isGameOver = true;
                return;
            }
            }

        if(!islands.get(motherNature.getPosition()).checkIfConquered()){
            getPlayerSchoolBoard(conqueror.getNickname()).getTowerSection().towersToIsland(1);
            islands.get(motherNature.getPosition()).increaseNumberOfTowers(1);
        }

        islands.get(motherNature.getPosition()).getsConquered(conqueror);
        notifyObservers(ActionType.CONQUER_ISLAND);
    }

    /* this method checks whether the adjacent islands are also conquered by the current players. It handles
    * the case in which the nearing island's owner is null. This method, if islands need merging,
    * calls the merge method which handles the moving of students and towers basing on indexes (using an arraylist)*/
    public void checkForMerge(String nickname){
        boolean notNull1 = false;
        boolean notNullMinus1 = false;
        if(!(islands.get((motherNature.getPosition() + 1) % islands.size()).getOwner() == null))
            notNull1 = true;
        if(!(islands.get((motherNature.getPosition() - 1 + islands.size()) % islands.size()).getOwner() == null))
            notNullMinus1 = true;

        if(notNull1 && notNullMinus1){
            if (islands.get((motherNature.getPosition() - 1 + islands.size()) % islands.size()).getOwner().getNickname().equals(nickname)) {
                if (islands.get((motherNature.getPosition() + 1) % islands.size()).getOwner().getNickname().equals(nickname)) {
                    merge(motherNature.getPosition(), (motherNature.getPosition() - 1 + islands.size()) % islands.size(),
                            (motherNature.getPosition() + 1) % islands.size());
                }
            }
        }
        if(notNull1){
            if (islands.get((motherNature.getPosition() + 1) % islands.size()).getOwner().getNickname().equals(nickname)) {
                merge(motherNature.getPosition(), (motherNature.getPosition() + 1) % islands.size());
            }
        }

        if(notNullMinus1){
            if(islands.get((motherNature.getPosition() - 1 + islands.size()) % islands.size()).getOwner().getNickname().equals(nickname)){
                merge(motherNature.getPosition(), (motherNature.getPosition() - 1 + islands.size()) % islands.size());
            }
        }
    }

    /* it is not known at this time which of the two indexes is the greater */
    protected void merge(int index1, int index2){
        islands.get(index1).increaseNumberOfTowers(islands.get(index2).getNumberOfTowers());
        int k = 0;
        Island toKeep;
        for(PawnDiscColor c : PawnDiscColor.values()){
            k = islands.get(index2).getInfluenceByColor(c);
            for(int i = 0; i < k; i++){
                islands.get(index1).addStudent(islands.get(index2).removeStudent(c));
            }
        }
        toKeep = islands.get(index1);
        islands.remove(index2);
        motherNature.setPosition(islands.indexOf(toKeep));
        notifyObservers(ActionType.MERGE_ISLANDS);
    }

    protected void merge(int index1, int index2, int index3){
        merge(index1, index2);
        merge(motherNature.getPosition(), index3);
    }

    /* this method is only used in Expert mode, it is declared here for overriding*/
    public void useCard(Object o, String nickname, int cardID){}
    /* END OF METHODS FOLLOWING THE PLAYER'S ACTIONS */

    /* SERVICE METHODS FOR INITIALIZATION */
    protected void placeIslands(){
        for(int i = 0; i < 12; i++){
            islands.add(new Island(i));
        }
    }

    /* this method generates a random index indicating the island on which mother nature will be initially placed */
    protected void placeMotherNature(){
        Random rand = new Random();
        int randomIndex = rand.nextInt(islands.size());
        islands.get(randomIndex).setHostsToTrue();
        motherNature.setPosition(randomIndex);
    }

    /* this method initializes the clouds ArrayList, the size of which is based on the number of players */
    protected void placeClouds(){
        for(int i = 0; i < numberOfClouds; i++){
            clouds.add(new CloudTile(i, studentsOnClouds));
        }
    }

    /* this method places the initial ten students on the available islands
    *  (those that don't host mother nature or are opposite to the island that does) */
    protected void placeStudents(){
        int pos1, pos2;
        for(Island i : islands){
            pos1 = i.getIslandID() % 6;
            pos2 = motherNature.getPosition() % 6;
            if(!i.getHost()){
                if(!(pos1 == pos2)) {
                    i.addStudent(studentBag.drawStudent());
                }
            }
        }
    }

    protected void studentsOnClouds(){
        Student[] students = new Student[studentsOnClouds];
        for(CloudTile c : clouds){
            for(int i = 0; i < studentsOnClouds; i++){
                if(!studentBag.checkIfStudentBagEmpty()) {
                    students[i] = studentBag.drawStudent();
                } else {
                    lastRound = true;
                }
            }
            if(lastRound) return;
            c.fillCloud(students);
        }
    }

    protected void initializeSchoolBoards(){
        TowerColor c = TowerColor.BLACK;
        for(int i=0; i<players.size(); i++){
            if(i==0) c = TowerColor.BLACK;
            else if(i == 1) c = TowerColor.WHITE;
            else if (i == 2) c = TowerColor.GREY;
            schoolBoards.add(new SchoolBoard(studentsInEntrance ,numberOfTowers,c , mode, players.get(i)));
            for(int j = 0; j<studentsInEntrance; j++){
                schoolBoards.get(i).getEntrance().addStudent(studentBag.drawStudent());
            }
        }
    }

    protected void initializeProfessors(){
        for(PawnDiscColor c : PawnDiscColor.values()){
            professors.add(new Professor(c));
        }
    }

    protected void initializeDecks(){
        for(Player p : players){
            decks.add(new AssistantCardDeck(p));
        }
    }

    /* END OF SERVICE METHODS FOR INITIALIZATION */

    /* GETTER METHODS */
    public StudentBag getStudentBag(){
        return studentBag;
    }

    public ArrayList<Island> getIslandList(){
        return islands;
    }

    public SchoolBoard getPlayerSchoolBoard(String nickname){
        for(SchoolBoard sb : schoolBoards){
            if(sb.getOwner().getNickname().equals(nickname)){
                return sb;
            }
        }
        return null;
    }

    public ArrayList<SchoolBoard> getSchoolBoards(){
        return schoolBoards;
    }
    public int getMotherNaturePosition(){
        return motherNature.getPosition();
    }

    public ArrayList<AssistantCardDeck> getDecks(){
        return decks;
    }

    public MotherNature getMotherNature(){
        return motherNature;
    }

    public CloudTile getCloud(int cloudID){
        return clouds.get(cloudID);
    }

    public boolean allCloudsPicked(){
        for(CloudTile c : clouds){
            if(!c.isCloudEmpty()){
                return false;
            }
        }
        return true;
    }

    public AssistantCardDeck getPlayersDeck(String nickname){
        for(AssistantCardDeck deck : decks){
            if(deck.getOwner().getNickname().equals(nickname))
                return deck;
        }
        return null;
    }

    public boolean isLastRound(){ return lastRound; }

    public void setLastRound(){ lastRound = true; }

    public boolean isGameOver(){ return isGameOver;}

    public int getNumberOfClouds(){return numberOfClouds;}

    public void setMessageWrapper(ViewUpdateMessageWrapper messageWrapper){
        addObserver(messageWrapper);
    }

    /* END OF GETTER METHODS */

    /* METHODS FOR LEGAL ACTION CHECKS */
    public boolean isDeckEmpty(String nickname){
        for(AssistantCardDeck deck : decks){
            if(nickname.equals(deck.getOwner().getNickname())){
                return deck.checkIfDeckIsEmpty();
            }
        }
        return false;
    }

    public boolean isCardInDeck(String nickname, int cardID){
        for(AssistantCardDeck deck: decks){
            if(nickname.equals(deck.getOwner().getNickname())){
                return deck.checkIfCardIsPresent(cardID);
            }
        }
        return false;
    }

    public boolean colorAvailableInEntrance(String nickname, PawnDiscColor color){
        return getPlayerSchoolBoard(nickname).getEntrance().isColorAvailable(color);
    }

}
