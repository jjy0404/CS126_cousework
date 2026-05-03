package stores;

import structures.MyArrayList;


public class CastInfo{
    private Person person;
    private MyArrayList<Integer> movies = new MyArrayList<>(); // integer = filmID
    private MyArrayList<Integer> castStarMovies = new MyArrayList<>();
    private int creditCount = 0;


    public CastInfo (Person person, int filmID) {
        this.person = person;
        movies.add(filmID);
        creditCount++;
    }

    public Person getPerson() {
        return person;
    }

    public MyArrayList<Integer> getMovies() {
        return movies;
    }
    
    public MyArrayList<Integer> getCastStarMovies() {
        return castStarMovies;
    }

    public int getCreditCount() {
        return creditCount;
    }

    public void addMovie(int filmID) {
        if (movies.contains(filmID) == false) {
            movies.add(filmID);
        }
        creditCount++;
    }

    public void addStarMovie(int filmID) {
        if (castStarMovies.contains(filmID) == false) {
            castStarMovies.add(filmID);
        }
    }

    public void removeMovie(int filmID) {
        if (movies.contains(filmID)) {
            movies.remove(filmID);
        }
        creditCount--;
    }

    public void removeStarMovie(int filmID) {
        castStarMovies.remove(filmID);
    }
}