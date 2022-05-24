package it.polimi.ingsw.Server;

import it.polimi.ingsw.Model.BasicElements.AssistantCardDeck;
import it.polimi.ingsw.Model.BasicElements.Island;
import it.polimi.ingsw.Model.BoardClasses.BoardExpert;
import it.polimi.ingsw.Utils.NetMessages.ExpertViewUpdateMessage;
import it.polimi.ingsw.Utils.NetMessages.ViewUpdateMessage;
import it.polimi.ingsw.Model.Expert.CharacterCardTemplate;
import it.polimi.ingsw.Model.Expert.CoinCounter;
import it.polimi.ingsw.Model.Model;
import it.polimi.ingsw.Model.SchoolBoardClasses.SchoolBoard;
import it.polimi.ingsw.Utils.NetMessages.EndGameMessage;
import it.polimi.ingsw.Utils.NetMessages.NewRoundMessage;
import it.polimi.ingsw.Utils.NetMessages.TurnChangeMessage;

import java.util.ArrayList;

public class ViewUpdateMessageWrapper{

    public TurnChangeMessage turnChangeMessage(String currentPlayer){
        TurnChangeMessage message = new TurnChangeMessage();
        message.setCurrentPlayer(currentPlayer);
        return message;
    }

    public NewRoundMessage newRoundMessage(){
        return new NewRoundMessage();
    }

    public EndGameMessage endGameMessage(String nickname){
        EndGameMessage message = new EndGameMessage();
        message.setWinner(nickname);
        return message;
    }

    public ViewUpdateMessage boardUpdateSimple(Model model){
        ViewUpdateMessage message = new ViewUpdateMessage();

        ArrayList<String> islands = message.getIslands();
        ArrayList<String> clouds = message.getClouds();
        ArrayList<String> decks = message.getDecks();
        ArrayList<String> schoolboards = message.getSchoolboards();

        message.setMnPosition(model.getBoard().getMotherNaturePosition());

        for(Island i : model.getBoard().getIslandList()){
            islands.add(i.toString());
        }

        for(int i = 0; i < model.getNumberOfClouds(); i++){
            clouds.add(model.getBoard().getCloud(i).toString());
        }

        for(AssistantCardDeck d : model.getBoard().getDecks()){
            decks.add(d.toString());
        }

        for(SchoolBoard sb : model.getBoard().getSchoolBoards()){
            schoolboards.add(sb.toString());
        }

        message.setCurrentPlayer(model.getRoundManager().getCurrentPlayer().getNickname());

        return message;

    }

    //da trovare modo intelligente di farlo: probabilmente con una classe che eredita da
    // boardUpdate in modo da aggiungere roba e basta.
    public ExpertViewUpdateMessage boardUpdateExpert(Model model){
        BoardExpert board = (BoardExpert)model.getBoard();

        ExpertViewUpdateMessage expertViewUpdateMessage = new ExpertViewUpdateMessage();
        expertViewUpdateMessage.setViewUpdate_base(boardUpdateSimple(model));

        ArrayList<String> coinCounters = expertViewUpdateMessage.getCoinCounters();
        ArrayList<String> extractedCards = expertViewUpdateMessage.getExtractedCharCards();

        for(CoinCounter cc : board.getCoinCounters()){
            coinCounters.add(cc.toString());
        }

        for(CharacterCardTemplate c : board.getExtractedCards()){
            extractedCards.add(c.toStringCard());
        }

        return expertViewUpdateMessage;
    }

}
