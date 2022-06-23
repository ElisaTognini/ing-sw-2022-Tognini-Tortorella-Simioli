package it.polimi.ingsw.tests;

import it.polimi.ingsw.Model.BasicElements.AssistantCard;
import it.polimi.ingsw.Model.Player;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

import static org.junit.Assert.*;

/** Class AssistantCardTest tests class AssistantCard. */

public class AssistantCardTest {

    AssistantCard c;
    ArrayList<AssistantCard> list = new ArrayList<>();
    Player owner;

    /** Method createAttempt checks if the constructor of class Assistant Card creates a new instance of AssistantCard
     * according to the parameters passed. */
    @Test
    public void createAttempt(){
        c = new AssistantCard(1, 1, owner);
        assertEquals(1, c.getAssistantCardID());
        assertEquals(1, c.getMotherNatureMovements());
    }


    /** Method checkRepeated checks if the constructor of class Assistant Card creates a new instance of AssistantCard
     * according to the parameters passed for multiple times. */
    @Test
    public void checkRepeated(){
        for(int i=0; i<10; i++){
            list.add(new AssistantCard(i, i, owner));
            assertEquals(i, list.get(i).getAssistantCardID());
            assertEquals(i, list.get(i).getMotherNatureMovements());
        }
    }


    /** Method testEquals checks if two instances of the same card are seen as equal even though they have different
     * mother nature movements. */
    @Test
    public void testEquals(){
        AssistantCard c1, c2;
        c1 = new AssistantCard(1, 2, owner);
        c2 = new AssistantCard(1, 3, owner);
        assertEquals(true, c1.equals(c2));
    }
}
