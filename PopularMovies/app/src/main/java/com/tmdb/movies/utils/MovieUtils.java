package com.tmdb.movies.utils;

import java.time.LocalDate;

public class MovieUtils {

    private MovieUtils() {}

    public static String getTileYear(String title, String rleaseDate) {
        LocalDate localDate = LocalDate.parse(rleaseDate);
        StringBuilder sb = new StringBuilder(title);
        sb.append(" (").append(localDate.getYear()).append(")");
        return sb.toString();
    }
}
