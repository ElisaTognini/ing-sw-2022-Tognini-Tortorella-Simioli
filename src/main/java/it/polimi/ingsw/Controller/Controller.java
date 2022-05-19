package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Client.ActionMessages.AssistantCardMessage;
import it.polimi.ingsw.Client.ActionMessages.MoveStudentToDRMessage;
import it.polimi.ingsw.Client.ActionMessages.MoveStudentToIslandMessage;
import it.polimi.ingsw.Client.ActionMessages.PickCloudMessage;
import it.polimi.ingsw.Server.VirtualView;
import it.polimi.ingsw.Utils.Enums.GameMode;
import it.polimi.ingsw.Model.Model;
import it.polimi.ingsw.Server.Match;

import java.util.Observable;
import java.util.Observer;

public class Controller implements Observer {

    private BaseActionController baseActionController;
    private ExpertModeController expertModeController;
    private Model model;

    public Controller(Model model){
        this.model = model;
        baseActionController = new BaseActionController(model);

        if(model.getMode().equals(GameMode.EXPERT)){
            expertModeController = new ExpertModeController(model);
        }
    }

    public synchronized void addMatchAsObserver(Match match){
        baseActionController.addObserver(match);
        if(expertModeController != null){
            expertModeController.addObserver(match);
        }
    }

    public BaseActionController getBaseActionController(){return baseActionController;}

    public ExpertModeController getExpertModeController() {return expertModeController;}

    @Override
    public synchronized void update(Observable o, Object arg) {
        if(arg instanceof AssistantCardMessage){
            baseActionController.chooseAssistantCard(((AssistantCardMessage) arg).getPickedCardID(),
                    ((VirtualView)o).getNickname());
        }
        else if(arg instanceof MoveStudentToDRMessage){
            baseActionController.moveStudentToDR(((MoveStudentToDRMessage)arg).getColor(),
                    ((VirtualView)o).getNickname());
        }
        else if(arg instanceof MoveStudentToIslandMessage){
            baseActionController.moveStudentToIsland(((MoveStudentToIslandMessage)arg).getColor(),
                    ((VirtualView)o).getNickname(),((MoveStudentToIslandMessage)arg).getIslandID());
        }
        else if(arg instanceof PickCloudMessage){
            baseActionController.picksCloud(((VirtualView)o).getNickname(),
                    ((PickCloudMessage)arg).getCloudID());
        }
    }
}
