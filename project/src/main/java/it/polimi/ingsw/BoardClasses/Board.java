package it.polimi.ingsw.BoardClasses;
import it.polimi.ingsw.BasicElements.*;
import it.polimi.ingsw.Enums.PawnDiscColor;
import it.polimi.ingsw.Enums.PlayerNumber;
import it.polimi.ingsw.Enums.TurnFlow;
import it.polimi.ingsw.Player;
import it.polimi.ingsw.SchoolBoardClasses.SchoolBoard;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

public abstract class Board {

    protected LinkedList<Island> islandPlacement;
    protected StudentBag bag;
    protected MotherNature motherNature;
    protected final int islandNumber = 12;
    protected Player currentlyPlaying;
    protected TurnFlow currentStage;
    protected PlayerNumber numberOfPlayers;
    protected ArrayList<CloudTile> clouds;
    protected ArrayList<Player> players;
    protected ArrayList<SchoolBoard> schoolBoards;
    protected ArrayList<AssistantCardDeck> decks;
    protected ArrayList<Professor> professors;

    public Board(){
        this.clouds = new ArrayList<CloudTile>();
        clouds.add(new CloudTile(0));
        clouds.add(new CloudTile(1));
        this.professors = new ArrayList<Professor>();
        for (PawnDiscColor color : PawnDiscColor.values()) {
            professors.add(new Professor(color));
        }
        this.motherNature = new MotherNature();
        this.islandPlacement = new LinkedList<Island>();
        placeIslands();
        this.decks = new ArrayList<AssistantCardDeck>();
        // basing off the number of players, we will have dedicated decks
        this.schoolBoards = new ArrayList<SchoolBoard>();
        // as before,basing off the number of players, we will have as many school boards as they are
        this.currentStage = TurnFlow.BEGINS_TURN;
        
    }
/* for each game mode, setup includes:
* - placing mother nature on a random island tile
* - add one student on each island except for the sixth island from mother nature */
    public void setup(){
        motherNature.setPosition(randomPlaceMotherNature());
        islandPlacement.get(motherNature.getPosition()).setHostsToTrue();
        /* ten students must be extracted randomly and placed on the islands, skipping the sixth from MN*/
    }

    public void roundSetup(){

    }

    public void choosePlayerOrder(){   // modifies the list of players, putting them in the correct order to start the turn

    }

    protected void placeIslands(){

        for (int i = 0; i < islandNumber; i++){
            islandPlacement.add(new Island(i));
        }

    }

    protected Player chooseFirstPlayer(){
        Random rand = new Random();
        int randomIndex = rand.nextInt(players.size());
        Player firstPlayer = players.get(randomIndex);

        return firstPlayer;
    }

    protected int randomPlaceMotherNature(){
        Random rand = new Random();
        int randomIndex = rand.nextInt(islandNumber);
        return randomIndex;
    }

    /* these methods are supposed to be called directly by the player */

    public void picksCard(int cardID, String nickname){}
    public void moveStudents(String nickname, char[] colors, char[] islandOrDiningRoom, int[] islandID){}
    public void picksCloudTile(String nickname, int cloudNumber){}

}
