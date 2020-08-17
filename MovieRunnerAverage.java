import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class MovieRunnerAverage {


    public static void main(String args[]) throws FileNotFoundException, IOException {
        MovieRunnerAverage movieRunnerAverage = new MovieRunnerAverage();
        movieRunnerAverage.printAverageRatings();
        movieRunnerAverage.getAverageRatingOneMovie();
    }
    public void printAverageRatings() throws FileNotFoundException, IOException {
        SecondRatings secondRatings = new SecondRatings("data/ratedmoviesfull.csv","data/ratings.csv");
        // SecondRatings secondRatings = new SecondRatings("data/ratedmovies_short.csv","data/ratings_short.csv");
        int movieSize=secondRatings.getMovieSize();
        int raterSize=secondRatings.getRaterSize();
        System.out.println("movieSize is "+ movieSize+" raterSize is "+raterSize);

        ArrayList<Rating> ratingList =secondRatings.getAverageRatings(12);
        // for(int i=0;i<ratingList.size();i++){
        //     System.out.println(ratingList.get(i).getValue()+" " +secondRatings.getTitle(ratingList.get(i).getItem()));
        // }
        System.out.println(secondRatings.getTitle(ratingList.get(0).getItem())+" "+ratingList.get(0).getValue());
        return ;
    }

    public void getAverageRatingOneMovie() throws FileNotFoundException, IOException {
        // SecondRatings secondRatings = new SecondRatings("data/ratedmovies_short.csv","data/ratings_short.csv");
        SecondRatings secondRatings = new SecondRatings("data/ratedmoviesfull.csv","data/ratings.csv");

        // String movieName="The Godfather";
        String movieName="Vacation";
        String Id = secondRatings.getId(movieName);
        ArrayList<Rating> ratingList = secondRatings.getAverageRatings(3);
        for(int i=0;i<ratingList.size();i++){
            if(ratingList.get(i).getItem().equals(Id)){
                System.out.println("\nRating for the Movie "+movieName+" is "+ratingList.get(i).getValue());
            }
        }
        
    }
}