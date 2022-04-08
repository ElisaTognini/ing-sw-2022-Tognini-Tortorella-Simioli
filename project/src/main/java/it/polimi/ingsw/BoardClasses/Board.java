package it.polimi.ingsw.BoardClasses;

import it.polimi.ingsw.BasicElements.*;
import it.polimi.ingsw.Enums.*;
import it.polimi.ingsw.Player;
import it.polimi.ingsw.SchoolBoardClasses.SchoolBoard;

import java.util.ArrayList;
import java.util.Random;

public class Board {

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
    protected TowerColor tokenColor;
    protected final int studentsInEntrance;

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
    }

    public void roundSetup(){
        studentsOnClouds();
    }

    /* THESE METHODS ARE CALLED IN RESPONSE TO ACTIONS OF THE PLAYER */

    public AssistantCard playAssistantCard(int cardID, String nickname){
        for(AssistantCardDeck d : decks){
            if(d.getOwner().getNickname().equals(nickname)){
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
                break;
            }
        }
    }

    /* this method moves a student from the entrance to the dining room */
    public void moveStudent(PawnDiscColor color, String nickname){
        for(SchoolBoard sb : schoolBoards) {
            if (sb.getOwner().getNickname().equals(nickname)) {
                sb.getDiningRoom().addStudent(sb.getEntrance().removeStudent(color));
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
    }

    /* END OF PLAYER-ACTION METHODS */


    /* THESE METHODS ACT ON THE CONSEQUENCES OF THE PLAYER'S ACTIONS */

    public void moveMotherNature(int movements){
        int position = motherNature.getPosition();
        islands.get(position).setHostsToFalse();
        motherNature.setPosition((motherNature.getPosition() + movements) % islands.size());
        islands.get(motherNature.getPosition()).setHostsToTrue();
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
    }

    /* manca la parte in cui viene aggiunta la prima torre all'isola altrimenti il metodo non funziona:
    * quando sulla fine viene fatto towersToIsland si tenta di ottenere il numero di torri già presente sull'isola,
    * che inizialmente è zero ma non viene mai aumentato
    * + corner case delle influenze uguali */
    public void conquerIsland(String nickname){
        int sum;
        int maxSum = 0;
        Player conqueror = null;
        for(SchoolBoard sb : schoolBoards){
            sum = 0;
            for(PawnDiscColor color: PawnDiscColor.values()){
                if(sb.getProfessorTable().hasProfessor(color)){
                    sum = sum + islands.get(motherNature.getPosition()).getInfluenceByColor(color);
                }
                if(islands.get(motherNature.getPosition()).checkIfConquered()){
                    if(islands.get(motherNature.getPosition()).getOwner().getNickname().equals(sb.getOwner().getNickname())){
                        sum = sum + islands.get(motherNature.getPosition()).getNumberOfTowers();
                    }
                }
                if(sum > maxSum){
                    maxSum = sum;
                    conqueror = sb.getOwner();
                }
            }
        }
        if(conqueror.getNickname().equals(nickname)){
            fixTowers(nickname);
            islands.get(motherNature.getPosition()).getsConquered(conqueror);
            for(SchoolBoard sb: schoolBoards){
                if(sb.getOwner().getNickname().equals(nickname)){
                    sb.getTowerSection().towersToIsland(islands.get(motherNature.getPosition()).getNumberOfTowers());
                }
            }

        }
    }

    protected void fixTowers(String nickname){
        if(islands.get(motherNature.getPosition()).checkIfConquered()) {
            if (!islands.get(motherNature.getPosition()).getOwner().equals(nickname)) {
                for(SchoolBoard sb: schoolBoards){
                    if(islands.get(motherNature.getPosition()).getOwner().equals(sb.getOwner())){
                        sb.getTowerSection().returnTowers(islands.get(motherNature.getPosition()).getNumberOfTowers());
                    }
                }
            }
        }
    }

    public void checkForMerge(String nickname){
        if(islands.get((motherNature.getPosition() - 1) % islands.size()).getOwner().equals(nickname)){
            if(islands.get((motherNature.getPosition() + 1) % islands.size()).getOwner().equals(nickname)){
                merge(motherNature.getPosition(), (motherNature.getPosition() - 1)%islands.size(),
                        (motherNature.getPosition() + 1)%islands.size());
            }
            merge(motherNature.getPosition(), (motherNature.getPosition() - 1)%islands.size());
        }
        if(islands.get((motherNature.getPosition() + 1) % islands.size()).getOwner().equals(nickname)){
            merge(motherNature.getPosition(), (motherNature.getPosition() + 1)%islands.size());
        }
    }

    /* it is not known at this time which of the two indexes is the greater */
    protected void merge(int index1, int index2){
        islands.get(index1).increaseNumberOfTowers(islands.get(index2).getNumberOfTowers());
        int k = 0;
        Island toKeep;
        for(PawnDiscColor c : PawnDiscColor.values()){
            k = islands.get(index1).getInfluenceByColor(c);
            for(int i = 0; i < k; i++){
                islands.get(index1).addStudent(islands.get(index2).removeStudent(c));
            }
        }
        toKeep = islands.get(index1);
        islands.remove(index2);
        motherNature.setPosition(islands.indexOf(toKeep));

    }

    protected void merge(int index1, int index2, int index3){
        merge(index1, index2);
        merge(motherNature.getPosition(), index3);
    }
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
                students[i] = studentBag.drawStudent();
            }
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
