package it.polimi.ingsw.Enums;

public enum PlayerNumber {

    TWO,
    THREE,
    TEAMS;

    public int convert(PlayerNumber value){
        int players = 0;
        switch (value){
            case TWO:
                players = 2;
                break;
            case THREE:
                players = 3;
                break;
            case TEAMS:
                players = 4;
                break;
        }
     return players;
    }
}
