package com.tmdb.movies.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.tmdb.movies.R;
import com.tmdb.movies.ui.fragments.MovieListFragment;

public class MoviesLauncherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);
        if (savedInstanceState == null) {
            MovieListFragment fragment = new MovieListFragment();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, fragment,
                            MovieListFragment.class.toString()).commit();
            getSupportFragmentManager().executePendingTransactions();
        }
    }
}
