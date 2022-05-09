package it.polimi.ingsw.Client;

import it.polimi.ingsw.Enums.ActionType;
import it.polimi.ingsw.Enums.TurnFlow;

import java.util.Observable;
import java.util.Observer;

/* this class receives an update each time the player interacts with the view.
* When the update is received, simple checks are performed client side and then the message is made so that it
* is the correct format to be read server side. */
/* an action will be expected thanks to messages containing current turn flow of the player (those
will come from the model) and the format of these messages is known: action will
   only be requested if FORMAT is valid*/

/* c'è da mandargli il nickname, da capire come fargli arrivare il turnFlow per sapere cosa
* si aspetta, e infine da decidere il formato dei messaggi.*/
public class Parser implements Observer {
    private TurnFlow stage;
    private String actionPerformer;

    public Parser(String nickname){
        this.actionPerformer = nickname;
    }


    @Override
    public void update(Observable o, Object arg) {

    }
}
