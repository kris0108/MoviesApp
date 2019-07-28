package com.tmdb.movies.repository;

import com.tmdb.movies.model.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieAPI {

    @GET("/3/movie/{sort_criteria}")
    Call<MovieResponse> getMovies(
            @Path("sort_criteria") String sortCriteria,
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("page") int page
    );
}
