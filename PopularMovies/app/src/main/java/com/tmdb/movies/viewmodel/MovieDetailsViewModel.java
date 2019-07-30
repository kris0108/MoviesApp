package com.tmdb.movies.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.tmdb.movies.model.MovieDetails;
import com.tmdb.movies.repository.MovieApiRepository;

import timber.log.Timber;

public class MovieDetailsViewModel extends ViewModel {

    private final LiveData<MovieDetails> mMovieDetails;

    public MovieDetailsViewModel(float movieId) {
        MovieApiRepository movieApiRepository = MovieApiRepository.getInstance();
        mMovieDetails = movieApiRepository.getMovieDetails(movieId);
    }

    public LiveData<MovieDetails> getMovieDetails() {
        Timber.d("MovieDetailsViewModel getMovieDetails");
        return mMovieDetails;
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        private final float mMovieId;
        //  Application application;

        public Factory(float movieId) {
            mMovieId = movieId;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new MovieDetailsViewModel(mMovieId);
        }
    }

}
