package com.tmdb.movies.datasource;

import android.arch.paging.DataSource;
import android.content.Context;

import com.tmdb.movies.model.Movie;

public class MovieDataSourceFactory extends DataSource.Factory<Integer, Movie> {

    private MovieDataSource mMovieDataSource;
    private Context mContext;

    public MovieDataSourceFactory(Context context) {
        mContext = context;
    }

    @Override
    public DataSource<Integer, Movie> create() {
        mMovieDataSource = new MovieDataSource(mContext);
        return mMovieDataSource;
    }
}
