package it.polimi.ingsw.BoardClasses;

import it.polimi.ingsw.BasicElements.*;
import it.polimi.ingsw.Enums.*;
import it.polimi.ingsw.Game;
import java.util.*;

public class SimpleNotTeamsBoard extends Board{

    public SimpleNotTeamsBoard(){
        super();
        //this.bag = new StudentBag();
        this.currentlyPlaying = chooseFirstPlayer();
        // this.numberOfPlayers = Game.getNumberOfPlayers();
        if(numberOfPlayers.equals(numberOfPlayers.THREE)) clouds.add(new CloudTile(numberOfPlayers.convert(numberOfPlayers)));
    }

    public void setup(){
        super.setup();
        for(int i = 0; i < numberOfPlayers.convert(numberOfPlayers); i++){
            for(int j = 0; j <= numberOfPlayers.convert(numberOfPlayers); j++){
                //islandPlacement.get(i).addStudent(bag.drawStudent());
            }
        }
    }


}
