package stores;

import structures.MyHashMap;

public class MovieRating {
    private int movieID;
    private MyHashMap<Integer, RatingData> movieRating;

    public MovieRating(int movieID, MyHashMap<Integer, RatingData> movieRating) {
        this.movieID = movieID;
        this.movieRating = movieRating;
    }

    public int getMovieID() {
        return movieID;
    }

    public MyHashMap<Integer, RatingData> getMovieRating() {
        return movieRating;
    }
}