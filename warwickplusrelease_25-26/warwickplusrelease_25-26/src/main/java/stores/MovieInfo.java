package stores;


// class for storing each movie data (filmID, casts, and crews who is related)
public class MovieInfo{
    private int filmID;
    private CastCredit[] casts;
    private CrewCredit[] crews;


    public MovieInfo (int filmID, CastCredit[] casts, CrewCredit[] crews) {
        this.filmID = filmID;
        this.casts = casts;
        this.crews = crews;
    }


    // getters for movie data

    public CastCredit[] getCasts() {
        return casts;
    }

    public CrewCredit[] getCrews() {
        return crews;
    }

    public int getSizeOfCast() {
        return casts.length;
    }

    public int getSizeOfCrew() {
        return crews.length;
    }

}

