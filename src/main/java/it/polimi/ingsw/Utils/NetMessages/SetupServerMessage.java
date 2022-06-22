package it.polimi.ingsw.Utils.NetMessages;

/** messages of this type are sent by the server to the clients during the setup phases of
 * the game (asking for username, number of players and game mode). As
 * this message is sent through socket, this class implements Serializable.
 * @see java.io.Serializable*/
public class SetupServerMessage implements ServerMessage {
    private String message;

    public SetupServerMessage(String m){
        this.message = m;
    }

    public String getSetupServerMessage(){ return message; }

    @Override
    public String toString(){
        return message;
    }
}
