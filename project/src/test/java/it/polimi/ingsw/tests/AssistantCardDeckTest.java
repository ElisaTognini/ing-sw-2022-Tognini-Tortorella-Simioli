package it.polimi.ingsw.tests;

import it.polimi.ingsw.BasicElements.AssistantCard;
import it.polimi.ingsw.BasicElements.AssistantCardDeck;
import it.polimi.ingsw.Player;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;

public class AssistantCardDeckTest {

    Player owns = new Player("player");
    AssistantCardDeck deckTest = new AssistantCardDeck(owns);

    @Test
    public void getsPlayer(){
        assertEquals(owns, deckTest.getOwner());
    }

    @Test
    public void checksDraw(){
        AssistantCard c;
        for(int i=1; i<=10; i++){
            c = deckTest.drawCard(i);
            assertEquals(i, c.getAssistantCardID());
            System.out.println("card ID " + c.getAssistantCardID() + " power factor " + c.getMotherNatureMovements());
        }
    }

    @Test
    public void testLoops(){
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
}
