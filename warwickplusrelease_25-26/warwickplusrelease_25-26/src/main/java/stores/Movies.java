package stores;

import java.time.LocalDate;

import interfaces.IMovies;
import structures.MyAVLTree;
import structures.MyArrayList;
import structures.MyHashMap;


public class Movies implements IMovies{
    Stores stores;
    MyHashMap<Integer, Movie> movieDB;
    MyHashMap<Integer, CollectionInfo> collectionDB;
    MyAVLTree<LocalDate, Integer> moviesByDate;

    /**
     * The constructor for the Movies data store. This is where you should
     * initialise your data structures.
     * @param stores An object storing all the different key stores,
     *               including itself
     */
    public Movies(Stores stores) {
        this.stores = stores;
        // TODO Add initialisation of data structure here
        this.movieDB = new MyHashMap<>(1000);
        this.collectionDB = new MyHashMap<>(1000);
        this.moviesByDate = new MyAVLTree<>();

    }

    /**
     * Adds data about a film to the data structure
     * 
     * @param id               The unique ID for the film
     * @param title            The English title of the film
     * @param originalTitle    The original language title of the film
     * @param overview         An overview of the film
     * @param tagline          The tagline for the film (empty string if there is no
     *                         tagline)
     * @param status           Current status of the film
     * @param genres           An array of Genre objects related to the film
     * @param release          The release date for the film
     * @param budget           The budget of the film in US Dollars
     * @param revenue          The revenue of the film in US Dollars
     * @param languages        An array of ISO 639 language codes for the film
     * @param originalLanguage An ISO 639 language code for the original language of
     *                         the film
     * @param runtime          The runtime of the film in minutes
     * @param homepage         The URL to the homepage of the film
     * @param adult            Whether the film is an adult film
     * @param video            Whether the film is a "direct-to-video" film
     * @param poster           The unique part of the URL of the poster (empty if
     *                         the URL is not known)
     * @return TRUE if the data able to be added, FALSE otherwise
     * 
     */
    @Override
    public boolean add(int id, String title, String originalTitle, String overview, String tagline, String status, Genre[] genres, LocalDate release, long budget, long revenue, String[] languages, String originalLanguage, double runtime, String homepage, boolean adult, boolean video, String poster) {
        // TODO Implement this function
        if ((id != -1) && (movieDB.get(id) == null)) {
            Movie newMovie = new Movie(id, title, originalTitle, overview, tagline, status, genres, release, budget, revenue, languages, originalLanguage, runtime, homepage, adult, video, poster);
            movieDB.put(id, newMovie);
            if (release != null) {
                moviesByDate.insert(release, id);
            }
            return true;
        }
        return false;
    }

    /**
     * Removes a film from the data structure, and any data
     * added through this class related to the film
     * 
     * @param id The film ID
     * @return TRUE if the film has been removed successfully, FALSE otherwise
     */
    @Override
    public boolean remove(int id) {
        // TODO Implement this function
        if ((id != -1) && (movieDB.get(id) != null)) {
            int collectionID = movieDB.get(id).getCollectionID();
            if ((collectionID != -1) && (collectionDB.get(collectionID) != null)) {
                collectionDB.get(collectionID).removeFilmID(id);
            }
            moviesByDate.remove(movieDB.get(id).getRelease(), id);
            movieDB.remove(id);
            return true;
        }
        return false;
    }
    

    /**
     * Gets all the IDs for all films
     * 
     * @return An array of all film IDs stored
     */
    @Override
    public int[] getAllIDs() {
        // TODO Implement this function
        MyArrayList<Integer> keys = movieDB.keySet();
        if (keys.size() > 0) {
            int[] allIDs = new int[keys.size()];
            for (int i = 0; i < keys.size(); i++) {
                allIDs[i] = keys.get(i);
            }
            return allIDs;
        }

        return null;
    }

    /**
     * Finds the film IDs of all films released within a given range. If a film is
     * released either on the start or end dates, then that film should not be
     * included
     * 
     * @param start The start point of the range of dates
     * @param end   The end point of the range of dates
     * @return An array of film IDs that were released between start and end
     */
    @Override
    public int[] getAllIDsReleasedInRange(LocalDate start, LocalDate end) {
        // TODO Implement this function
        MyArrayList<Integer> ids = moviesByDate.getValuesInRange(start, end);
        
            int[] allIDs = new int[ids.size()];
            for (int i = 0; i < ids.size(); i++) {
                allIDs[i] = ids.get(i);
            }
        return allIDs;
    }

    /**
     * Gets the title of a particular film, given the ID number of that film
     * 
     * @param id The movie ID
     * @return The title of the requested film. If the film cannot be found, then
     *         return null
     */
    @Override
    public String getTitle(int id) {
        // TODO Implement this function
        if ((id != -1) && (movieDB.get(id) != null)) {
            return movieDB.get(id).getTitle();
        }
        return null;
    }

    /**
     * Gets the original title of a particular film, given the ID number of that
     * film
     * 
     * @param id The movie ID
     * @return The original title of the requested film. If the film cannot be
     *         found, then return null
     */
    @Override
    public String getOriginalTitle(int id) {
        // TODO Implement this function
        if ((id != -1) && (movieDB.get(id) != null)) {
            return movieDB.get(id).getOriginalTitle();
        }
        return null;
    }

    /**
     * Gets the overview of a particular film, given the ID number of that film
     * 
     * @param id The movie ID
     * @return The overview of the requested film. If the film cannot be found, then
     *         return null
     */
    @Override
    public String getOverview(int id) {
        // TODO Implement this function
        if ((id != -1) && (movieDB.get(id) != null)) {
            return movieDB.get(id).getOverview();
        }
        return null;
    }

    /**
     * Gets the tagline of a particular film, given the ID number of that film
     * 
     * @param id The movie ID
     * @return The tagline of the requested film. If the film cannot be found, then
     *         return null
     */
    @Override
    public String getTagline(int id) {
        // TODO Implement this function
        if ((id != -1) && (movieDB.get(id) != null)) {
            return movieDB.get(id).getTagline();
        }
        return null;
    }

    /**
     * Gets the status of a particular film, given the ID number of that film
     * 
     * @param id The movie ID
     * @return The status of the requested film. If the film cannot be found, then
     *         return null
     */
    @Override
    public String getStatus(int id) {
        // TODO Implement this function
        if ((id != -1) && (movieDB.get(id) != null)) {
            return movieDB.get(id).getStatus();
        }
        return null;
    }

    /**
     * Gets the genres of a particular film, given the ID number of that film
     * 
     * @param id The movie ID
     * @return The genres of the requested film. If the film cannot be found, then
     *         return null
     */
    @Override
    public Genre[] getGenres(int id) {
        // TODO Implement this function
        if ((id != -1) && (movieDB.get(id) != null)) {
            return movieDB.get(id).getGenres();
        }
        return null;
    }

    /**
     * Gets the release date of a particular film, given the ID number of that film
     * 
     * @param id The movie ID
     * @return The release date of the requested film. If the film cannot be found,
     *         then return null
     */
    @Override
    public LocalDate getRelease(int id) {
        // TODO Implement this function
        if ((id != -1) && (movieDB.get(id) != null)) {
            return movieDB.get(id).getRelease();
        }
        return null;
    }

    /**
     * Gets the budget of a particular film, given the ID number of that film
     * 
     * @param id The movie ID
     * @return The budget of the requested film. If the film cannot be found, then
     *         return -1
     */
    @Override
    public long getBudget(int id) {
        // TODO Implement this function
        if ((id != -1) && (movieDB.get(id) != null)) {
            return movieDB.get(id).getBudget();
        }
        return -1;
    }

    /**
     * Gets the revenue of a particular film, given the ID number of that film
     * 
     * @param id The movie ID
     * @return The revenue of the requested film. If the film cannot be found, then
     *         return -1
     */
    @Override
    public long getRevenue(int id) {
        // TODO Implement this function
        if ((id != -1) && (movieDB.get(id) != null)) {
            return movieDB.get(id).getRevenue();
        }
        return -1;
    }

    /**
     * Gets the languages of a particular film, given the ID number of that film
     * 
     * @param id The movie ID
     * @return The languages of the requested film. If the film cannot be found,
     *         then return null
     */
    @Override
    public String[] getLanguages(int id) {
        // TODO Implement this function
        if ((id != -1) && (movieDB.get(id) != null)) {
            return movieDB.get(id).getLanguages();
        }
        return null;
    }

    /**
     * Gets the original language of a particular film, given the ID number of that
     * film
     * 
     * @param id The movie ID
     * @return The original language of the requested film. If the film cannot be
     *         found, then return null
     */
    @Override
    public String getOriginalLanguage(int id) {
        // TODO Implement this function
        if ((id != -1) && (movieDB.get(id) != null)) {
            return movieDB.get(id).getOriginalLanguage();
        }
        return null;
    }

    /**
     * Gets the runtime of a particular film, given the ID number of that film
     * 
     * @param id The movie ID
     * @return The runtime of the requested film. If the film cannot be found, then
     *         return -1.0d
     */
    @Override
    public double getRuntime(int id) {
        // TODO Implement this function
        if ((id != -1) && (movieDB.get(id) != null)) {
            return movieDB.get(id).getRuntime();
        }
        return -1.0d;
    }

    /**
     * Gets the homepage of a particular film, given the ID number of that film
     * 
     * @param id The movie ID
     * @return The homepage of the requested film. If the film cannot be found, then
     *         return null
     */
    @Override
    public String getHomepage(int id) {
        // TODO Implement this function
        if ((id != -1) && (movieDB.get(id) != null)) {
            return movieDB.get(id).getHomepage();
        }
        return null;
    }

    /**
     * Gets weather a particular film is classed as "adult", given the ID number of
     * that film
     * 
     * @param id The movie ID
     * @return The "adult" status of the requested film. If the film cannot be
     *         found, then return false
     */
    @Override
    public boolean getAdult(int id) {
        // TODO Implement this function
        if ((id != -1) && (movieDB.get(id) != null)) {
            return movieDB.get(id).getAdult();
        }
        return false;
    }

    /**
     * Gets weather a particular film is classed as "direct-to-video", given the ID
     * number of that film
     * 
     * @param id The movie ID
     * @return The "direct-to-video" status of the requested film. If the film
     *         cannot be found, then return false
     */
    @Override
    public boolean getVideo(int id) {
        // TODO Implement this function
        if ((id != -1) && (movieDB.get(id) != null)) {
            return movieDB.get(id).getVideo();
        }
        return false;
    }

    /**
     * Gets the poster URL of a particular film, given the ID number of that film
     * 
     * @param id The movie ID
     * @return The poster URL of the requested film. If the film cannot be found,
     *         then return null
     */
    @Override
    public String getPoster(int id) {
        // TODO Implement this function
        if ((id != -1) && (movieDB.get(id) != null)) {
            return movieDB.get(id).getPoster();
        }
        return null;
    }

    /**
     * Sets the average IMDb score and the number of reviews used to generate this
     * score, for a particular film
     * 
     * @param id          The movie ID
     * @param voteAverage The average score on IMDb for the film
     * @param voteCount   The number of reviews on IMDb that were used to generate
     *                    the average score for the film
     * @return TRUE if the data able to be added, FALSE otherwise
     */
    @Override
    public boolean setVote(int id, double voteAverage, int voteCount) {
        // TODO Implement this function
        if ((id != -1) && (movieDB.get(id) != null) && (voteAverage != -1.0) && (voteCount != -1)) {
            movieDB.get(id).setVoteAverage(voteAverage);
            movieDB.get(id).setVoteCount(voteCount);
            return true;
        }
        return false;
    }

    /**
     * Gets the average score for IMDb reviews of a particular film, given the ID
     * number of that film
     * 
     * @param id The movie ID
     * @return The average score for IMDb reviews of the requested film. If the film
     *         cannot be found, then return -1.0d
     */
    @Override
    public double getVoteAverage(int id) {
        // TODO Implement this function
        if ((id != -1) && (movieDB.get(id) != null)) {
            return movieDB.get(id).getVoteAverage();
        }
        return -1.0d;
    }

    /**
     * Gets the amount of IMDb reviews used to generate the average score of a
     * particular film, given the ID number of that film
     * 
     * @param id The movie ID
     * @return The amount of IMDb reviews used to generate the average score of the
     *         requested film. If the film cannot be found, then return -1
     */
    @Override
    public int getVoteCount(int id) {
        // TODO Implement this function
        if ((id != -1) && (movieDB.get(id) != null)) {
            return movieDB.get(id).getVoteCount();
        }
        return -1;
    }

    /**
     * Adds a given film to a collection. The collection is required to have an ID
     * number, a name, and a URL to a poster for the collection
     * 
     * @param filmID                 The movie ID
     * @param collectionID           The collection ID
     * @param collectionName         The name of the collection
     * @param collectionPosterPath   The URL where the poster can
     *                               be found
     * @param collectionBackdropPath The URL where the backdrop can
     *                               be found
     * @return TRUE if the data able to be added, FALSE otherwise
     * 여기서 Movie 객체에 있는 collectionID도 바꾼다
     */
    @Override
    public boolean addToCollection(int filmID, int collectionID, String collectionName, String collectionPosterPath, String collectionBackdropPath) {
        // TODO Implement this function
        if ((filmID != -1) && (collectionID != -1) && (movieDB.get(filmID) != null)) {
            movieDB.get(filmID).setCollectionID(collectionID);
            if (collectionDB.get(collectionID) == null) {
                CollectionInfo newCollection = new CollectionInfo(collectionID, collectionName, collectionPosterPath, collectionBackdropPath);
                collectionDB.put(collectionID, newCollection);
                collectionDB.get(collectionID).setFilmsID(filmID);
            }
            else {
                collectionDB.get(collectionID).setFilmsID(filmID);
            }
            return true;
        }
        return false;
    }

    /**
     * Get all films that belong to a given collection
     * 
     * @param collectionID The collection ID to be searched for
     * @return An array of film IDs that correspond to the given collection ID. If
     *         there are no films in the collection ID, or if the collection ID is
     *         not valid, return an empty array.
     * 나중에 CollectionInfo에서는 MyArrayList로 되어 있으니 int[]로 바꾸는 작업 해야한다 사진 찍어 놨다.
     */
    @Override
    public int[] getFilmsInCollection(int collectionID) {
        // TODO Implement this function
        if (collectionID != -1 && (collectionDB.get(collectionID) != null)) {
            MyArrayList<Integer> ids = collectionDB.get(collectionID).getFilmsID();
            int[] allIDs = new int[ids.size()];
            for (int i = 0; i < ids.size(); i++) {
                allIDs[i] = ids.get(i);
            }
            return allIDs;
        }
        return new int[0];
    }

    /**
     * Gets the name of a given collection
     * 
     * @param collectionID The collection ID
     * @return The name of the collection. If the collection cannot be found, then
     *         return null
     */
    @Override
    public String getCollectionName(int collectionID) {
        // TODO Implement this function
        if ((collectionID != -1) && (collectionDB.get(collectionID) != null)) {
            return collectionDB.get(collectionID).getCollectionName();
        }
        return null;
    }

    /**
     * Gets the poster URL for a given collection
     * 
     * @param collectionID The collection ID
     * @return The poster URL of the collection. If the collection cannot be found,
     *         then return null
     */
    @Override
    public String getCollectionPoster(int collectionID) {
        // TODO Implement this function
        if ((collectionID != -1) && (collectionDB.get(collectionID) != null)) {
            return collectionDB.get(collectionID).getCollectionPosterPath();
        }

        return null;
    }

    /**
     * Gets the backdrop URL for a given collection
     * 
     * @param collectionID The collection ID
     * @return The backdrop URL of the collection. If the collection cannot be
     *         found, then return null
     */
    @Override
    public String getCollectionBackdrop(int collectionID) {
        // TODO Implement this function
        if ((collectionID != -1) && (collectionDB.get(collectionID) != null)) {
            return collectionDB.get(collectionID).getCollectionBackdropPath();
        }
        return null;
    }

    /**
     * Gets the collection ID of a given film
     * 
     * @param filmID The movie ID
     * @return The collection ID for the requested film. If the film cannot be
     *         found, then return -1
     * Movie object에서 collectionID 꺼내자.
     */
    @Override
    public int getCollectionID(int filmID) {
        // TODO Implement this function
        if ((filmID != -1) && (movieDB.get(filmID) != null)) {
            int collectionID = movieDB.get(filmID).getCollectionID();
            if ((collectionID != -1) && (collectionDB.get(collectionID) != null)) {
                return collectionID;
            }
        }

        return -1;
    }

    /**
     * Sets the IMDb ID for a given film
     * 
     * @param filmID The movie ID
     * @param imdbID The IMDb ID
     * @return TRUE if the data able to be set, FALSE otherwise
     */
    @Override
    public boolean setIMDB(int filmID, String imdbID) {
        // TODO Implement this function
        if ((filmID != -1) && (movieDB.get(filmID) != null)) {
            movieDB.get(filmID).setImdb(imdbID);
            return true;
        }
        return false;
    }

    /**
     * Gets the IMDb ID for a given film
     * 
     * @param filmID The movie ID
     * @return The IMDb ID for the requested film. If the film cannot be found,
     *         return null
     */
    @Override
    public String getIMDB(int filmID) {
        // TODO Implement this function
        if ((filmID != -1) && (movieDB.get(filmID) != null)) {
            return movieDB.get(filmID).getImdb();
        }
        return null;
    }

    /**
     * Sets the popularity of a given film. If the popularity for a film already exists, replace it with the new value
     * 
     * @param id         The movie ID
     * @param popularity The popularity of the film
     * @return TRUE if the data able to be set, FALSE otherwise
     */
    @Override
    public boolean setPopularity(int id, double popularity) {
        // TODO Implement this function
        if ((id != -1) && (movieDB.get(id) != null)) {
            movieDB.get(id).setPopularity(popularity);
            return true;
        }
        return false;
    }

    /**
     * Gets the popularity of a given film
     * 
     * @param id The movie ID
     * @return The popularity value of the requested film. If the film cannot be
     *         found, then return -1.0d. If the popularity has not been set, return 0.0
     */
    @Override
    public double getPopularity(int id) {
        // TODO Implement this function
        if ((id != -1) && (movieDB.get(id) != null)) {
            if (movieDB.get(id).getPopularity() == -1.0d) {
                movieDB.get(id).setPopularity(0.0d);
                return movieDB.get(id).getPopularity();
            }
            return movieDB.get(id).getPopularity();
        }
        return -1.0d;
    }

    /**
     * Adds a production company to a given film
     * 
     * @param id      The movie ID
     * @param company A Company object that represents the details on a production
     *                company
     * @return TRUE if the data able to be added, FALSE otherwise
     */
    @Override
    public boolean addProductionCompany(int id, Company company) {
        // TODO Implement this function
        if ((id != -1) && (movieDB.get(id) != null)) {
            movieDB.get(id).setProductionCompanies(company);
            return true;
        }
        return false;
    }

    /**
     * Adds a production country to a given film
     * 
     * @param id      The movie ID
     * @param country A ISO 3166 string containing the 2-character country code
     * @return TRUE if the data able to be added, FALSE otherwise
     */
    @Override
    public boolean addProductionCountry(int id, String country) {
        // TODO Implement this function
        if ((id != -1) && (movieDB.get(id) != null)) {
            movieDB.get(id).setProductionCountries(country);
            return true;
        }
        return false;
    }

    /**
     * Gets all the production companies for a given film
     * 
     * @param id The movie ID
     * @return An array of Company objects that represent all the production
     *         companies that worked on the requested film. If the film cannot be
     *         found, then return null
     */
    @Override
    public Company[] getProductionCompanies(int id) {
        // TODO Implement this function
        if ((id != -1) && (movieDB.get(id) != null)) {
            MyArrayList<Company> companies = movieDB.get(id).getProductioCompanies();
            if (companies.size() > 0) {
                Company[] allCompanies = new Company[companies.size()];
                for (int i = 0; i < companies.size(); i++) {
                    allCompanies[i] = companies.get(i);
                }
                return allCompanies;
            }
        }

        return null;
    }

    /**
     * Gets all the production companies for a given film
     * 
     * @param id The movie ID
     * @return An array of Strings that represent all the production countries (in
     *         ISO 3166 format) that worked on the requested film. If the film
     *         cannot be found, then return null
     */
    @Override
    public String[] getProductionCountries(int id) {
        // TODO Implement this function
        if ((id != -1) && (movieDB.get(id) != null)) {
            MyArrayList<String> countries = movieDB.get(id).getProductionCountries();
            if (countries.size() > 0) {
                String[] allCountries = new String[countries.size()];
                for (int i = 0; i < countries.size(); i++) {
                    allCountries[i] = countries.get(i);
                }
                return allCountries;
            }
        }
        return null;
    }

    /**
     * States the number of movies stored in the data structure
     * 
     * @return The number of movies stored in the data structure
     */
    @Override
    public int size() {
        // TODO Implement this function
        return movieDB.size();
    }

    /**
     * Produces a list of movie IDs that have the search term in their title,
     * original title or their overview
     * 
     * @param searchTerm The term that needs to be checked
     * @return An array of movie IDs that have the search term in their title,
     *         original title or their overview. If no movies have this search term,
     *         then an empty array should be returned
     */
    @Override
    public int[] findFilms(String searchTerm) {
        // TODO Implement this function
        MyArrayList<Integer> keys = movieDB.keySet();
        MyArrayList<Integer> keysHaveTerm = new MyArrayList<>();
        
        for (int i = 0; i < keys.size(); i++) {
            int id = keys.get(i);  //filmID
            if (movieDB.get(id).getTitle().contains(searchTerm) == true) {
                keysHaveTerm.add(id);
            }
        }

        int[] filmsID = new int[keysHaveTerm.size()];
        for (int i = 0; i < keysHaveTerm.size(); i++) {
            filmsID[i] = keysHaveTerm.get(i);
        }

        return filmsID;
    }
}
