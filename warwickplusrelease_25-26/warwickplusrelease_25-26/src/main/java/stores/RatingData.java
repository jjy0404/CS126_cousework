package stores;

import java.time.LocalDateTime;


// class for storing individual rating information (rating and timestamp)
public class RatingData {
    private float rating;
    private LocalDateTime timestamp;

    public RatingData (float rating, LocalDateTime timestamp) {
        this.rating = rating;
        this.timestamp = timestamp;
    }


    // getters for rating data

    public float getRating() {
        return rating;
    }

    public LocalDateTime LocalDateTime() {
        return timestamp;
    }


    // setters for rating data

    public void setRating(float rating) {
        this.rating = rating;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}