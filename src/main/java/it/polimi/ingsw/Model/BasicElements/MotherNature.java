package it.polimi.ingsw.Model.BasicElements;

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
