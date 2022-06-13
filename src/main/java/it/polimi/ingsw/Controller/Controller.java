package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Client.ActionMessages.*;
import it.polimi.ingsw.Server.VirtualView;
import it.polimi.ingsw.Utils.Enums.GameMode;
import it.polimi.ingsw.Model.Model;
import it.polimi.ingsw.Server.Match;

import java.util.Observable;
import java.util.Observer;

/**
 * Class Controller is the main controller class, it calls the controllers of the game
 * based on the mode in which the match is played, such as BaseActionController for
 * simple mode and ExpertModeController for expert mode.
 *
 * @see java.util.Observer
 */

public class Controller implements Observer {

    private BaseActionController baseActionController;
    private ExpertModeController expertModeController;
    private Model model;

    /**
     * Constructor Controller creates a new Controller instance. A BaseActionController is always
     * instantiated, in addition to an ExpertModeController in the case of a match in expert mode.
     *
     * @param model of type Model - model reference.
     */

    public Controller(Model model){
        this.model = model;
        baseActionController = new BaseActionController(model);

        if(model.getMode().equals(GameMode.EXPERT)){
            expertModeController = new ExpertModeController(model);
        }
    }

    /**
     * Match class becomes observer of BaseActionController class,
     * in addition to ExpertModeController in the case of a match in expert mode.
     *
     * @param match of type Match - match reference.
     */

    public synchronized void addMatchAsObserver(Match match){
        baseActionController.addObserver(match);
        if(expertModeController != null){
            expertModeController.addObserver(match);
        }
    }


    /**
     * Method getBaseActionController returns the baseActionController of this Controller object.
     *
     * @return the baseActionController (type BaseActionController) of this Controller object.
     */

    public BaseActionController getBaseActionController(){return baseActionController;}

    /**
     * Method getExpertModeController returns the expertModeController of this Controller object.
     *
     * @return the expertModeController (type ExpertModeController) of this Controller object.
     */

    public ExpertModeController getExpertModeController() {return expertModeController;}

    /**
     * @see Observer#update(Observable, Object)
     * */

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
        else if(arg instanceof CharacterCardMessage){
            expertModeController.useCharacterCard(((VirtualView)o).getNickname(),
                    ((CharacterCardMessage)arg).getParam(), ((CharacterCardMessage)arg).getCardID());
        }
    }
}
