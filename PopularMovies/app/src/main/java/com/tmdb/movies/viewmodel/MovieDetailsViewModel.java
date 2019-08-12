package com.tmdb.movies.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.tmdb.movies.model.MovieDetails;
import com.tmdb.movies.repository.MovieApiRepository;

import timber.log.Timber;

public class MovieDetailsViewModel extends AndroidViewModel {

    private final LiveData<MovieDetails> mMovieDetails;

    public MovieDetailsViewModel(@NonNull Application application, float movieId) {
        super(application);
        MovieApiRepository movieApiRepository = MovieApiRepository
                .getInstance(application.getApplicationContext());
        mMovieDetails = movieApiRepository.getMovieDetails(movieId);
    }

    public LiveData<MovieDetails> getMovieDetails() {
        Timber.d("MovieDetailsViewModel getMovieDetails");
        return mMovieDetails;
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        private final float mMovieId;
        private Application mApplication;

        public Factory(Application application, float movieId) {
            mApplication = application;
            mMovieId = movieId;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new MovieDetailsViewModel(mApplication, mMovieId);
        }
    }

}
