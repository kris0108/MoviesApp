package com.tmdb.movies.datasource;

import android.arch.paging.DataSource;

import com.tmdb.movies.model.Movie;

public class MovieDataSourceFactory extends DataSource.Factory<Integer, Movie> {

    private MovieDataSource mMovieDataSource;

    @Override
    public DataSource<Integer, Movie> create() {
        mMovieDataSource = new MovieDataSource();
        return mMovieDataSource;
    }
}
