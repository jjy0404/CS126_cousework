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

            MovieRating movieRating = moviesDB.get(movieid);
            UserRating userRating = usersDB.get(userid);

            if ((userRating == null) && (movieRating == null)) {
                RatingData ratingData = new RatingData(rating, timestamp);  // making new RatingData instance

                MyHashMap usersRating = new MyHashMap(100);  // HashMap for constructing UserRating instance
                MyHashMap moviesRating = new MyHashMap(100);  // HashMap for constructing MovieRating instance

                usersRating.put(movieid, ratingData);
                moviesRating.put(userid, ratingData);

                UserRating user = new UserRating(userid, usersRating);  // constructing UserRating instance
                MovieRating movie = new MovieRating(movieid, moviesRating);  // constructing MovieRating instance

                user.addSumOfRatings(rating);  // adding data to UserRating instance
                movie.addSumOfRatings(rating);  // adding data to MovieRating instance

                usersDB.put(userid, user);  // adding data to userDB
                moviesDB.put(movieid, movie);  // adding data to moviesDB

                // adding new data MyAVLTrees
                movieRatingCount.insert(1, movieid);
                userRatingCount.insert(1, userid);
                aveRatingCount.insert(rating, movieid);

                size++;
                return true;
            }
            else if ((userRating == null) && (movieRating != null)) { // MovieRating instance is already maded
                // removing old data from MyAVLTrees
                movieRatingCount.remove(movieRating.getRatingSize(), movieid);  
                aveRatingCount.remove(this.getMovieAverageRating(movieid), movieid);  

                // making new RatingData instance
                RatingData ratingData = new RatingData(rating, timestamp);

                // HashMap for constructing UserRating instance
                MyHashMap usersRating = new MyHashMap(100);

                // adding data to MovieRating and UserRating instance
                usersRating.put(movieid, ratingData);
                movieRating.getRating().put(userid, ratingData);
                movieRating.addSumOfRatings(rating);  
                UserRating user = new UserRating(userid, usersRating);
                user.addSumOfRatings(rating);
                usersDB.put(userid, user);

                // adding new data MyAVLTrees
                movieRatingCount.insert(movieRating.getRatingSize(), movieid);  
                userRatingCount.insert(1, userid);  
                aveRatingCount.insert(this.getMovieAverageRating(movieid), movieid);  

                size++;
                return true;
            }
            else if ((userRating != null) && (movieRating == null)) {  // UserRating instance is already maded
                userRatingCount.remove(userRating.getRatingSize(), userid);  // remove old data

                // making new RatingData instance
                RatingData ratingData = new RatingData(rating, timestamp);

                // HashMap for constructing MovieRating instance
                MyHashMap moviesRating = new MyHashMap(100);

                // adding data to MovieRating and UserRating instance
                userRating.getRating().put(movieid, ratingData);
                userRating.addSumOfRatings(rating);
                moviesRating.put(userid, ratingData);
                MovieRating movie = new MovieRating(movieid, moviesRating);
                movie.addSumOfRatings(rating);
                moviesDB.put(movieid, movie);

                // adding new data to MyAVLTrees
                movieRatingCount.insert(1, movieid);
                userRatingCount.insert(userRating.getRatingSize(), userid);
                aveRatingCount.insert(rating, movieid);

                size++;
                return true;
            }
            else if ((userRating.getRating().get(movieid) == null) && (movieRating.getRating().get(userid) == null)) {  //User and MovieRatign instace is already maded

                // deleting old data from MyAVLTrees
                movieRatingCount.remove(movieRating.getRatingSize(), movieid);
                userRatingCount.remove(userRating.getRatingSize(), userid);
                aveRatingCount.remove(this.getMovieAverageRating(movieid), movieid);

                // making new RatingData instance
                RatingData ratingData = new RatingData(rating, timestamp);

                // adding data to MovieRating and UserRating instance
                userRating.getRating().put(movieid, ratingData);
                userRating.addSumOfRatings(rating);
                movieRating.getRating().put(userid, ratingData);
                movieRating.addSumOfRatings(rating);

                // adding new data MyAVLTrees
                movieRatingCount.insert(movieRating.getRatingSize(), movieid);
                userRatingCount.insert(userRating.getRatingSize(), userid);
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

            UserRating userRating = usersDB.get(userid);
            MovieRating movieRating = moviesDB.get(movieid);

            if (((userRating.getRating().get(movieid) != null) && (movieRating.getRating().get(userid) != null))) {
                
                // deleting old data from MyAVLTrees
                movieRatingCount.remove(movieRating.getRatingSize(), movieid);      
                userRatingCount.remove(userRating.getRatingSize(), userid);  
                aveRatingCount.remove(this.getMovieAverageRating(movieid), movieid); 

                // adding data to MovieRating and UserRating instance
                userRating.subSumoOfRatings(userRating.getRating().get(movieid).getRating());  // subtracting sum of rating
                userRating.getRating().remove(movieid);  // deleting data from UserRating instance
                movieRating.subSumoOfRatings(movieRating.getRating().get(userid).getRating());  // subtracting sum of rating
                movieRating.getRating().remove(userid);  // deleting data from MovieRating instance

                // adding new data to MyAVLTrees
                movieRatingCount.insert(movieRating.getRatingSize(), movieid);
                userRatingCount.insert(userRating.getRatingSize(), userid);
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

            UserRating userRating = usersDB.get(userid);
            MovieRating movieRating = moviesDB.get(movieid);

            if ((userRating.getRating().get(movieid) == null) && (movieRating.getRating().get(userid) == null)) {  // not already rated
                

                // deleting old data from MyAVLTrees
                movieRatingCount.remove(movieRating.getRatingSize(), movieid);
                userRatingCount.remove(userRating.getRatingSize(), userid);
                aveRatingCount.remove(this.getMovieAverageRating(movieid), movieid);

                RatingData ratingData = new RatingData(rating, timestamp);

                 // adding data to MovieRating and UserRating instance
                userRating.getRating().put(movieid, ratingData);
                userRating.addSumOfRatings(rating);
                movieRating.getRating().put(userid, ratingData);
                movieRating.addSumOfRatings(rating);

                // adding new data to MyAVLTrees
                movieRatingCount.insert(movieRating.getRatingSize(), movieid);
                userRatingCount.insert(userRating.getRatingSize(), userid);
                aveRatingCount.insert(this.getMovieAverageRating(movieid), movieid);

                size++;
                return true;
            }
            else {  //already rated

                // deleting old data from MyAVLTrees
                aveRatingCount.remove(this.getMovieAverageRating(movieid), movieid);

                // adding data to MovieRating and UserRating instance
                userRating.subSumoOfRatings(userRating.getRating().get(movieid).getRating());
                movieRating.subSumoOfRatings(movieRating.getRating().get(userid).getRating());
                userRating.getRating().get(movieid).setRating(rating);
                userRating.getRating().get(movieid).setTimestamp(timestamp);
                userRating.addSumOfRatings(rating);
                movieRating.addSumOfRatings(rating);

                // adding new data to MyAVLTrees
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
        MovieRating movieRating = moviesDB.get(movieid);

        if ((movieid > -1) && (movieRating != null)) {

            MyArrayList<RatingData> ratingData = movieRating.getRating().valueSet();
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
         UserRating userRating = usersDB.get(userid);

        if ((userid > 0) && (userRating != null)) {

            MyArrayList<RatingData> ratingData = userRating.getRating().valueSet();
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
         MovieRating movieRating = moviesDB.get(movieid);

        if ((movieid > -1) && (movieRating != null)) {
            return movieRating.getSumOfRatings() /  movieRating.getRatingSize();
        }
        if ((stores.getMovies().getOriginalTitle(movieid) != null) && !(stores.getMovies().getOriginalTitle(movieid).equals("")) && (movieRating == null)) {  // checking if the film exists in Movies store
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
        UserRating userRating = usersDB.get(userid);

        if ((userid > 0) && (userRating != null)) {
            return userRating.getSumOfRatings() / userRating.getRatingSize();  // calculating average
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
        MovieRating movieRating = moviesDB.get(movieid);

        if ((movieRating != null) && (movieid > -1)) {
            return movieRating.getRatingSize();
        } 
        if ((stores.getMovies().getOriginalTitle(movieid) != null) && !(stores.getMovies().getOriginalTitle(movieid).equals("")) && (movieRating == null)) {  // checking if the film exists in Movies store
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
