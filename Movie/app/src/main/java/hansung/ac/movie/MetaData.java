package hansung.ac.movie;

import java.util.ArrayList;

public class MetaData {
    private int id;
    private String overview;
    private int runtime;
    private String tagline;
    private String title;
    private double vote_average;
    private int vote_count;
    private String genres;
    private float rating;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getVote_average() {
        return vote_average;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }

    public int getVote_count() {
        return vote_count;
    }

    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }
    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "MetaData{" +
                "id=" + id +
                ", overview='" + overview + '\'' +
                ", runtime=" + runtime +
                ", tagline='" + tagline + '\'' +
                ", title='" + title + '\'' +
                ", vote_average=" + vote_average +
                ", vote_count=" + vote_count +
                ", genres='" + genres + '\'' +
                '}';
    }
}
