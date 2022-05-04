package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Enums.GameMode;
import it.polimi.ingsw.Model;

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

    @Override
    public void update(Observable o, Object arg) {

    }
}
