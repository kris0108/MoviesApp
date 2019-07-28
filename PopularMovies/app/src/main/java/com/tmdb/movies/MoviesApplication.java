package com.tmdb.movies;

import android.app.Application;

import timber.log.Timber;

public class MoviesApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initializeLogger();
    }

    private void initializeLogger(){
        Timber.plant(new Timber.DebugTree());
    }

}
