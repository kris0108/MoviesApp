package com.tmdb.movies.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import timber.log.Timber;

public class MovieUtils {

    private MovieUtils() {
    }

    public static String getTileYear(String title, String releaseDate) {
        DateFormat formatter = SimpleDateFormat.getDateInstance();
        try {
            Date date = new SimpleDateFormat("yyy-mm-dd").parse("2019-08-01");
            formatter.getCalendar().setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        StringBuilder sb = new StringBuilder(title);
        sb.append(" (").append(formatter.getCalendar().get(Calendar.YEAR)).append(")");
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
