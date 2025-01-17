package com.tmdb.movies.utils;

import com.tmdb.movies.BuildConfig;

public final class Constants {

    private Constants() {
    }

    public static final String TMDB_BASE_URL = "https://api.themoviedb.org/3/";
    public static final String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/";
    public static final String IMAGE_FILE_SIZE = "w185";
    public static final String POSTER_FILE_SIZE = "w500";
    public static final String API_KEY = BuildConfig.API_KEY;
    public static final String LANGUAGE = "en-US";
    public static final String QUERY_CRITERIA = "popular";
    public static final String EXTRA_MOVIE = "movie";
    public static final int INIT_PAGE = 1;
    public static final int PREV_PAGE_KEY = 1;
    public static final int NEXT_PAGE_KEY = 2;
}
