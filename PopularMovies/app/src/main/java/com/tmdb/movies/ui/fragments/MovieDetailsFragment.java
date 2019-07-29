package com.tmdb.movies.ui.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;
import com.tmdb.movies.R;
import com.tmdb.movies.databinding.FragMovieDetailsBinding;
import com.tmdb.movies.model.Movie;
import com.tmdb.movies.utils.Constants;
import com.tmdb.movies.viewmodel.MovieDetailsViewModel;

public class MovieDetailsFragment extends Fragment {

    FragMovieDetailsBinding mFragMovieDetailsBinding;
    Movie mMovie;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        getContext().getTheme().applyStyle(R.style.DetailTheme, true);
        mFragMovieDetailsBinding = DataBindingUtil.inflate(inflater,
                R.layout.frag_movie_details, container, false);
        return mFragMovieDetailsBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = getArguments();

       // setUpActionBar();

        mMovie = (Movie) bundle.getParcelable(Constants.EXTRA_MOVIE);

        MovieDetailsViewModel.Factory factory = new MovieDetailsViewModel.Factory(mMovie.getId());

        MovieDetailsViewModel movieDetailsViewModel = ViewModelProviders.of(this, factory)
                .get(MovieDetailsViewModel.class);

        loadMoviePoster();
    }

   /* private void setUpActionBar() {

        ActionBar actionBar = getActivity().getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
    }
*/
    private void loadMoviePoster() {
        String posterPath = mMovie.getPoster_path();
        String posterUrl = Constants.IMAGE_BASE_URL + Constants.POSTER_FILE_SIZE + posterPath;
        Picasso.with(getContext())
                .load(posterUrl)
                .error(R.drawable.image)
                .into(mFragMovieDetailsBinding.moviePoster);
    }
}
