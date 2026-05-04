package stores;

import structures.MyArrayList;


// class (instance) for storing collection movie data.
public class CollectionInfo {
    private int collectionID = -1;
    private String collectionName = "";
    private String collectionPosterPath = "";
    private String collectionBackdropPath = "";
    private final MyArrayList<Integer> filmsID;

    public CollectionInfo(int collectionID, String collectionName, String collectionPosterPath, String collectionBackdropPath) {
        this.collectionID = collectionID;
        this.collectionName = collectionName;
        this.collectionPosterPath = collectionPosterPath;
        this.collectionBackdropPath = collectionBackdropPath;
        filmsID = new MyArrayList<>();
    }


    // getters for collection movie data
    
    public int getCollectionID() {
        return collectionID;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public String getCollectionPosterPath() {
        return collectionPosterPath;
    }

    public String getCollectionBackdropPath() {
        return collectionBackdropPath;
    }

    public MyArrayList<Integer> getFilmsID() {
        return filmsID;
    }


    // setters for collection movie data

    public void setFilmsID(int filmID) {
        filmsID.add(filmID);
    }

    public void removeFilmID(int filmID) {
        filmsID.remove(filmID);
    }
}