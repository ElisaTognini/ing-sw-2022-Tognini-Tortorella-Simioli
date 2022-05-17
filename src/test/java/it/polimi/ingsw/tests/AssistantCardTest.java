package it.polimi.ingsw.tests;
import it.polimi.ingsw.Model.BasicElements.AssistantCard;
import it.polimi.ingsw.Model.Player;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class AssistantCardTest {

    AssistantCard c;
    ArrayList<AssistantCard> list = new ArrayList<>();
    Player owner;

    @Test
    public void createAttempt(){
        c = new AssistantCard(1, 1, owner);
        assertEquals(1, c.getAssistantCardID());
        assertEquals(1, c.getMotherNatureMovements());
    }

    @Test
    public void checkRepeated(){
        for(int i=0; i<10; i++){
            list.add(new AssistantCard(i, i, owner));
            assertEquals(i, list.get(i).getAssistantCardID());
            assertEquals(i, list.get(i).getMotherNatureMovements());
        }
    }

    @Test
    public void testEquals(){
        AssistantCard c1, c2;
        c1 = new AssistantCard(1, 2, owner);
        c2 = new AssistantCard(1, 3, owner);
        assertEquals(true, c1.equals(c2));
    }
}
