package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Enums.GameMode;
import it.polimi.ingsw.Model;
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

    public void addMatchAsObserver(Match match){
        baseActionController.addObserver(match);
        if(expertModeController != null){
            expertModeController.addObserver(match);
        }
    }

    public BaseActionController getBaseActionController(){return baseActionController;}

    public ExpertModeController getExpertModeController() {return expertModeController;}

    @Override
    public void update(Observable o, Object arg) {

    }
}
