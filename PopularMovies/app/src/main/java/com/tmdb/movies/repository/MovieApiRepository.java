package com.tmdb.movies.repository;

import com.tmdb.movies.model.MovieResponse;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

public class MovieApiRepository {

    private MovieAPI mMovieApi;
    private static MovieApiRepository mMovieApiRepository;
    private Retrofit mRetrofit;

    private MovieApiRepository() {
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
}
