package it.polimi.ingsw.Utils.NetMessages;

import java.io.Serializable;
import java.util.ArrayList;

/** this message functions like its base mode counterpart,
 * of which it contains an instance as an attribute because all the base features
 * are unchanged in representation with expert mode. In addition to base mode features,
 * this message contains representation of all added features with expert rules.
 * As this message is sent through sockets by the server to players in a match, this
 * class implements Serializable.
 * @see Serializable
 * @see ViewUpdateMessage
 * @see ServerMessage
 * */

public class ExpertViewUpdateMessage implements ServerMessage, Serializable {
    private ViewUpdateMessage viewUpdate_base;
    private ArrayList<String> coinCounters = new ArrayList<>();
    private ArrayList<String> extractedCharCards = new ArrayList<>();
    private ArrayList<Integer> islandsNETiles = new ArrayList<>();

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

    public ArrayList<Integer> getIslandsNETiles(){return islandsNETiles;}
}
