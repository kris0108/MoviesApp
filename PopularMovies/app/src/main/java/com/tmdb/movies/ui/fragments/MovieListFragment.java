package com.tmdb.movies.ui.fragments;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tmdb.movies.R;
import com.tmdb.movies.databinding.FragMovieListBinding;
import com.tmdb.movies.model.Genre;
import com.tmdb.movies.model.Movie;
import com.tmdb.movies.ui.MoviesLauncherActivity;
import com.tmdb.movies.ui.adapters.MovieListAdapter;
import com.tmdb.movies.utils.Constants;
import com.tmdb.movies.utils.GenresMapper;
import com.tmdb.movies.viewmodel.MovieListViewModel;

import java.util.List;

import timber.log.Timber;

public class MovieListFragment extends Fragment
        implements MovieListAdapter.MovieListAdapterOnClickHandler {

    MovieListAdapter mMovieListAdapter;
    FragMovieListBinding mMovieListFragBinding;
    MovieListViewModel mMovieListViewModel;
    RecyclerView mRecyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mMovieListFragBinding = DataBindingUtil.inflate(inflater,
                R.layout.frag_movie_list, container, false);
        mRecyclerView = mMovieListFragBinding.movieList;
        mMovieListAdapter = new MovieListAdapter(this);
        mMovieListFragBinding.movieList.setAdapter(mMovieListAdapter);
        mMovieListFragBinding.setIsLoading(true);
        return mMovieListFragBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mMovieListViewModel = ViewModelProviders.of(this)
                .get(MovieListViewModel.class);
        observeForGenres();
        observeForMovies();
        setSwipeRefreshLayout();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void observeForMovies() {
        Timber.d("observeForMovies");
        mMovieListViewModel.getMoviePagedList()
                .observe(this, new Observer<PagedList<Movie>>() {
                    @Override
                    public void onChanged(@Nullable PagedList<Movie> pagedList) {
                        if (pagedList != null) {
                            Timber.d("pagedList size:" + pagedList.size());
                            mMovieListFragBinding.setIsLoading(false);
                            mMovieListAdapter.submitList(pagedList);
                        } else {
                            Timber.d("Error while loading movies");
                        }
                    }
                });
    }

    private void observeForGenres() {
        Timber.d("observeForGenres");
        mMovieListViewModel.getGenreList().observe(this, new Observer<List<Genre>>() {
            @Override
            public void onChanged(@Nullable List<Genre> genres) {
                if (null != genres) {
                    Timber.d("genres size:" + genres.size());
                    SparseArray<String> genresMap = GenresMapper.toSparseArray(genres);
                    mMovieListAdapter.setmGenreMap(genresMap);
                } else {
                    Timber.e("Error in retrieving genres");
                }
            }
        });
    }

    private void setSwipeRefreshLayout() {
        mMovieListFragBinding.swipeRefresh.setColorSchemeColors(getResources().getColor(R.color.colorAccent));
        mMovieListFragBinding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                updateUI();
                hideRefresh();
            }
        });
    }

    private void updateUI() {
        Timber.d("updateUI");
        mRecyclerView.setAdapter(mMovieListAdapter);
        observeForMovies();
    }

    private void hideRefresh() {
        Timber.d("hideRefresh");
        mMovieListFragBinding.swipeRefresh.setRefreshing(false);
    }

    @Override
    public void onItemClick(Movie movie) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.EXTRA_MOVIE, movie);
        if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
            ((MoviesLauncherActivity) getActivity()).show(bundle);
        }
    }
}
