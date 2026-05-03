package stores;

import java.time.LocalDateTime;

import interfaces.IRatings;
import structures.MyAVLTree;
import structures.MyArrayList;
import structures.MyHashMap;

public class Ratings implements IRatings {
    Stores stores;
    MyHashMap<Integer, UserRating> usersDB;  //integer (userID)
    MyHashMap<Integer, MovieRating> moviesDB;  //integer (movieID)
    MyAVLTree<Integer, Integer> movieRatingCount;  // (rating num, movieID)
    MyAVLTree<Integer, Integer> userRatingCount;  // (rating num, userID)
    MyAVLTree<Float, Integer> aveRatingCount;  //(average rate, movieID)
    int size = 0;

    /**
     * The constructor for the Ratings data store. This is where you should
     * initialise your data structures.
     * @param stores An object storing all the different key stores,
     *               including itself
     */
    public Ratings(Stores stores) {
        this.stores = stores;
        // TODO Add initialisation of data structure here
        this.usersDB = new MyHashMap<>(1000);
        this.moviesDB = new MyHashMap<>(1000);
        this.movieRatingCount = new MyAVLTree<>();
        this.userRatingCount = new MyAVLTree<>();
        this.aveRatingCount = new MyAVLTree<>();
    }

    /**
     * Adds a rating to the data structure. The rating is made unique by its user ID
     * and its movie ID
     * 
     * @param userID    The user ID
     * @param movieID   The movie ID
     * @param rating    The rating gave to the film by this user (between 0 and 5
     *                  inclusive)
     * @param timestamp The time at which the rating was made
     * @return TRUE if the data able to be added, FALSE otherwise
     */
    @Override
    public boolean add(int userid, int movieid, float rating, LocalDateTime timestamp) {
        // TODO Implement this function
        if ((userid > 0) && (movieid > -1)) { 
            if ((usersDB.get(userid) == null) && (moviesDB.get(movieid) == null)) {
                RatingData ratingData = new RatingData(rating, timestamp);  // making new RatingData instance
                MyHashMap userRating = new MyHashMap(100);  // HashMap for constructing UserRating instance
                MyHashMap movieRating = new MyHashMap(100);  // HashMap for constructing MovieRating instance
                userRating.put(movieid, ratingData);
                movieRating.put(userid, ratingData);
                UserRating user = new UserRating(userid, userRating);  // constructing UserRating instance
                MovieRating movie = new MovieRating(movieid, movieRating);  // constructing MovieRating instance
                user.addSumOfRatings(rating);  // adding data to UserRating instance
                movie.addSumOfRatings(rating);  // adding data to MovieRating instance
                usersDB.put(userid, user);  // adding data to userDB
                moviesDB.put(movieid, movie);  // adding data to moviesDB
                movieRatingCount.insert(1, movieid);
                userRatingCount.insert(1, userid);
                aveRatingCount.insert(rating, movieid);
                size++;
                return true;
            }
            else if ((usersDB.get(userid) == null) && (moviesDB.get(movieid) != null)) { // MovieRating instance is already maded
                movieRatingCount.remove(moviesDB.get(movieid).getRatingSize(), movieid);  // remove old data
                aveRatingCount.remove(this.getMovieAverageRating(movieid), movieid);  // remove old data
                RatingData ratingData = new RatingData(rating, timestamp);
                MyHashMap userRating = new MyHashMap(100);
                userRating.put(movieid, ratingData);
                moviesDB.get(movieid).getRating().put(userid, ratingData);
                moviesDB.get(movieid).addSumOfRatings(rating);  
                UserRating user = new UserRating(userid, userRating);
                user.addSumOfRatings(rating);
                usersDB.put(userid, user);
                movieRatingCount.insert(moviesDB.get(movieid).getRatingSize(), movieid);  // updating data
                userRatingCount.insert(1, userid);  // updating data
                aveRatingCount.insert(this.getMovieAverageRating(movieid), movieid);  // updating data
                size++;
                return true;
            }
            else if ((usersDB.get(userid) != null) && (moviesDB.get(movieid) == null)) {  // UserRating instance is already maded
                userRatingCount.remove(usersDB.get(userid).getRatingSize(), userid);  // remove old data
                RatingData ratingData = new RatingData(rating, timestamp);
                MyHashMap movieRating = new MyHashMap(100);
                usersDB.get(userid).getRating().put(movieid, ratingData);
                usersDB.get(userid).addSumOfRatings(rating);
                movieRating.put(userid, ratingData);
                MovieRating movie = new MovieRating(movieid, movieRating);
                movie.addSumOfRatings(rating);
                moviesDB.put(movieid, movie);
                movieRatingCount.insert(1, movieid);
                userRatingCount.insert(usersDB.get(userid).getRatingSize(), userid);
                aveRatingCount.insert(rating, movieid);
                size++;
                return true;
            }
            else if ((usersDB.get(userid).getRating().get(movieid) == null) && (moviesDB.get(movieid).getRating().get(userid) == null)) {  //User and MovieRatign instace is already maded
                movieRatingCount.remove(moviesDB.get(movieid).getRatingSize(), movieid);
                userRatingCount.remove(usersDB.get(userid).getRatingSize(), userid);
                aveRatingCount.remove(this.getMovieAverageRating(movieid), movieid);
                RatingData ratingData = new RatingData(rating, timestamp);
                usersDB.get(userid).getRating().put(movieid, ratingData);
                usersDB.get(userid).addSumOfRatings(rating);
                moviesDB.get(movieid).getRating().put(userid, ratingData);
                moviesDB.get(movieid).addSumOfRatings(rating);
                movieRatingCount.insert(moviesDB.get(movieid).getRatingSize(), movieid);
                userRatingCount.insert(usersDB.get(userid).getRatingSize(), userid);
                aveRatingCount.insert(this.getMovieAverageRating(movieid), movieid);
                size++;
                return true;
            }
            else {
                return false;
            }
        }
        return false;
    }

    /**
     * Removes a given rating, using the user ID and the movie ID as the unique
     * identifier
     * 
     * @param userID  The user ID
     * @param movieID The movie ID
     * @return TRUE if the data was removed successfully, FALSE otherwise
     */
    @Override
    public boolean remove(int userid, int movieid) {
        // TODO Implement this function
        if (((userid > 0) && (movieid > -1)) && (usersDB.get(userid) != null) && (moviesDB.get(movieid) != null)) {
            if (((usersDB.get(userid).getRating().get(movieid) != null) && (moviesDB.get(movieid).getRating().get(userid) != null))) {
                movieRatingCount.remove(moviesDB.get(movieid).getRatingSize(), movieid);  // deleting old data
                userRatingCount.remove(usersDB.get(userid).getRatingSize(), userid);  // deleting old data
                aveRatingCount.remove(this.getMovieAverageRating(movieid), movieid);  // deleting old data
                usersDB.get(userid).subSumoOfRatings(usersDB.get(userid).getRating().get(movieid).getRating());  // subtracting sum of rating
                usersDB.get(userid).getRating().remove(movieid);  // deleting data from UserRating instance
                moviesDB.get(movieid).subSumoOfRatings(moviesDB.get(movieid).getRating().get(userid).getRating());  // subtracting sum of rating
                moviesDB.get(movieid).getRating().remove(userid);  // deleting data from MovieRating instance
                movieRatingCount.insert(moviesDB.get(movieid).getRatingSize(), movieid);
                userRatingCount.insert(usersDB.get(userid).getRatingSize(), userid);
                aveRatingCount.insert(this.getMovieAverageRating(movieid), movieid);
                size--;
                return true;
            }
        }
        return false;
    }

    /**
     * Sets a rating for a given user ID and movie ID. Therefore, should the given
     * user have already rated the given movie, the new data should overwrite the
     * existing rating. However, if the given user has not already rated the given
     * movie, then this rating should be added to the data structure
     * 
     * @param userID    The user ID
     * @param movieID   The movie ID
     * @param rating    The new rating to be given to the film by this user (between
     *                  0 and 5 inclusive)
     * @param timestamp The time at which the new rating was made
     * @return TRUE if the data able to be added/updated, FALSE otherwise
     */
    @Override
    public boolean set(int userid, int movieid, float rating, LocalDateTime timestamp) {  
        // TODO Implement this function
        if (((userid > 0) && (movieid > -1)) && ((usersDB.get(userid) != null) && (moviesDB.get(movieid) != null))) {
            if ((usersDB.get(userid).getRating().get(movieid) == null) && (moviesDB.get(movieid).getRating().get(userid) == null)) {  // not already rated
                movieRatingCount.remove(moviesDB.get(movieid).getRatingSize(), movieid);
                userRatingCount.remove(usersDB.get(userid).getRatingSize(), userid);
                aveRatingCount.remove(this.getMovieAverageRating(movieid), movieid);
                RatingData ratingData = new RatingData(rating, timestamp);
                usersDB.get(userid).getRating().put(movieid, ratingData);
                usersDB.get(userid).addSumOfRatings(rating);
                moviesDB.get(movieid).getRating().put(userid, ratingData);
                moviesDB.get(movieid).addSumOfRatings(rating);
                movieRatingCount.insert(moviesDB.get(movieid).getRatingSize(), movieid);
                userRatingCount.insert(usersDB.get(userid).getRatingSize(), userid);
                aveRatingCount.insert(this.getMovieAverageRating(movieid), movieid);
                size++;
                return true;
            }
            else {  //already rated
                aveRatingCount.remove(this.getMovieAverageRating(movieid), movieid);
                usersDB.get(userid).subSumoOfRatings(usersDB.get(userid).getRating().get(movieid).getRating());
                moviesDB.get(movieid).subSumoOfRatings(moviesDB.get(movieid).getRating().get(userid).getRating());
                usersDB.get(userid).getRating().get(movieid).setRating(rating);
                usersDB.get(userid).getRating().get(movieid).setTimestamp(timestamp);
                usersDB.get(userid).addSumOfRatings(rating);
                moviesDB.get(movieid).addSumOfRatings(rating);
                aveRatingCount.insert(this.getMovieAverageRating(movieid), movieid);
                return true;
            }
        }
        if ((userid > 0) && (movieid > -1)) {
            this.add(userid, movieid, rating, timestamp);
            return true;
        }
        return false;
    }

    /**
     * Get all the ratings for a given film
     * 
     * @param movieID The movie ID
     * @return An array of ratings. If there are no ratings or the film cannot be
     *         found in Ratings, then return an empty array
     */
    @Override
    public float[] getMovieRatings(int movieid) {
        // TODO Implement this function
        if ((movieid > -1) && (moviesDB.get(movieid) != null)) {
            MyArrayList<RatingData> ratingData = moviesDB.get(movieid).getRating().valueSet();
            float[] allMovieRatings = new float[ratingData.size()];

            for (int i = 0; i < ratingData.size(); i++) {  // copying arraylist to array
                allMovieRatings[i] = ratingData.get(i).getRating();
            }
            return allMovieRatings;
        }

        return new float[0];
    }

    /**
     * Get all the ratings for a given user
     * 
     * @param userID The user ID
     * @return An array of ratings. If there are no ratings or the user cannot be
     *         found in Ratings, then return an empty array
     */
    @Override
    public float[] getUserRatings(int userid) {
        // TODO Implement this function
        if ((userid > 0) && (usersDB.get(userid) != null)) {
            MyArrayList<RatingData> ratingData = usersDB.get(userid).getRating().valueSet();
            float[] allUserRatings = new float[ratingData.size()];

            for (int i = 0; i < ratingData.size(); i++) {  // copying arraylist to array
                allUserRatings[i] = ratingData.get(i).getRating();
            }
            return allUserRatings;
        }

        return new float[0];
    }

    /**
     * Get the average rating for a given film
     * 
     * @param movieID The movie ID
     * @return Produces the average rating for a given film. 
     *         If the film cannot be found in Ratings, but does exist in the Movies store, return 0.0f. 
     *         If the film cannot be found in Ratings or Movies stores, return -1.0f.
     */
    @Override
    public float getMovieAverageRating(int movieid) {
        // TODO Implement this function
        if ((movieid > -1) && (moviesDB.get(movieid) != null)) {
            return moviesDB.get(movieid).getSumOfRatings() /  moviesDB.get(movieid).getRatingSize();
        }
        if ((stores.getMovies().getOriginalTitle(movieid) != null) && !(stores.getMovies().getOriginalTitle(movieid).equals("")) && (moviesDB.get(movieid) == null)) {  // checking if the film exists in Movies store
            return 0.0f;
        }
        return -1.0f;
    }

    /**
     * Get the average rating for a given user
     * 
     * @param userID The user ID
     * @return Produces the average rating for a given user. If the user cannot be
     *         found in Ratings, or there are no rating, return -1.0f
     */
    @Override
    public float getUserAverageRating(int userid) {
        // TODO Implement this function
        if ((userid > 0) && (usersDB.get(userid) != null)) {
            return usersDB.get(userid).getSumOfRatings() / usersDB.get(userid).getRatingSize();  // calculating average
        }
        return -1.0f;
    }

    /**
     * Gets the top N movies with the most ratings, in order from most to least
     * 
     * @param num The number of movies that should be returned
     * @return A sorted array of movie IDs with the most ratings. The array should be
     *         no larger than num. If there are less than num movies in the store,
     *         then the array should be the same length as the number of movies in Ratings
     */
    @Override
    public int[] getMostRatedMovies(int num) {
        // TODO Implement this function
        MyArrayList<Integer> nRatedMovies = movieRatingCount.getTopN(num);
        int[] mostRatedMovies = new int[nRatedMovies.size()];

        for (int i = 0; i < nRatedMovies.size(); i++) {  // copying arraylist to array
            mostRatedMovies[i] = nRatedMovies.get(i);
        }

        return mostRatedMovies;
    }

    /**
     * Gets the top N users with the most ratings, in order from most to least
     * 
     * @param num The number of users that should be returned
     * @return A sorted array of user IDs with the most ratings. The array should be
     *         no larger than num. If there are less than num users in the store,
     *         then the array should be the same length as the number of users in Ratings
     */
    @Override
    public int[] getMostRatedUsers(int num) {
        // TODO Implement this function
        MyArrayList<Integer> nRatedUsers = userRatingCount.getTopN(num);
        int[] mostRatedUsers = new int[nRatedUsers.size()];

        for (int i = 0; i < nRatedUsers.size(); i++) {  // copying arraylist to array
            mostRatedUsers[i] = nRatedUsers.get(i);
        }

        return mostRatedUsers;
    }

    /**
     * Get the number of ratings that a movie has
     * 
     * @param movieid The movie id to be found
     * @return The number of ratings the specified movie has. 
     *         If the movie exists in the Movies store, but there are no ratings for it, then return 0. 
     *         If the movie does not exist in the Ratings or Movies store, then return -1.
     */
    @Override
    public int getNumRatings(int movieid) {
        // TODO Implement this function
        if ((moviesDB.get(movieid) != null) && (movieid > -1)) {
            return moviesDB.get(movieid).getRatingSize();
        } 
        if ((stores.getMovies().getOriginalTitle(movieid) != null) && !(stores.getMovies().getOriginalTitle(movieid).equals("")) && (moviesDB.get(movieid) == null)) {  // checking if the film exists in Movies store
            return 0;
        }

        return -1;
    }

    /**
     * Get the highest average rated film IDs, in order of there average rating
     * (hightst first).
     * 
     * @param numResults The maximum number of results to be returned
     * @return An array of the film IDs with the highest average ratings, highest
     *         first. If there are less than num movies in the store,
     *         then the array should be the same length as the number of movies in Ratings
     */
    @Override
    public int[] getTopAverageRatedMovies(int numResults) {
        // TODO Implement this function
        MyArrayList<Integer> nRatedMovies = aveRatingCount.getTopN(numResults);
        int[] topRatedMovies = new int[nRatedMovies.size()];

        for (int i = 0; i < nRatedMovies.size(); i++) {  // copying arraylist to array
            topRatedMovies[i] = nRatedMovies.get(i);
        }

        return topRatedMovies;
    }

    /**
     * Gets the number of ratings in the data structure
     * 
     * @return The number of ratings in the data structure
     */
    @Override
    public int size() {
        // TODO Implement this function

        return size;
    }
}
