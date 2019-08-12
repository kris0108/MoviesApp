package com.tmdb.movies.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;

import com.tmdb.movies.model.Genre;
import com.tmdb.movies.model.GenreResponse;
import com.tmdb.movies.model.Movie;
import com.tmdb.movies.model.MovieDetails;
import com.tmdb.movies.model.MovieResponse;
import com.tmdb.movies.utils.Constants;
import com.tmdb.movies.utils.MovieUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
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

    private final long mCacheValue = 5 * 1024 * 1024;
    public static final String HEADER_CACHE_CONTROL = "Cache-Control";

    private MovieApiRepository(final Context context) {

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
                .cache(getCache(context))
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        if(!MovieUtils.isConnected(context)) {
                            request = request.newBuilder().header(HEADER_CACHE_CONTROL,
                                    "public, only-if-cached, max-stale="
                                            + 60 * 60 * 24).build();
                            okhttp3.Response response = chain.proceed(request);
                            return response;
                        }
                        return chain.proceed(request);
                    }
                })
                .build();

        mRetrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mMovieApi = mRetrofit.create(MovieAPI.class);
    }

    private Cache getCache(Context context) {
        Cache cache = null;
        cache = new Cache(new File(context.getCacheDir(), "http-cache"),
                mCacheValue);
        return cache;
    }

    public static MovieApiRepository getInstance(Context context) {
        if (null == mMovieApiRepository) {
            mMovieApiRepository = new MovieApiRepository(context);
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

    public LiveData<MovieDetails> getMovieDetails(float movieId) {

        Timber.d("getMovieDetails:"+movieId);
        final MutableLiveData<MovieDetails> movieDetails = new MutableLiveData<>();
        mMovieApi.getDetails(movieId, Constants.API_KEY, Constants.LANGUAGE)
                .enqueue(new Callback<MovieDetails>() {
                    @Override
                    public void onResponse(Call<MovieDetails> call, Response<MovieDetails> response) {
                        if(null != response.body()) {
                            Timber.i(response.body().toString());
                            movieDetails.setValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<MovieDetails> call, Throwable t) {
                        Timber.e(t.getMessage());
                    }
                });
        return movieDetails;
    }

    public LiveData<List<Movie>> getMoviesByTitle(String title) {
        final MutableLiveData<List<Movie>> movies = new MutableLiveData<>();

        mMovieApi.searchMoviesByTitle(Constants.API_KEY, Constants.LANGUAGE, title)
                .enqueue(new Callback<MovieResponse>() {
                    @Override
                    public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                        if(null != response.body()) {
                            Timber.i(response.body().toString());
                            movies.setValue(response.body().getResults());
                        }
                    }

                    @Override
                    public void onFailure(Call<MovieResponse> call, Throwable t) {
                        Timber.e(t.getMessage());
                    }
                });
        return movies;
    }

}
