package com.tmdb.movies.ui.fragments;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tmdb.movies.R;
import com.tmdb.movies.databinding.FragMovieListBinding;
import com.tmdb.movies.model.Movie;
import com.tmdb.movies.ui.adapters.MovieListAdapter;
import com.tmdb.movies.viewmodel.MovieListViewModel;

import timber.log.Timber;

public class MovieListFragment extends Fragment {
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
        mMovieListAdapter = new MovieListAdapter();
        mMovieListFragBinding.movieList.setAdapter(mMovieListAdapter);
        mMovieListFragBinding.setIsLoading(true);
        return mMovieListFragBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mMovieListViewModel = ViewModelProviders.of(this)
                .get(MovieListViewModel.class);
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
}
