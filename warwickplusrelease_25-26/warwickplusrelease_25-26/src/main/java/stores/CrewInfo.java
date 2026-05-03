package stores;

import structures.MyArrayList;

public class CrewInfo{
    private Person person;
    MyArrayList<Integer> movies = new MyArrayList<>();


    public CrewInfo(Person person, int filmID) {
        this.person = person;
        movies.add(filmID);
    }

    public Person getPerson() {
        return person;
    }

    public MyArrayList<Integer> getMovies() {
        return movies;
    }

    public void addMovie(int filmID) {
        if (movies.contains(filmID) == false) {
            movies.add(filmID);
        }
    }

    public void removeMovie(int filmID) {
        movies.remove(filmID);
    }
}