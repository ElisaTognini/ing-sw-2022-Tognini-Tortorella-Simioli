package it.polimi.ingsw.Model.BoardClasses;

import it.polimi.ingsw.Model.BasicElements.*;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Model.SchoolBoardClasses.SchoolBoard;
import it.polimi.ingsw.Utils.Enums.GameMode;
import it.polimi.ingsw.Utils.Enums.PawnDiscColor;
import it.polimi.ingsw.Utils.Enums.TowerColor;

import java.util.*;

/** Class Board controls most of the logic of the game model side, indeed it contains references to all the basic
 * elements of the game (decks, clouds, islands, mother nature, student bag, students and professors), the players and
 * their school boards, the mode the game is being played, also keeping track of the eventual winner. */

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

    /** Constructor Board creates new instance of the board. Parameters numberOfClouds, numberOfTowers and
     * studentsInEntrance will be computed by the Model class based on the number of players in the lobby.
     *
     * @param players - of type ArrayList<Players> - the players currently playing the game.
     * @param numberOfClouds - of type int - the number of clouds that will be added to the board.
     * @param numberOfTowers - of type int - the number of towers to add in each player's tower section.
     * @param studentsOnClouds - of type int - the number of students to add on each cloud at the beginning of every
     *                         round
     * @param studentsInEntrance - of type int - the number of towers to add in each player's entrance.
     * @param mode - of type GameMode - the mode chosen by the players to play the game.
     *
     * */
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


    /** Method setup is responsible for the setup of the board for every new match: it places all the basic elements
     * for the game, initialises professors, decks and players' school boards and sends all the player the setup
     * board via MessageWrapper. */
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

        if(mode.equals(GameMode.SIMPLE)) {
            setChanged();
            notifyObservers();
        }
    }


    /** Method setup is responsible for the setup of the board for every new match: refills clouds with
     * students from the student bag */
    public void roundSetup(){
        studentsOnClouds();
        if(mode.equals(GameMode.SIMPLE)) {
            setChanged();
            notifyObservers();
        }
    }

    /* METHODS CALLED IN RESPONSE TO ACTIONS OF THE PLAYER */

    /** playAssistantCard returns the assistant card chosen by the player during their planning phase
     *
     * @param cardID - of type int - the ID of the card chosen by the player
     * @param nickname - of type String - the player who played the card
     *
     * @return AssistantCard - the card picked by the player
     * */
    public AssistantCard playAssistantCard(int cardID, String nickname){
        for(AssistantCardDeck d : decks){
            if(d.getOwner().getNickname().equals(nickname)){
                d.getOwner().setCardPickedToTrue();
                setChanged();
                return d.drawCard(cardID);
            }
        }
        return null;
    }


    /** Method moveStudent moves selected student from the player's entrance to a specified island
     *
     * @param color - of type PawnDiscColor - the color of the chosen student
     * @param nickname - of type String - the player who is performing the action
     * @param islandID - of type int - the ID of the island to which the student is being moved
     * */
    public void moveStudent(PawnDiscColor color, String nickname, int islandID){
        for(SchoolBoard sb : schoolBoards){
            if(sb.getOwner().getNickname().equals(nickname)){
                islands.get(islandID).addStudent(sb.getEntrance().removeStudent(color));
                setChanged();
                notifyObservers();
                break;
            }
        }
    }



    /** Method moveStudent moves selected student from the player's entrance to their dining room
     *
     * @param color - of type PawnDiscColor - the color of the chosen student
     * @param nickname - of type String - the player who is performing the action
     * */
    public void moveStudent(PawnDiscColor color, String nickname){
        for(SchoolBoard sb : schoolBoards) {
            if (sb.getOwner().getNickname().equals(nickname)) {
                sb.getDiningRoom().addStudent(sb.getEntrance().removeStudent(color));
                setChanged();
                notifyObservers();
            }
        }
    }


    /** chooseCloudTile takes the students from the selected cloud and adds them to the player's entrance
     *
     * @param nickname of type String - the player performing the action
     * @param cloudID of type int - cloud ID */
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
        setChanged();
        notifyObservers();
    }
    /* END OF PLAYER-ACTION METHODS */


    /* METHODS ACTING ON THE CONSEQUENCES OF THE PLAYER'S ACTIONS */

    public void moveMotherNature(int movements){
        int position = motherNature.getPosition();
        islands.get(position).setHostsToFalse();
        motherNature.setPosition((motherNature.getPosition() + movements) % islands.size());
        islands.get(motherNature.getPosition()).setHostsToTrue();
        setChanged();
        notifyObservers();
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
            maxPlayer = ownerToCompare(c);
            maxInfluence = influenceToCompare(c);
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
        setChanged();
        notifyObservers();
    }

    private int influenceToCompare(PawnDiscColor c){
        for(SchoolBoard sb : schoolBoards){
            if(sb.getProfessorTable().hasProfessor(c))
                return sb.getDiningRoom().influenceForProf(c);
        }
        return 0;
    }

    private String ownerToCompare(PawnDiscColor c){
        for(SchoolBoard sb : schoolBoards){
            if(sb.getProfessorTable().hasProfessor(c)){
                return sb.getOwner().getNickname();
            }
        }
        return "";
    }

    /*  if two players have the same influence over an unconquered island, the island will not be
    * conquered. If two players hold the same influence over a conquered island, the island
    * will not change its owner.*/
    public void conquerIsland() {
        int[] sum = new int[players.size()];
        int i;
        int maxInfluence = 0;
        boolean deuce = false;
        Player conqueror = null;
        /*if at the end of the method the conqueror is still null, nobody conquers*/

        for (i = 0; i < sum.length; i++) {
            sum[i] = 0;
            for (PawnDiscColor color : PawnDiscColor.values()) {
                if (schoolBoards.get(i).getProfessorTable().hasProfessor(color)) {
                    sum[i] = sum[i] + islands.get(motherNature.getPosition()).getInfluenceByColor(color);
                }
            }

            if (islands.get(motherNature.getPosition()).checkIfConquered()) {
                if (islands.get(motherNature.getPosition()).getOwner().getNickname().equals(schoolBoards.get(i).getOwner().getNickname())) {
                    sum[i] = sum[i] + islands.get(motherNature.getPosition()).getNumberOfTowers();
                    maxInfluence = sum[i];
                    conqueror = schoolBoards.get(i).getOwner();
                }
            }
        }

        /* decides who conquers the island */
        for (i = 0; i < sum.length; i++ ){
            if (sum[i] > maxInfluence) {
                maxInfluence = sum[i];
                conqueror = schoolBoards.get(i).getOwner();
                deuce = false;
            } else if (sum[i] == maxInfluence && maxInfluence != 0) {
                deuce = true;
            }
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
            //retrieving towers from conqueror's schoolboard
            if(!(getPlayerSchoolBoard(conqueror.getNickname()).getTowerSection().getNumberOfTowers() <
                islands.get(motherNature.getPosition()).getNumberOfTowers())) {
                getPlayerSchoolBoard(conqueror.getNickname()).getTowerSection().towersToIsland(islands.get(motherNature.getPosition()).getNumberOfTowers());
            } else {
                isGameOver = true;
                return;
                }
            }

        if(!(islands.get(motherNature.getPosition()).checkIfConquered())){
            getPlayerSchoolBoard(conqueror.getNickname()).getTowerSection().towersToIsland(1);
            islands.get(motherNature.getPosition()).increaseNumberOfTowers(1);
        }

        islands.get(motherNature.getPosition()).getsConquered(conqueror);
        setChanged();
        notifyObservers();
    }

    /* this method checks whether the adjacent islands are also conquered by the current players. It handles
    * the case in which the nearing island's owner is null. This method, if islands need merging,
    * calls the merge method which handles the moving of students and towers basing on indexes (using an arraylist)*/
    public void checkForMerge(){
        boolean notNull1 = false;
        boolean notNullMinus1 = false;
        String owner1 = " ";
        String owner2 = " ";

        if(!(islands.get((motherNature.getPosition() + 1) % islands.size()).getOwner() == null)) {
            notNull1 = true;
            owner1 = islands.get((motherNature.getPosition() + 1) % islands.size()).getOwner().getNickname();
        }
        if(!(islands.get((motherNature.getPosition() - 1 + islands.size()) % islands.size()).getOwner() == null)) {
            notNullMinus1 = true;
            owner2 = islands.get((motherNature.getPosition() - 1 + islands.size()) % islands.size()).getOwner().getNickname();
        }

        if((notNull1 && notNullMinus1) && (owner1.equals(owner2)) && (islands.get(motherNature.getPosition()).getOwner() != null)){
            if (islands.get(motherNature.getPosition()).getOwner().getNickname().equals(owner2)) {
                merge(motherNature.getPosition(), (motherNature.getPosition() + 1) % islands.size(),
                        (motherNature.getPosition() - 1 + islands.size()) % islands.size());
            }
        }
        else if(notNull1 && (islands.get(motherNature.getPosition()).getOwner() != null) &&
                (islands.get(motherNature.getPosition()).getOwner().getNickname().equals(owner1))){
                merge(motherNature.getPosition(), (motherNature.getPosition() + 1) % islands.size());
        }

        else if(notNullMinus1 && (islands.get(motherNature.getPosition()).getOwner() != null) &&
                (islands.get(motherNature.getPosition()).getOwner().getNickname().equals(owner2))){
                merge(motherNature.getPosition(), (motherNature.getPosition() - 1 + islands.size()) % islands.size());
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
        for(int i = 0; i < islands.size() ; i++){
            islands.get(i).setIslandID(i);
        }
        motherNature.setPosition(islands.indexOf(toKeep));
        setChanged();
        notifyObservers();
    }

    protected void merge(int index1, int index2, int index3){
        Island third = islands.get(index3);
        merge(index1, index2);
        merge(motherNature.getPosition(), islands.indexOf(third));
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

    public void setLastRound(){
        lastRound = true;
        setChanged();
        notifyObservers();
    }

    public boolean isGameOver(){ return isGameOver;}

    public int getNumberOfClouds(){return numberOfClouds;}


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
