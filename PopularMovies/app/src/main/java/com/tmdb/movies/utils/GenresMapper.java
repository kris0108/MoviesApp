package com.tmdb.movies.utils;

import android.support.annotation.NonNull;
import android.util.SparseArray;

import com.tmdb.movies.model.Genre;

import java.util.List;

import timber.log.Timber;

public class GenresMapper {

    private GenresMapper() {
    }

    public static SparseArray<String> toSparseArray(@NonNull List<Genre> genres) {
        SparseArray<String> genresSparseArray = new SparseArray<>();
        for (Genre genre : genres) {
            genresSparseArray.put(genre.getId(), genre.getName());
        }
        return genresSparseArray;
    }

    public static String getGenreText(@NonNull List<Genre> genres, int onlyFirstNElements) {
        StringBuilder sb = new StringBuilder();
        if (null != genres) {
            int size = genres.size();
            Timber.d("getGenreText:" + size);
            if (size < onlyFirstNElements) {
                onlyFirstNElements = size;
            }

            for (int i = 0; i < onlyFirstNElements; i++) {
                sb.append(genres.get(i).getName());
                if (i >= 0 && i < onlyFirstNElements - 1)
                    sb.append(",").append(" ");
            }
        }
        return sb.toString();
    }

    public static String getGenreNames(@NonNull SparseArray<String> orderedItems,
                                       @NonNull List<Integer> genreIds, final int onlyNFirstItems) {
        StringBuilder sb = new StringBuilder();
        if (null != orderedItems) {
            int size = genreIds.size();
            boolean onlyNFirstItemsHasSpecified = onlyNFirstItems != 0;
            for (int i = 0; i < size; i++) {
                if (onlyNFirstItemsHasSpecified && i > onlyNFirstItems - 1) {
                    break;
                }
                sb.append(orderedItems.get(genreIds.get(i)));
                if (onlyNFirstItemsHasSpecified) {
                    sb.append(i < onlyNFirstItems - 1 && i < size - 1 ? ", " : " ");
                } else {
                    sb.append(i < size - 1 ? ", " : " ");
                }
            }
        }
        return sb.toString();
    }
}
