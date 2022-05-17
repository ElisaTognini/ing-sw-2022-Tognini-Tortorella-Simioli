package it.polimi.ingsw.Utils.NetMessages;

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
