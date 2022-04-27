package it.polimi.ingsw.Expert;

/* choose an island and resolve it as if mother nature had stopped there; mother
* nature will still move as per normal game rules.  */

import it.polimi.ingsw.BoardClasses.BoardExpert;

public class Card3 extends CharacterCardTemplate{

    public Card3(BoardExpert board) {
        super(board);
        cost = 3;
        cardID = 3;
    }

    /* this method saves the previous position on mother nature, changes it,
    * forces the board to resolve the island. After that, the position of mother nature goes back
    * to its previous value (which ensures the turn proceeds as normal)*/
    @Override
    public void useCard(Object o, String nickname){
        Parameter parameters;
        int oldPos;

        if(o instanceof Parameter){
            parameters = (Parameter)o;
        }
        else throw new IllegalArgumentException();

        oldPos = board.getMotherNaturePosition();
        board.setMotherNaturePosition(parameters.getIslandID());
        board.assignProfessors();
        board.conquerIsland();
        board.setMotherNaturePosition(oldPos);
    }
}
