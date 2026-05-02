package stores;

import java.time.LocalDateTime;

public class RatingData {
    private float rating;
    private LocalDateTime timestamp;

    public RatingData (float rating, LocalDateTime timestamp) {
        this.rating = rating;
        this.timestamp = timestamp;
    }

    public float getRating() {
        return rating;
    }

    public LocalDateTime LocalDateTime() {
        return timestamp;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}