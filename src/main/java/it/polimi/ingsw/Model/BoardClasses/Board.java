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


    /** Method roundSetup is responsible for the setup of the board for every new match: refills clouds with
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


    /** Method chooseCloudTile takes the students from the selected cloud and adds them to the player's entrance
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

    /** Method moveMotherNature moves mother nature accordingly to the power factors of the assistant card played
     * by the players during the planning phase, setting to false the boolean value of the flag hostsMotherNature
     * on the island mother nature is leaving and then to true on the island mother nature has been moved to.
     *
     * @param movements of type int - the power factor of the assistant card
     * */
    public void moveMotherNature(int movements){
        int position = motherNature.getPosition();
        islands.get(position).setHostsToFalse();
        motherNature.setPosition((motherNature.getPosition() + movements) % islands.size());
        islands.get(motherNature.getPosition()).setHostsToTrue();
        setChanged();
        notifyObservers();
    }


    /** Method assignProfessors runs through all the colors of enum PawnDiscColor and, for each of them,
     * iterates players' school boards to find who has the most influence of the current color, saving their
     * nickname and their influence as temporary values for attributes maxInfluence and maxPlayer, respectively.
     * Finally, it assigns the professor to the player with the maximum influence of said color.
     * If two (or more) players have the same influence over a color, the professor is not assigned to either
     * of them (if not previously owned) or it remains property of its previous owner. */
    public void assignProfessors(){
        int maxInfluence;
        int temp;
        String maxPlayer;
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

    /** Private method influenceToCompare iterates through the players' school boards to find the influence
     * of the owner of a professor over the color selected in order to use it to be compared  to the other
     * player's current influence on said color to see if the professor needs to be re-assigned by method
     *  assignProfessor in the current turn.
     *
     * @param c of type PawnDiscColor - color of the professor
     *
     * @return int - the influence of that player over that color
     * */
    private int influenceToCompare(PawnDiscColor c){
        for(SchoolBoard sb : schoolBoards){
            if(sb.getProfessorTable().hasProfessor(c))
                return sb.getDiningRoom().influenceForProf(c);
        }
        return 0;
    }


    /** Private method ownerToCompare iterates through the players' school boards to find the owner of
     * the professor of the color selected in order to compare it to the one temporary saved in maxPlayer
     * in method assignProfessor to see if they match or if the professor needs to be re-assigned.
     *
     * @param c of type PawnDiscColor - color of the professor
     *
     * @return String - the owner of the professor of that color
     * */
    private String ownerToCompare(PawnDiscColor c){
        for(SchoolBoard sb : schoolBoards){
            if(sb.getProfessorTable().hasProfessor(c)){
                return sb.getOwner().getNickname();
            }
        }
        return "";
    }

    /** Method conquerIsland iterates through each of the players' school board to check who has the professor
     * for each color in enum PawnDiscColor and calculates the influence that player has on the island mother
     * nature is currently on. Then checks if the island had already been conquered: if so, it adds the influence
     * of the tower(s) to the owner of the island's influence. Finally, it decides who conquers the island based
     * on who has the most influence over it; if a player has more influence on the owner of the island,
     * the former becomes the current owner of the island and the method replaces the towers of the previous
     * owner with the current ones'.*/
    /* Otherwise, if two players have the same influence over an unconquered island, the island will not be
    * conquered, or if two players hold the same influence over a conquered island, the island
    * will not change its owner.
    * If conqueror is still null at the end of the method, nobody conquers. */
    public void conquerIsland() {
        int[] sum = new int[players.size()];
        int i;
        int maxInfluence = 0;
        boolean deuce = false;
        Player conqueror = null;

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

            getPlayerSchoolBoard(islands.get(motherNature.getPosition()).getOwner().getNickname()).getTowerSection().returnTowers(
                    islands.get(motherNature.getPosition()).getNumberOfTowers());

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


    /** Method checkForMerge checks whether the adjacent islands are also conquered by the current player.
     *  If so, it sets to true the internal variables notNull1 or notNullMinus1 (or both, in case of a
     *  merge for three islands) and then calls the merge method to take care of the actual merging. */
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


    /** Method merge takes care of merging of two island: it increases the number of towers on the island to
     * keep, based on how many towers were on the other island. Then it iterates through the colors in the
     * PawnDiscColor enum to get the number of students of that color that are currently on the island to
     * remove and adds them on the island to keep. Finally, it removes the second island from the islands
     * array list and reassigns the indexes to the islands accordingly.
     *
     * @param index1 of type int - the ID of the island to keep
     * @param index2 of type int - the ID of the island to remove */
    protected void merge(int index1, int index2){
        islands.get(index1).increaseNumberOfTowers(islands.get(index2).getNumberOfTowers());
        int k;
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


    /** Method merge handles merging when three islands are involved: first, it calls merge for the first two
     * islands, then for the newly created merge and the third island.
     *
     * @param index1 of type int - the ID of the first island
     * @param index2 of type int - the ID of the second island
     * @param index3 of type int - the ID of the third island */
    protected void merge(int index1, int index2, int index3){
        Island third = islands.get(index3);
        merge(index1, index2);
        merge(motherNature.getPosition(), islands.indexOf(third));
    }


    /** Method useCard is used in Expert mode only, but it is declared here for overriding purposes.
    *
    * @param o of type Object - the parameter required by the card to perform the action
    * @param nickname of String - the player who purchased the card
    * @param cardID of int - the ID of the character card purchased
    * */
    public void useCard(Object o, String nickname, int cardID){}

    /* END OF METHODS FOLLOWING THE PLAYER'S ACTIONS */

    /* SERVICE METHODS FOR INITIALIZATION */

    /** Method placeIslands adds all the islands in the islands array list during setup. */
    protected void placeIslands(){
        for(int i = 0; i < 12; i++){
            islands.add(new Island(i));
        }
    }


    /** Method placeMotherNature generates a random index indicating the island on which mother nature will be
     * initially placed. */
    protected void placeMotherNature(){
        Random rand = new Random();
        int randomIndex = rand.nextInt(islands.size());
        islands.get(randomIndex).setHostsToTrue();
        motherNature.setPosition(randomIndex);
    }


    /** Method placeClouds initializes the clouds ArrayList and adds clouds in it, accordingly to the value
     * of the attribute studentsOnCloud. */
    protected void placeClouds(){
        for(int i = 0; i < numberOfClouds; i++){
            clouds.add(new CloudTile(i, studentsOnClouds));
        }
    }


    /** Method placeStudents places the initial ten students on the available islands, those that don't host
     * mother nature or are opposite to the island that does. */
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


    /** Method studentsOnClouds places the students on each cloud, accordingly to the value of the attribute
     * studentsOnClouds. */
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


    /** Method initializeSchoolBoards creates a new school board for each player at the beginning of the match,
     * assigning a different color for the towers in each player's tower section, then adding students in their
     * entrance, according to the value of the attribute studentInEntrance. */
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


    /** Method initializeProfessors creates a new professor for each color in enum PawnDiscColor.*/
    protected void initializeProfessors(){
        for(PawnDiscColor c : PawnDiscColor.values()){
            professors.add(new Professor(c));
        }
    }


    /** Method initializeDecks creates a new instance of AssistantCardDeck for each player at the beginning
     * of the match.*/
    protected void initializeDecks(){
        for(Player p : players){
            decks.add(new AssistantCardDeck(p));
        }
    }

    /* END OF SERVICE METHODS FOR INITIALIZATION */

    /* GETTER METHODS */

    /** getter method - Method getStudentBag returns the student bag.
     *
     * @return StudentBag - reference to the student bag */
    public StudentBag getStudentBag(){
        return studentBag;
    }


    /** getter method - Method getIslandList returns the array list containing all the islands
     *
     * @return ArrayList - the list of islands on the board */
    public ArrayList<Island> getIslandList(){
        return islands;
    }


    /** getter method - Method getPlayerSchoolBoard returns the school board of the selected player
     *
     * @param nickname of type String - the nickname of the player who owns the school board
     *
     * @return SchoolBoard - the player's school board */
    public SchoolBoard getPlayerSchoolBoard(String nickname){
        for(SchoolBoard sb : schoolBoards){
            if(sb.getOwner().getNickname().equals(nickname)){
                return sb;
            }
        }
        return null;
    }


    /** getter method - Method getSchoolBoards returns all the players' school boards
     *
     * @return ArrayList - the players' school boards. */
    public ArrayList<SchoolBoard> getSchoolBoards(){
        return schoolBoards;
    }


    /** getter method - Method getMotherNaturePosition returns the ID of the island currently hosting
     * mother nature.
     *
     * @return int - mother nature's position */
    public int getMotherNaturePosition(){
        return motherNature.getPosition();
    }


    /** getter method - Method getDecks returns all the players' decks
     *
     * @return ArrayList - array list with each of the players' decks*/
    public ArrayList<AssistantCardDeck> getDecks(){
        return decks;
    }


    /** getter method - Method getMotherNature returns mother nature
     *
     * @return MotherNature - reference to mother nature*/
    public MotherNature getMotherNature(){
        return motherNature;
    }


    /** getter method - Method getCloud returns the selected cloud, based on the cloud id
     *
     * @param cloudID of type int - the id of the cloud
     *
     * @return CloudTile - the cloud identified by the input id */
    public CloudTile getCloud(int cloudID){
        return clouds.get(cloudID);
    }


    /** method allCloudsPicked iterates through each of the clouds present on the board and returns
     * ture if all of them are empty, false otherwise.
     *
     * @return boolean - result of the check */
    public boolean allCloudsPicked(){
        for(CloudTile c : clouds){
            if(!c.isCloudEmpty()){
                return false;
            }
        }
        return true;
    }


    /** getter method - method getPlayersDeck returns the selected player's deck of assistant cards
     *
     * @param nickname of type String - the player's nickname
     *
     * @return AssistantCardDeck - the player's deck */
    public AssistantCardDeck getPlayersDeck(String nickname){
        for(AssistantCardDeck deck : decks){
            if(deck.getOwner().getNickname().equals(nickname))
                return deck;
        }
        return null;
    }

    /** getter method - method isLastRound returns the value of boolean attribute lastRound
     *
     * @return boolean - true if it's last round, false otherwise */
    public boolean isLastRound(){ return lastRound; }


    /** setter method - method setLastRound sets boolean attribute lastRound to true */
    public void setLastRound(){
        lastRound = true;
        setChanged();
        notifyObservers();
    }


    /** getter method - method isGameOver returns the value of the boolean attribute isGameOver.
     * isGameOver is set by default to false and only changed if one of the players wins.
     *
     * @return boolean - true if someone won, false otherwise*/
    public boolean isGameOver(){ return isGameOver;}

    /* END OF GETTER METHODS */


    /* METHODS FOR LEGAL ACTION CHECKS */

    /** Method isCardInDeck checks if a selected car is inside the player's deck.
     *
     * @param nickname of type String - the player's nickname
     * @param cardID of type int - the ID of the card
     *
     * @return boolean - true if card is present, false otherwise */
    public boolean isCardInDeck(String nickname, int cardID){
        for(AssistantCardDeck deck: decks){
            if(nickname.equals(deck.getOwner().getNickname())){
                return deck.checkIfCardIsPresent(cardID);
            }
        }
        return false;
    }


    /** Method colorAvailableInEntrance checks if a student of a selected color is available in the
     * entrance of the player
     *
     * @param nickname of type String - the player's nickname
     * @param color of type PawnDiscColor - selected color
     *
     * @return boolean - true if there is at least one student of said color, false otherwise */
    public boolean colorAvailableInEntrance(String nickname, PawnDiscColor color){
        return getPlayerSchoolBoard(nickname).getEntrance().isColorAvailable(color);
    }

}
