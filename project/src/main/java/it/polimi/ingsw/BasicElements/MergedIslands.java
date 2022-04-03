package it.polimi.ingsw.BasicElements;
import java.util.*;

public class MergedIslands {

    private ArrayList<Island> islands;
    private int mergeID;

    public MergedIslands(){
        islands = new ArrayList<Island>();
    }

    public void merge(Island iA, Island iB){
        islands.add(iA);
        islands.add(iB);
    }

    public void merge(Island island){
        islands.add(island);
    }

    public void merge(MergedIslands mergedIslands){
        islands.addAll(mergedIslands.getIslands());
    }

    private ArrayList<Island> getIslands(){
        return islands;
    }
}
