package stores;

import structures.MyHashMap;

public class UserRating {
    private final int userID;
    private MyHashMap<Integer, RatingData> userRating;

    public UserRating(int userID, MyHashMap<Integer, RatingData> userRating) {
        this.userID = userID;
        this.userRating = userRating;
    }

    public int getUserID() {
        return userID;
    }

    public MyHashMap<Integer, RatingData> getUserRating() {
        return userRating;
    }
}