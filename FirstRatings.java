import edu.duke.*;
import jdk.jfr.consumer.RecordedFrame;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import org.apache.commons.csv.*;

public class FirstRatings {
    public static void main(String[] args) throws FileNotFoundException, IOException {
        FirstRatings firstRatings=new FirstRatings();
        // firstRatings.testLoadMovies();
        // System.out.println("\n");
        // firstRatings.testLoadRaters();
        
    }

    // return a Arraylist of Movie object
    public ArrayList<Movie> loadMovies(String fileName) throws FileNotFoundException, IOException {
        CSVFormat format = CSVFormat.RFC4180.withHeader().withDelimiter(',');

        // initialize the CSVParser object
        CSVParser parser = new CSVParser(new FileReader(fileName), format);

        ArrayList<Movie> movies = new ArrayList<Movie>();
        for (CSVRecord record : parser) {
            String id = record.get("id");
            String title = record.get("title");
            String year = record.get("year");
            String country = record.get("country");
            String genre = record.get("genre");
            String director = record.get("director");
            int minutes = Integer.parseInt(record.get("minutes"));
            String img = record.get("poster");

            Movie movie = new Movie(id, title, year, genre, director, country, img, minutes);
            movies.add(movie);
        }
        // close the parser
        try {
            parser.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return movies;
    }

    public void testLoadMovies() throws FileNotFoundException, IOException {
        FirstRatings firstRatings = new FirstRatings();
        ArrayList<Movie> movieList = firstRatings.loadMovies("data/ratedmovies_short.csv");
        // ArrayList<Movie> movieList =
        // firstRatings.loadMovies("data/ratedmoviesfull.csv");

        System.out.println("Total Movie " + movieList.size());
        // for(int i=0;i<movieList.size();i++)
        // {
        // System.out.print(movieList.get(i)+"\n");
        // }
        int movieOfComedy = 0;
        int movie150 = 0;
        HashMap<String, Integer> directorsWork = new HashMap<String, Integer>();
        for (int i = 0; i < movieList.size(); i++) {
            if (movieList.get(i).getGenres().contains("Comedy")) {
                movieOfComedy++;
            }
            if (movieList.get(i).getMinutes() > 150) {
                movie150++;
            }
            if (!directorsWork.containsKey(movieList.get(i).getDirector())) {
                directorsWork.put(movieList.get(i).getDirector(), 1);
                // if they don't find it in prev put new entry
            } else if (directorsWork.containsKey(movieList.get(i).getDirector())) {
                directorsWork.put(movieList.get(i).getDirector(),
                        directorsWork.get(movieList.get(i).getDirector()) + 1);
                // if they find increase the old entry
            }
        }
        System.out.println("Count of Comedy Movies " + movieOfComedy);
        System.out.println("Count of movies longer than 150 Minutes is" + movie150);

        // Calculating director that made maximum number of movies
        int max = 0;
        String name = "";
        for (String i : directorsWork.keySet()) {
            if (directorsWork.get(i) > max) {
                max = directorsWork.get(i);
                name = i;
            }
        }
        System.out.println("Director " + name + "has made " + max + " movies");

    }

    public ArrayList<Rater> loadRaters(String fileName) throws FileNotFoundException, IOException {
        CSVFormat format = CSVFormat.RFC4180.withHeader().withDelimiter(',');
        // initialize the CSVParser object
        CSVParser parser = new CSVParser(new FileReader(fileName), format);

        ArrayList<Rater> raterList = new ArrayList<Rater>();
        HashMap<String, Boolean> raterMap = new HashMap<String, Boolean>();// map is raterMap ( rater, Boolean)
        HashMap<String, Integer> movieMap = new HashMap<String, Integer>();// item_name, occurence of movie

        for (CSVRecord record : parser) {
            String movie_id = record.get("movie_id");
            double rating_value = Double.parseDouble(record.get("rating"));
            String rater_id = record.get("rater_id");

            // if new entry for movieMap
            if (movieMap.get(movie_id) == null) {
                movieMap.replace(movie_id, 0, 1);
            }
            // if old entry for movieMap
            else {
                movieMap.replace(movie_id, movieMap.get(movie_id), movieMap.get(movie_id) + 1);
            }

            // if rater_id does not available in map(rater_id,bool) then
            // add new entry for map(rater_id,bool) and raterList
            if (!raterMap.containsKey(rater_id)) {
                Rater newRater = new EfficientRater(rater_id);
                newRater.addRating(movie_id, rating_value);

                raterMap.put(rater_id, true);
                raterList.add(newRater);

            }
            // if rater_id is available in raterMap(rater_id,bool) add new rating for that
            // rater in raterList
            else {
                for (int i = 0; i < raterList.size(); i++) {
                    if (raterList.get(i).getID().equals(rater_id)) {
                        raterList.get(i).addRating(movie_id, rating_value);
                    }
                }
            }
        }

        // close the parser
        try {
            parser.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // System.out.println("Hello");
        movieMap.forEach((k, v) -> System.out.println("movie_id" + k + "occurence " + v));
        return raterList;

    }

    public void testLoadRaters() throws FileNotFoundException, IOException {
        FirstRatings firstRatings = new FirstRatings();
        ArrayList<Rater> raterList = firstRatings.loadRaters("data/ratings_short.csv");
        // ArrayList<Rater> raterList= firstRatings.loadRaters("data/ratings.csv");

        System.out.println("There are " + raterList.size() + " raters");

        int maximumRatings = 0;
        for (Rater rater : raterList) {
            if (maximumRatings < rater.getItemsRated().size()) {
                maximumRatings = rater.getItemsRated().size();
            }
        }

        int raterIdOfMaxRatings = 0;
        for (Rater rater : raterList) {
            if (rater.getItemsRated().size() == maximumRatings) {
                raterIdOfMaxRatings = Integer.parseInt(rater.getID());
            }
        }

        System.out.println("Rater with id " + raterIdOfMaxRatings + " did maximum ratings " + maximumRatings);

        int desiredNumber = 0;
        for (int i = 0; i < raterList.size(); i++) {
            if (raterList.get(i).getID().equals("193")) {
                desiredNumber = raterList.get(i).getItemsRated().size();
            }
        }

        System.out.println("Rater with Id 193 has rated " + desiredNumber + " movies");

        // movieRatings=movieRatings per movie
        HashMap<String, Integer> movieRatings = new HashMap<String, Integer>();
        ArrayList<String> tempList;
        for (int i = 0; i < raterList.size(); i++) {

            tempList = raterList.get(i).getItemsRated(); // second string arrayList

            for (int j = 0; j < tempList.size(); j++) {// nested loop

                // if movieRating does not have that item
                if (!movieRatings.containsKey(tempList.get(j))) {
                    movieRatings.put(tempList.get(j), 1);
                }
                // if movieRating have the that item
                else {
                    movieRatings.replace(tempList.get(j), movieRatings.get(tempList.get(j)),
                            movieRatings.get(tempList.get(j) + 1));
                }

            }
        }

        System.out.println("\nprinting out movie ratings per movie\n");
        movieRatings.forEach((k, v) -> System.out.println("movie " + k + " number of ratings " + v));
        System.out.println("\nTotal number of unique movies is " + movieRatings.size());

    }

   

}