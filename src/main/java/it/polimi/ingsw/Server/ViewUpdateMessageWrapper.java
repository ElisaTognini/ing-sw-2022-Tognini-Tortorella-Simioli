package it.polimi.ingsw.Server;

import it.polimi.ingsw.BasicElements.Island;
import it.polimi.ingsw.BoardClasses.Board;
import it.polimi.ingsw.Client.ViewUpdateMessage;
import it.polimi.ingsw.Enums.ActionType;

import java.util.ArrayList;
import java.util.Observable;

public class ViewUpdateMessageWrapper extends Observable {

    public ViewUpdateMessageWrapper(ArrayList<VirtualView> views){
        for(VirtualView v : views){
            addObserver(v);
        }
    }

    /* sending initial message for setupBoard */
    public void sendSetUpBoard(Board board){
        ViewUpdateMessage message = new ViewUpdateMessage();
        ArrayList<String> islands = new ArrayList<>();
        for(Island i :  board.getIslandList()){
            islands.add(i.toString());
        }

        message.setActionType(ActionType.SETUP);
        message.setChangedIsland(islands);

        notifyObservers(message);
    }

    public void sendRoundSetup(Board board){
        ViewUpdateMessage message = new ViewUpdateMessage();
        ArrayList<String> clouds = new ArrayList<>();

        for(int i = 0; i < board.getNumberOfClouds(); i++){
            clouds.add(board.getCloud(i).toString());
        }
        message.setActionType(ActionType.ROUND_SETUP);
        message.setChangesCloud(clouds);

        notifyObservers(message);

    }
}
