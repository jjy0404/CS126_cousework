package stores;

import structures.MyHashMap;

public class MovieRating {
    private int movieID;
    private MyHashMap<Integer, RatingData> rating;  // int is userID
    private float sumOfRatings = 0;

    public MovieRating(int movieID, MyHashMap<Integer, RatingData> movieRating) {
        this.movieID = movieID;
        this.rating = movieRating;
    }

    public int getMovieID() {
        return movieID;
    }

    public MyHashMap<Integer, RatingData> getRating() {
        return rating;
    }

    public int getRatingSize() {
        return rating.size();
    }

    public float getSumOfRatings() {
        return sumOfRatings;
    }

    public void addSumOfRatings(float rating) {
        sumOfRatings = sumOfRatings + rating;
    }

    public void subSumoOfRatings(float rating) {
        sumOfRatings = sumOfRatings - rating;
    }
}