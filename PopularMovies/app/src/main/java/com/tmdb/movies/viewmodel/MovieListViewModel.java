package com.tmdb.movies.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;

import com.tmdb.movies.datasource.MovieDataSourceFactory;
import com.tmdb.movies.model.Genre;
import com.tmdb.movies.model.Movie;
import com.tmdb.movies.repository.MovieApiRepository;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import timber.log.Timber;

public class MovieListViewModel extends ViewModel {

    private MovieApiRepository mMovieApiRepository;
    private LiveData<List<Genre>> mGenreList;
    private LiveData<PagedList<Movie>> mMoviePagedList;

    public MovieListViewModel() {
        mMovieApiRepository = MovieApiRepository.getInstance();
        initGeners();
        initDataSource();
    }

    public void initGeners() {
        mGenreList = mMovieApiRepository.getGenresList();
    }

    /**
     * Initialize the paged list
     */
    private void initDataSource() {
        Timber.d("initDataSource");
        Executor executor = Executors.newFixedThreadPool(5);
        MovieDataSourceFactory movieDataFactory = new MovieDataSourceFactory();
        PagedList.Config config = (new PagedList.Config.Builder())
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(10)
                .setPageSize(20)
                .setPrefetchDistance(50)
                .build();

        mMoviePagedList = new LivePagedListBuilder<>(movieDataFactory, config)
                .setFetchExecutor(executor)
                .build();
    }

    public LiveData<PagedList<Movie>> getMoviePagedList() {
        return mMoviePagedList;
    }

    public LiveData<List<Genre>> getGenreList() {
        return mGenreList;
    }

    public LiveData<List<Movie>> getMovieByTitle(String title) {
        return mMovieApiRepository.getMoviesByTitle(title);
    }
}
