package com.tmdb.movies.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieDetails {

    @SerializedName("genres")
    @Expose
    private List<Genre> genres = null;

    @SerializedName("overview")
    @Expose
    private String overview;

    @SerializedName("runtime")
    @Expose
    private int runtime;

    @SerializedName("homepage")
    @Expose
    private String homepage;

    public List<Genre> getGenres() {
        return genres;
    }

    public String getOverview() {
        return overview;
    }

    public int getRuntime() {
        return runtime;
    }

    public String getHomepage() {
        return homepage;
    }
}
