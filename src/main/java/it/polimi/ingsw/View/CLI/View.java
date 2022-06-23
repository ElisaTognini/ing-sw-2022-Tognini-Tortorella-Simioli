package it.polimi.ingsw.View.CLI;

import it.polimi.ingsw.Utils.NetMessages.*;
import java.util.Observable;
import java.util.Observer;

/** class View acts as a template for both CLI and GUI game visualizations.
 * It reacts to the reception of messages by the Client class (which notifies it about them)
 * and, based on the type of message, a method is called so that the game representation
 * for the client is coherent with the game state on server side.
 * It acts as on Observable towards classes that send messages through sockets to the server
 * @see Observable
 * @see Observer*/

public class View extends Observable implements Observer {

    /** overrides method update in Observer class so that changes can be displayed
     * based on the type of message sent by the server.*/
    @Override
    public void update(Observable o, Object arg) {
        if(arg instanceof SetupServerMessage){
            /* requests for basic preliminary information to user */
            displaySetupMessage((SetupServerMessage)arg);
        }
        else if(arg instanceof ViewUpdateMessage){
            /*view itself will be updated - base mode*/
            updateGameBoard((ViewUpdateMessage)arg);
        }
        else if(arg instanceof ExpertViewUpdateMessage){
            /* view itself will be updated - expert rules*/
            updateGameBoardExpert((ExpertViewUpdateMessage)arg);
        }
        else if(arg instanceof BaseServerMessage){
            /* view will display the error*/
            displayError((BaseServerMessage)arg);
        }
        else if(arg instanceof NewRoundMessage){
            /* view will display new round*/
            displayNewRoundMessage((NewRoundMessage)arg);
        }
        else if(arg instanceof TurnChangeMessage){
            /* view will display the change of turn in both action and planning*/
            displayTurnChange((TurnChangeMessage)arg);
        }
        else if(arg instanceof EndGameMessage){
            /* view will display winner and game will close*/
            displayEndOfGame((EndGameMessage)arg);
        }
        /*contains information to store client-side*/
        else if(arg instanceof GameModeMessage){
            setMode((GameModeMessage)arg);
        }
        else if(arg instanceof NicknameMessage) {
            setNickname((NicknameMessage) arg);
        }
        else if(arg instanceof PlayedCardMessage){
            setPlayedCard((PlayedCardMessage) arg);
        }
    }

    /** allows the view of each client to update itself whenever the server
     * sends a message that contains the current state of the game.
     * @param message contains all the necessary information that allows the game
     * visualization to display changes and keep track of the match.
     *- expert rules*/
    public void updateGameBoardExpert(ExpertViewUpdateMessage message) {}

    /** view gives instruction to the user on preliminary information that
     * is asked of them (e.g. nickname)
     * @param message contains the type of information requested to user*/
    public void displaySetupMessage(SetupServerMessage message){}

    /** allows the view of each client to update itself whenever the server
     * sends a message that contains the current state of the game.
     * @param message contains all the necessary information that allows the game
     * visualization to display changes and keep track of the match.
     * - base rules*/
    public void updateGameBoard(ViewUpdateMessage message){}

    /** tells view to notify the user of a misuse of game rules
     * @param message contains the type of invalid action performed by the player*/
    public void displayError(BaseServerMessage message){}

    /** displays notification of a starting new round
     * @param message contains the new round notification message to be displayed*/
    public void displayNewRoundMessage(NewRoundMessage message){}

    /** displays the current player in turn every time a change of turn occurs
     * @param message contains information about the new current player */
    public void displayTurnChange(TurnChangeMessage message){}

    /** view will display the winning screen, showing who won the match
     * @param message contains information about the winner of the current game*/
    public void displayEndOfGame(EndGameMessage message){}

    /** stores the game mode contained in the message to allow the view to function
     * correctly based on the rules and the assets to display depending on which
     * rules were chosen.
     * @param mode represents the mode (simple or expert) of the match the user is partaking in*/
    public void setMode(GameModeMessage mode){}

    /** stores the nickname of the user for visualization purposes
     * @param nick contains this user's nickname*/
    public void setNickname(NicknameMessage nick){}

    /** displays the assistant cards played by all players in this turn, so that
     * mother nature's path is predictable by all users in the match.
     * @param message contains all information to display about a played assistant card*/
    public void setPlayedCard(PlayedCardMessage message){};
}
