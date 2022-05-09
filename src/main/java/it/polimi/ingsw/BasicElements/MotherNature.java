package it.polimi.ingsw.BasicElements;

public class MotherNature {

    private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public String toString(){
        return String.valueOf(position);
    }
}
