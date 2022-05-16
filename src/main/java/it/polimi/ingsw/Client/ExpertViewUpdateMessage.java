package it.polimi.ingsw.Client;

import it.polimi.ingsw.Server.ServerMessage;

import java.io.Serializable;
import java.util.ArrayList;

public class ExpertViewUpdateMessage implements ServerMessage, Serializable {
    private ViewUpdateMessage viewUpdate_base;
    private ArrayList<String> coinCounters = new ArrayList<>();
    private ArrayList<String> extractedCharCards = new ArrayList<>();

    public ViewUpdateMessage getViewUpdate_base() {
        return viewUpdate_base;
    }

    public void setViewUpdate_base(ViewUpdateMessage viewUpdate_base) {
        this.viewUpdate_base = viewUpdate_base;
    }

    public ArrayList<String> getCoinCounters() {
        return coinCounters;
    }

    public ArrayList<String> getExtractedCharCards() {
        return extractedCharCards;
    }
}
