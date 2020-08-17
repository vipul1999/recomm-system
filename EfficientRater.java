
/**
 * Write a description of class Rater here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;

public class EfficientRater implements Rater {
    private String myID;
    // private ArrayList<Rating> myRatings;
    private HashMap<String,Rating> movieIdToRating;
    public EfficientRater(String id) {
        myID = id;
        // myRatings = new ArrayList<Rating>();
        movieIdToRating =  new HashMap<String,Rating>();
    }

    public void addRating(String item, double rating) {
        // myRatings.add(new Rating(item,rating));
        movieIdToRating.put(item, new Rating(item,rating) );
    }

    public boolean hasRating(String item) {
        // for(int k=0; k < myRatings.size(); k++){
        //     if (myRatings.get(k).getItem().equals(item)){
        //         return true;
        //     }
        // }
        
        // return false;
        if(movieIdToRating.containsKey(item))
            return true;
        else 
            return false;
    }

    public String getID() {
        return myID;
    }

    public double getRating(String item) {
        // for(int k=0; k < myRatings.size(); k++){
        //     if (myRatings.get(k).getItem().equals(item)){
        //         return myRatings.get(k).getValue();
        //     }
        // }
        
        // return -1;

        if(movieIdToRating.containsKey(item)){
            return movieIdToRating.get(item).getValue();
        } else {
            return -1;
        }
    }

    public int numRatings() {
        return movieIdToRating.size();
    }

    public ArrayList<String> getItemsRated() {
        // ArrayList<String> list = new ArrayList<String>();
        // for(int k=0; k < movieIdToRating.size(); k++){
        //     list.add(movieIdToRating.get(k).getItem());
        // }
        Set<String> movieIdSet = movieIdToRating.keySet();
        ArrayList<String> movieIdList = new ArrayList<String>(movieIdSet);
        return movieIdList;
    }
}
