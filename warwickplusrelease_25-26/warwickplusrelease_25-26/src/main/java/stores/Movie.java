package stores;

import java.time.LocalDate;

import structures.MyArrayList;

public class Movie{
    private int id = -1;
    //private String name = "";
    private String title = "";
    private String originalTitle = "";
    private String overview = "";
    private String tagline = "";
    private String status = "";
    private Genre[] genres = new Genre[0];
    private LocalDate release = null;
    private long budget = -1;
    private long revenue = -1;
    private String[] languages = new String[0];
    private String originalLanguage = "";
    private double runtime = -1.0;
    private String homepage = "";
    private boolean adult = false;
    private boolean video = false;
    private String poster = "";
    private double voteAverage = -1.0;
    private int voteCount = -1;
    private int collectionID = -1;
    private String imdb = "";
    private double popularity = -1.0;
    private final MyArrayList<Company> productionCompanies = new MyArrayList<>();
    private final MyArrayList<String> productionCountries = new MyArrayList<>();

    public Movie(int id, String title, String originalTitle, String overview,
                 String tagline, String status, Genre[] genres, LocalDate release, long budget,
                 long revenue, String[] languages, String originalLanguage, double runtime,
                 String homepage, boolean adult, boolean video, String poster
    ) {
        this.id = id;   
        this.title = title;
        this.originalTitle = originalTitle;
        this.overview = overview;
        this.tagline = tagline;
        this.status = status;
        this.genres = genres;
        this.release = release;
        this.budget = budget;
        this.revenue = revenue;
        this.languages = languages;
        this.originalLanguage = originalLanguage;
        this.runtime = runtime;
        this.homepage = homepage;
        this.adult = adult;
        this.video = video;
        this.poster = poster;
    }

    public int getID() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public String getTagline() {
        return tagline;
    }

    public String getStatus() {
        return status;
    }

    public Genre[] getGenres() {
        return genres;
    }

    public LocalDate getRelease() {
        return release;
    }

    public long getBudget() {
        return budget;
    }

    public long getRevenue() {
        return revenue;
    }

    public String[] getLanguages() {
        return languages;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public double getRuntime() {
        return runtime;
    }

    public String getHomepage() {
        return homepage;
    }

    public boolean getAdult() {
        return adult;
    }

    public boolean getVideo() {
        return video;
    }

    public String getPoster() {
        return poster;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public int getCollectionID() {
        return collectionID;
    }

    public String getImdb() {
        return imdb;
    }

    public double getPopularity() {
        return popularity;
    }

    public MyArrayList<Company> getProductioCompanies() {
        return productionCompanies;
    }

    public MyArrayList<String> getProductionCountries() {
        return productionCountries;
    }


    //이제 voteAverage 부터 setters 만들면 된다.  
    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public void setCollectionID(int collectionID) {
        this.collectionID = collectionID;
    }

    public void setImdb(String imdb) {
        this.imdb = imdb;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public void setProductionCompanies(Company company) {
        productionCompanies.add(company);
    }

    public void setProductionCountries(String productionCountry) {
        productionCountries.add(productionCountry);
    }
}