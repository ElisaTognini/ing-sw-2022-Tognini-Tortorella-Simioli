package it.polimi.ingsw.Model;
import it.polimi.ingsw.Model.BasicElements.AssistantCardDeck;
import it.polimi.ingsw.Model.BoardClasses.Board;
import it.polimi.ingsw.Model.BoardClasses.BoardExpert;
import it.polimi.ingsw.Model.BoardClasses.RoundManager;
import it.polimi.ingsw.Model.SchoolBoardClasses.SchoolBoard;
import it.polimi.ingsw.Server.ViewUpdateMessageWrapper;
import it.polimi.ingsw.Utils.Enums.ActionType;
import it.polimi.ingsw.Utils.Enums.GameMode;
import it.polimi.ingsw.Utils.NetMessages.TurnChangeMessage;
import java.util.*;

/** Class Model contains reference to the board class and to the round manager in order to manage the interaction
 * between the actions allowed by the controller and the actual components of the game.
 * Moreover, it keeps track of the game mode the game is played, the number of players and consequently the number
 * of towers, the students on cloud and the number of clouds, as well as the students in the entrance.
 * In addition, it contains an internal class to process the notifies of the round manager. */
public class Model extends Observable implements Observer{
    private Board board;
    private RoundManager roundManager;
    private ArrayList<Player> playerList;
    private final GameMode mode;
    private final int numberOfPlayers;
    private int numberOfTowers;
    private int studentsOnCloud;
    private int numberOfClouds;
    private int studentsInEntrance;
    private final String[] playerNames;
    private ViewUpdateMessageWrapper messageWrapper;
    private TurnUpdates turnUpdates;

    /** Class TurnUpdates is an internal class which processes the messages sent by the method notify
     * inside RoundManager */
    public class TurnUpdates extends Observable implements Observer{

        /** Override of method update which sends a different notify type to match based on the argument it
         * receives.
         **/
        @Override
        public void update(Observable o, Object arg) {
            ActionType argument = (ActionType)arg;
            switch (argument){
                case PLAYER_CHANGE:
                    TurnChangeMessage message = messageWrapper.turnChangeMessage(roundManager.getCurrentPlayer().getNickname());
                    setChanged();
                    notifyObservers(message);
                    break;
                case NEW_ROUND:
                    setChanged();
                    notifyObservers(messageWrapper.newRoundMessage());
                    break;
                case END_GAME:
                    setChanged();
                    notifyObservers(messageWrapper.endGameMessage(getWinner().getNickname()));
            }
        }
    }

    /** Constructor Model creates a new instance of model based on the game mode, the number of players and the
     * nicknames of the players.
     *
     * @param mode of type GameMode - the mode chosen by the players to play
     * @param nicknames of type String[] - array list containing the nickname of the players
     * @param numberOfPlayers of type int - the number of players */
    public Model(GameMode mode, String[] nicknames, int numberOfPlayers){
        this.mode = mode;
        this.numberOfPlayers = numberOfPlayers;
        playerNames = nicknames;
        playerList = new ArrayList<>();
        setupBoard();
        roundManager = new RoundManager(playerList);
        turnUpdates = new TurnUpdates();
        roundManager.addObserver(turnUpdates);
        board.addObserver(this);
        messageWrapper = new ViewUpdateMessageWrapper();
    }

    /** Override of method update which sends updates to the board, based on the game mode chosen */
    @Override
    public void update(Observable o, Object arg) {
        switch (mode){
            case SIMPLE:
                setChanged();
                notifyObservers(messageWrapper.boardUpdateSimple(this));
                break;
            case EXPERT:
                setChanged();
                notifyObservers(messageWrapper.boardUpdateExpert(this));
                break;
        }
    }


    /** getter method - Method getBoard returns the board associated to this model
     *
     * @return Board - board */
    public Board getBoard(){
            return board;
    }


    /** getter method - Method getRoundManager returns the round manager associated to this model
     *
     * @return RoundManager - round manager */
    public RoundManager getRoundManager() {
        return roundManager;
    }


    /** Private method setupBoard computes the number of players and, consequently, the number
     * of towers, the students on cloud and the number of clouds, as well as the students in the entrance and
     * creates a new instance of board based on the computed settings and the game mode chosen */
    private void setupBoard(){
        setupPlayers();
        computeSettings();
        if(mode.equals(GameMode.SIMPLE)) {
            board = new Board(playerList, numberOfClouds, numberOfTowers, studentsOnCloud,
                    studentsInEntrance, mode);
        }
        else if (mode.equals(GameMode.EXPERT)){
            board = new BoardExpert(playerList, numberOfClouds, numberOfTowers, studentsOnCloud,
                    studentsInEntrance, mode);
        }
    }


    /** Private method computeSettings computes number of towers, the students on cloud and the number of clouds,
     * as well as the students in the entrance according to the choices of mode and number of players. */
    private void computeSettings(){
        studentsOnCloud = numberOfPlayers + 1;
        numberOfClouds = numberOfPlayers;

        switch (numberOfPlayers){
            case 2:
                numberOfTowers = 8;
                studentsInEntrance = 7;
                break;
            case 3:
                numberOfTowers = 6;
                studentsInEntrance = 9;
        }
    }


    /** Private method setupPlayers creates a new instance of Player for each nickname in the array playerNames
     * and adds them to the playerList array list */
    private void setupPlayers(){
        for(String s : playerNames){
            playerList.add(new Player(s));
        }
    }


    /** Method isGameOver checks if someone won the game: the game ends if a player runs out of towers, when
     * only three islands are left after the merging, when there are no more students in the bag or if any
     * player runs out of assistant cards.
     *
     * @return boolean - true if one of the conditions for which the game ends is true, false otherwise */
    public boolean isGameOver(){

        for(SchoolBoard sb: board.getSchoolBoards()){
            if(sb.getTowerSection().isTowerSectionEmpty()){
                return true;
            }
        }

        if(board.getIslandList().size() == 3)
            return true;

        if(board.getStudentBag().checkIfStudentBagEmpty())
            return true;

        for(AssistantCardDeck deck : board.getDecks()){
            if(deck.checkIfDeckIsEmpty()) return true;
        }

        return false;
    }

    /** Method getWinner returns the player who won the game; the winner is the player with the least towers in their
     * tower section.
     * In case of a tie regarding the number of towers, the players who are tying are stored in an array list and
     * the winner is whoever controls the most professors.
     *
     * @return Player - winner */
    public Player getWinner(){

        int minTowers = board.getPlayerSchoolBoard(playerList.get(0).getNickname()).getTowerSection().getNumberOfTowers();
        Player winner = playerList.get(0);
        ArrayList<Player> tiedPlayers = new ArrayList<>();
        boolean tie = false;

        for(int i = 1; i < numberOfPlayers; i++){
            if(board.getPlayerSchoolBoard(playerList.get(i).getNickname()).getTowerSection().getNumberOfTowers() < minTowers){
                minTowers = board.getPlayerSchoolBoard(playerList.get(i).getNickname()).getTowerSection().getNumberOfTowers();
                winner = playerList.get(i);
                tie = false;
                tiedPlayers.clear();
            }
            else if(board.getPlayerSchoolBoard(playerList.get(i).getNickname()).getTowerSection().getNumberOfTowers() == minTowers){
                tie = true;
                tiedPlayers.add(playerList.get(i));
            }
        }

        if(tie){
            tiedPlayers.add(playerList.get(0));
            int max = -1;
            int temp;
            for(Player p : tiedPlayers){
                temp = board.getPlayerSchoolBoard(p.getNickname()).getProfessorTable().getNumberOfProfessors();
                if(temp > max){
                    max = temp;
                    winner = p;
                }
            }
        }
        return winner;
    }


    /** getter method - Method getNumberOfClouds returns the number of clouds
     *
     * @return int - number of clouds */
    public int getNumberOfClouds (){
        return numberOfClouds;
    }


    /** getter method - Method getMode returns the game mode
     *
     * @return GameMode - game mode*/
    public GameMode getMode(){return mode;}


    /** getter method - Method getTurnUpdates returns the reference to the class TurnUpdates in this class
     *
     * @return TurnUpdates - value of attribute turnUpdates */
    public TurnUpdates getTurnUpdates(){
        return turnUpdates;
    }


    /** getter method - Method getPlayerList returns the array list with the players
     *
     * @return ArrayList - array list of players */
    public ArrayList<Player> getPlayerList(){
        return playerList;
    }
}
