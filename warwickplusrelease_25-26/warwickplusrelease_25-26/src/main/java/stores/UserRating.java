package stores;

import structures.MyHashMap;


// class for storing individual user ratings (storing ID, movie and rating that user gave, and sum of those rating)
public class UserRating {
    private final int userID;
    private MyHashMap<Integer, RatingData> rating;  // Integer -> movieID
    private float sumOfRatings = 0;

    public UserRating(int userID, MyHashMap<Integer, RatingData> userRating) {
        this.userID = userID;
        this.rating = userRating;
    }


    // getters for user data

    public int getUserID() {
        return userID;
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


    // setters to calculate sum of the user rating

    public void addSumOfRatings(float rating) {
        sumOfRatings = sumOfRatings + rating;
    }

    public void subSumoOfRatings(float rating) {
        sumOfRatings = sumOfRatings - rating;
    }
}