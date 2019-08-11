package com.tmdb.movies.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.tmdb.movies.R;
import com.tmdb.movies.ui.fragments.MovieDetailsFragment;
import com.tmdb.movies.ui.fragments.MovieListFragment;

import timber.log.Timber;

public class MoviesLauncherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Timber.d("onCreate");
        setContentView(R.layout.activity_movies);
        if (savedInstanceState == null) {
            setTheme(R.style.MainTheme);
            MovieListFragment fragment = new MovieListFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, fragment,
                            MovieListFragment.class.toString()).commit();
            getSupportFragmentManager().executePendingTransactions();
        }
    }

    public void show(Bundle bundle) {
        MovieDetailsFragment movieDetailsFragment = new MovieDetailsFragment();
        movieDetailsFragment.setArguments(bundle);
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(MovieDetailsFragment.class.toString())
                .replace(R.id.fragment_container,
                        movieDetailsFragment, MovieDetailsFragment.class.toString()).commit();
    }
}
