package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Enums.GameMode;
import it.polimi.ingsw.Model;

public class Controller {
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
}
