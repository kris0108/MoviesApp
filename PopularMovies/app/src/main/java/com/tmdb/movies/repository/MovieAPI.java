package com.tmdb.movies.repository;

import com.tmdb.movies.model.GenreResponse;
import com.tmdb.movies.model.MovieDetails;
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

    @GET("/3/genre/movie/list")
    Call<GenreResponse> getGenres(
            @Query("api_key") String apiKey,
            @Query("language") String language
    );

    @GET("/3/movie/{id}")
    Call<MovieDetails> getDetails(
            @Path("id") float id,
            @Query("api_key") String apiKey,
            @Query("language") String language
    );

    @GET("/3/search/movie")
    Call<MovieResponse> searchMoviesByTitle(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("query") String movieTitle);

}
