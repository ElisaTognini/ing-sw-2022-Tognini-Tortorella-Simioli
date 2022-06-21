package it.polimi.ingsw.View.CLI;

import it.polimi.ingsw.Utils.NetMessages.*;
import java.util.Observable;
import java.util.Observer;

public class View extends Observable implements Observer {

    @Override
    public void update(Observable o, Object arg) {
        if(arg instanceof SetupServerMessage){
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

    public void updateGameBoardExpert(ExpertViewUpdateMessage message) {}
    public void displaySetupMessage(SetupServerMessage message){}
    public void updateGameBoard(ViewUpdateMessage message){}
    public void displayError(BaseServerMessage message){}
    public void displayNewRoundMessage(NewRoundMessage message){}
    public void displayTurnChange(TurnChangeMessage message){}
    public void displayEndOfGame(EndGameMessage message){}
    public void setMode(GameModeMessage mode){}
    public void setNickname(NicknameMessage nick){}
    public void setPlayedCard(PlayedCardMessage message){};
}
