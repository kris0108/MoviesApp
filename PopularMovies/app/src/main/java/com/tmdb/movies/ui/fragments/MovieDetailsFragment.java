package com.tmdb.movies.ui.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;
import com.tmdb.movies.R;
import com.tmdb.movies.databinding.FragMovieDetailsBinding;
import com.tmdb.movies.model.Movie;
import com.tmdb.movies.model.MovieDetails;
import com.tmdb.movies.model.MovieEntry;
import com.tmdb.movies.utils.Constants;
import com.tmdb.movies.utils.GenresMapper;
import com.tmdb.movies.utils.MovieUtils;
import com.tmdb.movies.viewmodel.MovieDetailsViewModel;

import timber.log.Timber;

public class MovieDetailsFragment extends Fragment {

    FragMovieDetailsBinding mFragMovieDetailsBinding;
    private Movie mMovie;
    private MovieEntry mMovieEntry;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        mFragMovieDetailsBinding = DataBindingUtil.inflate(inflater,
                R.layout.frag_movie_details, container, false);
        mFragMovieDetailsBinding.setIsLoading(true);
        return mFragMovieDetailsBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle bundle = getArguments();
        mMovie = (Movie) bundle.getParcelable(Constants.EXTRA_MOVIE);
        mMovieEntry = new MovieEntry();
        loadMoviePoster();

        MovieDetailsViewModel.Factory factory = new MovieDetailsViewModel.Factory(mMovie.getId());
        MovieDetailsViewModel movieDetailsViewModel = ViewModelProviders.of(this, factory)
                .get(MovieDetailsViewModel.class);

        observeMovieDetails(movieDetailsViewModel);
    }

    private void loadMoviePoster() {
        String posterPath = mMovie.getPoster_path();
        String posterUrl = Constants.IMAGE_BASE_URL + Constants.POSTER_FILE_SIZE + posterPath;
        Picasso.with(getContext())
                .load(posterUrl)
                .error(R.drawable.image)
                .into(mFragMovieDetailsBinding.moviePoster);
    }

    private void observeMovieDetails(final MovieDetailsViewModel movieDetailsViewModel) {

        movieDetailsViewModel.getMovieDetails().observe(this, new Observer<MovieDetails>() {
            @Override
            public void onChanged(@Nullable MovieDetails movieDetails) {
                if (null != movieDetails) {
                    Timber.d("observeMovieDetails" + movieDetails.getRuntime());
                    mFragMovieDetailsBinding.setIsLoading(false);
                    loadUI(movieDetails);
                } else {
                    Timber.e("Error while accessing movie details");
                }
            }
        });
    }

    private void loadUI(MovieDetails movieDetails) {
        mMovieEntry.setTitle(mMovie.getTitle());
        mMovieEntry.setRunTime(MovieUtils.calculateRunTime(movieDetails.getRuntime()));
        mMovieEntry.setGenres(GenresMapper.getGenreText(movieDetails.getGenres(), 3));
        mMovieEntry.setReleaseDate(mMovie.getRelease_date());
        mMovieEntry.setOverview(movieDetails.getOverview());
        mMovieEntry.setHomePage(movieDetails.getHomepage());
        mFragMovieDetailsBinding.setMovieEntry(mMovieEntry);
    }
}
