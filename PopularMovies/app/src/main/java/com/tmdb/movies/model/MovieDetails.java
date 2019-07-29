package com.tmdb.movies.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieDetails {

    @SerializedName("genres")
    @Expose
    private List<Genre> mGenres = null;

    @SerializedName("overview")
    @Expose
    private String overview;

    @SerializedName("runtime")
    @Expose
    private int mRuntime;

    @SerializedName("homepage")
    @Expose
    private String homepage;

    public List<Genre> getmGenres() {
        return mGenres;
    }

    public String getOverview() {
        return overview;
    }

    public int getmRuntime() {
        return mRuntime;
    }

    public String getHomepage() {
        return homepage;
    }
}
