package it.polimi.ingsw.Server;

import it.polimi.ingsw.BasicElements.Island;
import it.polimi.ingsw.BoardClasses.Board;
import it.polimi.ingsw.BoardClasses.BoardExpert;
import it.polimi.ingsw.Client.ViewUpdateMessage;
import it.polimi.ingsw.Enums.ActionType;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class ViewUpdateMessageWrapper extends Observable implements Observer {

    public ViewUpdateMessageWrapper(ArrayList<VirtualView> views){
        for(VirtualView v : views){
            addObserver(v);
        }
    }

    /* sending initial message for setupBoard */
    private void sendSetUpBoard(Board board){
        ViewUpdateMessage message = new ViewUpdateMessage();
        ArrayList<String> islands = new ArrayList<>();
        for(Island i :  board.getIslandList()){
            islands.add(i.toString());
        }

        message.setActionType(ActionType.SETUP);
        message.setChangedIsland(islands);

        notifyObservers(message);
    }

    private void sendSetupBoardExpert(BoardExpert o) {
    }

    private void sendRoundSetup(Board board){
        ViewUpdateMessage message = new ViewUpdateMessage();
        ArrayList<String> clouds = new ArrayList<>();

        for(int i = 0; i < board.getNumberOfClouds(); i++){
            clouds.add(board.getCloud(i).toString());
        }
        message.setActionType(ActionType.ROUND_SETUP);
        message.setChangesCloud(clouds);

        notifyObservers(message);

    }


    @Override
    public void update(Observable o, Object arg) {

        switch ((ActionType)arg){
            case SETUP:
                if(o instanceof Board){
                    sendSetUpBoard((Board)o);
                }else if (o instanceof BoardExpert){
                    sendSetupBoardExpert((BoardExpert)o);
                }
                break;
        }
    }

}
