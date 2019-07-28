package com.tmdb.movies.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Movie {

    @SerializedName("vote_count")
    @Expose
    private float vote_count;
    @SerializedName("id")
    @Expose
    private float id;
    @SerializedName("video")
    @Expose
    private boolean video;
    @SerializedName("vote_average")
    @Expose
    private float vote_average;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("popularity")
    @Expose
    private float popularity;
    @SerializedName("poster_path")
    @Expose
    private String poster_path;
    @SerializedName("original_language")
    @Expose
    private String original_language;
    @SerializedName("original_title")
    @Expose
    private String original_title;
    @SerializedName("genre_ids")
    @Expose
    List<Integer> genre_ids;
    @SerializedName("backdrop_path")
    @Expose
    private String backdrop_path;
    @SerializedName("adult")
    @Expose
    private boolean adult;
    @SerializedName("overview")
    @Expose
    private String overview;
    @SerializedName("release_date")
    @Expose
    private String release_date;

    public float getVote_count() {
        return vote_count;
    }

    public float getId() {
        return id;
    }

    public boolean getVideo() {
        return video;
    }

    public float getVote_average() {
        return vote_average;
    }

    public String getTitle() {
        return title;
    }

    public float getPopularity() {
        return popularity;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public boolean getAdult() {
        return adult;
    }

    public String getOverview() {
        return overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public List<Integer> getGenre_ids() {
        return genre_ids;
    }

}
