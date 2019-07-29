package com.tmdb.movies.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.tmdb.movies.model.Genre;
import com.tmdb.movies.model.GenreResponse;
import com.tmdb.movies.model.MovieResponse;
import com.tmdb.movies.utils.Constants;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

public class MovieApiRepository {

    private MovieAPI mMovieApi;
    private static MovieApiRepository mMovieApiRepository;
    private Retrofit mRetrofit;

    private MovieApiRepository() {

        Timber.plant(new Timber.DebugTree());
        HttpLoggingInterceptor httpLoggingInterceptor = new
                HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Timber.i(message);
            }
        });

        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient()
                .newBuilder()
                .addInterceptor(httpLoggingInterceptor)
                .build();

        mRetrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mMovieApi = mRetrofit.create(MovieAPI.class);
    }

    public static MovieApiRepository getInstance() {
        if (null == mMovieApiRepository) {
            mMovieApiRepository = new MovieApiRepository();
        }
        return mMovieApiRepository;
    }

    public Call<MovieResponse> getMovies(String criteria, String api_key,
                                         String language, int page) {
        Timber.d("getMovies");
        return mMovieApi.getMovies(criteria, api_key, language, page);
    }

    public LiveData<List<Genre>> getGenresList() {
        final MutableLiveData<List<Genre>> genres = new MutableLiveData<>();

        mMovieApi.getGenres(Constants.API_KEY, Constants.LANGUAGE)
                .enqueue(new Callback<GenreResponse>() {
                    @Override
                    public void onResponse(Call<GenreResponse> call, Response<GenreResponse> response) {
                        if(null != response) {
                            Timber.i(response.body().toString());
                            genres.setValue(response.body().getGenres());
                        }
                    }

                    @Override
                    public void onFailure(Call<GenreResponse> call, Throwable t) {
                        Timber.e(t.getMessage());
                    }
                });
        return genres;
    }
}
