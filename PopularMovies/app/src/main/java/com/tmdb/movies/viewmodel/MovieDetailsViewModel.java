package com.tmdb.movies.viewmodel;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.tmdb.movies.model.Movie;
import com.tmdb.movies.model.MovieDetails;
import com.tmdb.movies.repository.MovieApiRepository;

public class MovieDetailsViewModel extends ViewModel {

    private final LiveData<MovieDetails> mMovieDetails;

    public MovieDetailsViewModel (float movieId) {
        MovieApiRepository movieApiRepository = MovieApiRepository.getInstance();
        mMovieDetails = movieApiRepository.getMovieDetails(movieId);
    }

    public LiveData<MovieDetails> getMovieDetails() {
        return mMovieDetails;
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        private final float mMovieId;

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
