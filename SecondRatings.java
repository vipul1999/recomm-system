
/**
 * Write a description of SecondRatings here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class SecondRatings {
    private ArrayList<Movie> myMovies;
    private ArrayList<Rater> myRaters;

    public SecondRatings() throws FileNotFoundException, IOException {
        // default constructor
        this("ratedmoviesfull.csv", "ratings.csv");
    }

    public SecondRatings(String moviefile, String ratingsfile) throws FileNotFoundException, IOException {
        FirstRatings firstRatings = new FirstRatings();
        myMovies=firstRatings.loadMovies(moviefile);//connectino between firstRating and SecondRating
        myRaters=firstRatings.loadRaters(ratingsfile);

    }
    
    public int getMovieSize(){
        return myMovies.size();
    }
    public int getRaterSize(){
        return myRaters.size();
    }

    private double getAverageById(String id, int minimalRaters){
        double avgRating = 0;//myraters< Rater<rater_id,List<movie_id,rating_value> >  myMovies<Movie<id...>>
        
        int howManyRatings=0;
        double sumOfRatings=0;
        for(int i=0;i<myRaters.size();i++){
            if(myRaters.get(i).hasRating(id)){
                sumOfRatings+=myRaters.get(i).getRating(id);
                howManyRatings++;
            }
        }

        if(howManyRatings!=0 && howManyRatings>=minimalRaters){
            avgRating = sumOfRatings/howManyRatings;
        }
        return avgRating;
    }
    
    public ArrayList<Rating> getAverageRatings(int minimalRaters){
        ArrayList<Rating> avgRatings = new ArrayList<Rating>();
        
        for(int i=0;i<myMovies.size();i++){
            double avgRating = getAverageById(myMovies.get(i).getID(), minimalRaters);
            if(avgRating!=0)
            avgRatings.add(new Rating(myMovies.get(i).getID(), avgRating));
        }
        avgRatings.sort((a,b)-> a.compareTo(b));
        return avgRatings;
    }

    public String getTitle(String id){
        String title ="";
        for(int i=0;i<myMovies.size();i++){
            if(myMovies.get(i).getID().equals(id)){
                title =  myMovies.get(i).getTitle();
                break;
            }
        }
        if(title.equals("")){
            title="NO SUCH ID";
        }
        return title;
    }

    public String getId(String title){
        String Id = "";

        for(int i=0;i<myMovies.size();i++){
            if(myMovies.get(i).getTitle().equals(title)){
                Id=myMovies.get(i).getID();
            }
        }
        if(Id.equals("")){
            Id="NO SUCH TITLE";
        }
        return Id;
    }
}