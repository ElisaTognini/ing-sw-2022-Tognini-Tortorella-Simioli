package it.polimi.ingsw.View.GUI;


import java.util.Observable;

public class Storage extends Observable {

    private String ipAddress;


    public void setIpAddress(String ipAddress){
        try {
            this.ipAddress = ipAddress;
            setChanged();
            notifyObservers(ipAddress);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
