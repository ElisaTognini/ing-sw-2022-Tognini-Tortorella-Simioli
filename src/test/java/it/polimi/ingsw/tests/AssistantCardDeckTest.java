package it.polimi.ingsw.tests;

import it.polimi.ingsw.Model.BasicElements.AssistantCard;
import it.polimi.ingsw.Model.BasicElements.AssistantCardDeck;
import it.polimi.ingsw.Model.Player;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.*;

public class AssistantCardDeckTest {

    Player owns;
    AssistantCardDeck deckTest;

    @Test
    public void getsPlayer() {
        owns = new Player("player");
        deckTest = new AssistantCardDeck(owns);
        assertEquals(owns, deckTest.getOwner());
    }

    @Test
    public void checksDraw(){
        owns = new Player("player");
        deckTest = new AssistantCardDeck(owns);
        AssistantCard c;
        for(int i=1; i<=10; i++){
            c = deckTest.drawCard(i);
            assertEquals(i, c.getAssistantCardID());
            System.out.println("card ID " + c.getAssistantCardID() + " power factor " + c.getMotherNatureMovements());
        }
    }

    @Test
    public void testLoops(){
        owns = new Player("player");
        deckTest = new AssistantCardDeck(owns);
        int k = 1;
        int g = 0;

        for(int i=1; i<=5; i++){
            for(int j=k; j<k+2; j++){
                System.out.println("id: " + j + "  value: " + i);
                g = j;
            }
            k = g+1;
        }
    }

    @Test
    public void checkIfDeckIsEmptyTest(){
        owns = new Player("player");
        deckTest = new AssistantCardDeck(owns);
        int i = 0;
        AssistantCard c;

        assertFalse(deckTest.checkIfDeckIsEmpty());
        for(i=1; i<11; i++){
            c = deckTest.drawCard(i);
            assertFalse(deckTest.checkIfDeckIsEmpty());
            deckTest.removeCard(c.getAssistantCardID());
        }
        assertTrue(deckTest.checkIfDeckIsEmpty());
    }
}
