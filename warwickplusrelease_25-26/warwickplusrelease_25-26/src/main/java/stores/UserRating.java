package stores;

import structures.MyHashMap;

public class UserRating {
    private final int userID;
    private MyHashMap<Integer, RatingData> rating;  //movieID
    private float sumOfRatings = 0;

    public UserRating(int userID, MyHashMap<Integer, RatingData> userRating) {
        this.userID = userID;
        this.rating = userRating;
    }

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

    public void addSumOfRatings(float rating) {
        sumOfRatings = sumOfRatings + rating;
    }

    public void subSumoOfRatings(float rating) {
        sumOfRatings = sumOfRatings - rating;
    }
}