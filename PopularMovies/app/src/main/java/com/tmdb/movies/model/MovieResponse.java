package com.tmdb.movies.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieResponse {

    @SerializedName("page")
    @Expose
    private float page;
    @SerializedName("total_results")
    @Expose
    private float total_results;
    @SerializedName("total_pages")
    @Expose
    private float total_pages;
    @SerializedName("results")
    @Expose
    List< Movie > results;

    public float getPage() {
        return page;
    }

    public float getTotal_results() {
        return total_results;
    }

    public float getTotal_pages() {
        return total_pages;
    }

    public List<Movie> getResults() {
        return results;
    }
}
