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
    private Match match;

    public Controller(Model model){
        this.model = model;
        baseActionController = new BaseActionController(model);

        if(model.getMode().equals(GameMode.EXPERT)){
            expertModeController = new ExpertModeController(model);
        }
    }

    public void setMatch(Match match){
        this.match = match;
        baseActionController.setMatch(match);
        if(expertModeController != null){
            expertModeController.setMatch(match);
        }
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
