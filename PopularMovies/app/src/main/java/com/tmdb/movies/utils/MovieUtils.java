package com.tmdb.movies.utils;

import java.time.LocalDate;

import timber.log.Timber;

public class MovieUtils {

    private MovieUtils() {
    }

    public static String getTileYear(String title, String rleaseDate) {
        LocalDate localDate = LocalDate.parse(rleaseDate);
        StringBuilder sb = new StringBuilder(title);
        sb.append(" (").append(localDate.getYear()).append(")");
        return sb.toString();
    }

    public static String calculateRunTime(int runTime) {
        Timber.d("calculateRunTime:" + runTime);
        StringBuilder sb = new StringBuilder();
        int hours = runTime / 60;
        int minutes = runTime % 60;
        sb.append(hours).append("h").append(" ")
                .append(minutes).append("m").append(" ");

        return sb.toString();
    }
}
