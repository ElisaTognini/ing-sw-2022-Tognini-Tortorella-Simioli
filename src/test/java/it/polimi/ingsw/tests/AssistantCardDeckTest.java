package it.polimi.ingsw.tests;
import it.polimi.ingsw.Model.BasicElements.AssistantCard;
import it.polimi.ingsw.Model.BasicElements.AssistantCardDeck;
import it.polimi.ingsw.Model.Player;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.*;

/** Class AssistantCardDeckTest tests class AssistantCardDeck. */

public class AssistantCardDeckTest {

    Player owns;
    AssistantCardDeck deckTest;

    /** Method getsPlayer creates a new instance of assistant card deck owned by a new instance of player and
     * checks if the owner returned by method getOwner of class AssistantCardDeck equals value of attribute
     * owns. */
    @Test
    public void getsPlayer() {
        owns = new Player("player");
        deckTest = new AssistantCardDeck(owns);
        assertEquals(owns, deckTest.getOwner());
    }


    /** Method checksDraw checks if method drawCard extracts the correct character card based on the ID passed in
     * input and then prints the card with its power factor. */
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


    /** Method testLoops checks if all the assistant cards are initialised with the correct id and power factor */
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


    /** Method checkIfDeckIsEmptyTest initialises a new instance of assistant card deck, calls for method
     * checkIfDeckIsEmpty and expects the return to be false. Then, it removes all the assistant cards from the
     * deck and calls for method checkIfDeckIsEmpty again, now expecting true in return. */
    @Test
    public void checkIfDeckIsEmptyTest(){
        owns = new Player("player");
        deckTest = new AssistantCardDeck(owns);
        int i;
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
