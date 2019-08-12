package com.tmdb.movies.datasource;

import android.arch.paging.PageKeyedDataSource;
import android.content.Context;
import android.support.annotation.NonNull;

import com.tmdb.movies.model.Movie;
import com.tmdb.movies.model.MovieResponse;
import com.tmdb.movies.repository.MovieAPI;
import com.tmdb.movies.repository.MovieApiRepository;
import com.tmdb.movies.utils.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class MovieDataSource extends PageKeyedDataSource<Integer, Movie> {

    private MovieApiRepository mMovieApiRepository;
    private MovieAPI mMovieApi;

    public MovieDataSource(Context context) {
        mMovieApiRepository = MovieApiRepository.getInstance(context);
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params,
                            @NonNull final LoadInitialCallback<Integer, Movie> callback) {

        Timber.d("loadInitial");
        mMovieApiRepository.getMovies(Constants.QUERY_CRITERIA, Constants.API_KEY,
                Constants.LANGUAGE, Constants.INIT_PAGE)
                .enqueue(new Callback<MovieResponse>() {
                    @Override
                    public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                        if (response.isSuccessful()) {
                            callback.onResult(response.body().getResults(),
                                    Constants.PREV_PAGE_KEY, Constants.NEXT_PAGE_KEY);

                        } else {
                            Timber.e("Response Code: " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<MovieResponse> call, Throwable t) {
                        Timber.e("Failed initializing a PageList: " + t.getMessage());
                    }
                });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params,
                           @NonNull LoadCallback<Integer, Movie> callback) {

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params,
                          @NonNull final LoadCallback<Integer, Movie> callback) {

        Timber.d("loadAfter");
        final int currentPage = params.key;
        mMovieApiRepository.getMovies(Constants.QUERY_CRITERIA, Constants.API_KEY,
                Constants.LANGUAGE, currentPage)
                .enqueue(new Callback<MovieResponse>() {
                    @Override
                    public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                        if (response.isSuccessful()) {
                            int nextKey = currentPage + 1;
                            callback.onResult(response.body().getResults(), nextKey);
                        }
                    }

                    @Override
                    public void onFailure(Call<MovieResponse> call, Throwable t) {
                        Timber.e("Failed appending page: " + t.getMessage());
                    }
                });
    }
}
