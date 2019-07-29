package com.tmdb.movies.utils;

import android.support.annotation.NonNull;
import android.util.SparseArray;

import com.tmdb.movies.model.Genre;

import java.util.List;

public class GenresMapper {

    private GenresMapper(){}

    public static SparseArray<String> toSparseArray(@NonNull List<Genre> genres) {
        SparseArray<String> genresSparseArray = new SparseArray<>();
        for (Genre genre : genres) {
            genresSparseArray.put(genre.getId(), genre.getName());
        }
        return genresSparseArray;
    }

    public static String getGenreNames(@NonNull SparseArray<String> orderedItems,
                                       @NonNull List<Integer> genreIds, final int onlyNFirstItems) {
        StringBuilder sb = new StringBuilder();
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
        return sb.toString();
    }
}
