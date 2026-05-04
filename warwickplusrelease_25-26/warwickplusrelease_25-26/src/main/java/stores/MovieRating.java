package stores;

import structures.MyHashMap;

public class MovieRating {

    // class (instance) that stores individual movie data (movieID, all of the ratings that individual user gave, sum of that ratings)
    private int movieID;
    private MyHashMap<Integer, RatingData> rating;  // int is userID
    private float sumOfRatings = 0;

    public MovieRating(int movieID, MyHashMap<Integer, RatingData> movieRating) {
        this.movieID = movieID;
        this.rating = movieRating;
    }


    // getters for movie data

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


    // setters for calcultating sum of the ratings

    public void addSumOfRatings(float rating) {
        sumOfRatings = sumOfRatings + rating;
    }

    public void subSumoOfRatings(float rating) {
        sumOfRatings = sumOfRatings - rating;
    }
}