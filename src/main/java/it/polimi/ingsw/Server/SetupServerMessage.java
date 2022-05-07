package it.polimi.ingsw.Server;

public class SetupServerMessage implements ServerMessage{
    private String message;

    public SetupServerMessage(String m){
        this.message = m;
    }

    public String getSetupServerMessage(){ return message; }
}
