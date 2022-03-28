package it.polimi.ingsw.tests;
import it.polimi.ingsw.BasicElements.AssistantCard;
import it.polimi.ingsw.BasicElements.AssistantCardDeck;
import it.polimi.ingsw.TailoredExceptions.InvalidCardActionException;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class AssistantCardTest {

    AssistantCard c;
    ArrayList<AssistantCard> list = new ArrayList<>();

    @Test
    public void createAttempt(){
        c = new AssistantCard(1, 1);
        assertEquals(1, c.getAssistantCardID());
        assertEquals(1, c.getMotherNatureMovements());
    }

    @Test
    public void checkRepeated(){
        for(int i=0; i<10; i++){
            list.add(new AssistantCard(i, i));
            assertEquals(i, list.get(i).getAssistantCardID());
            assertEquals(i, list.get(i).getMotherNatureMovements());
        }
    }

    @Test
    public void testEquals(){
        AssistantCard c1, c2;
        c1 = new AssistantCard(1, 2);
        c2 = new AssistantCard(1, 3);
        assertEquals(true, c1.equals(c2));
    }
}
