package stores;

import structures.MyArrayList;


// class for storing individual crew data (movies that is related ,Person -> id, name, and profile path)
public class CrewInfo{
    private Person person;
    MyArrayList<Integer> movies = new MyArrayList<>();


    public CrewInfo(Person person, int filmID) {
        this.person = person;
        movies.add(filmID);
    }


    // getters for crew data

    public Person getPerson() {
        return person;
    }

    public MyArrayList<Integer> getMovies() {
        return movies;
    }


    // setters for crew data

    public void addMovie(int filmID) {
        if (movies.contains(filmID) == false) {
            movies.add(filmID);
        }
    }

    public void removeMovie(int filmID) {
        movies.remove(filmID);
    }
}