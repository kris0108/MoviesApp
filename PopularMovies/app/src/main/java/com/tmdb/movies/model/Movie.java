package com.tmdb.movies.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Movie implements Parcelable {

    @SerializedName("id")
    @Expose
    private float id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("popularity")
    @Expose
    private float popularity;
    @SerializedName("poster_path")
    @Expose
    private String poster_path;
    @SerializedName("genre_ids")
    @Expose
    List<Integer> genre_ids;
    @SerializedName("release_date")
    @Expose
    private String release_date;

    public float getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public float getPopularity() {
        return popularity;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public String getRelease_date() {
        return release_date;
    }

    public List<Integer> getGenre_ids() {
        return genre_ids;
    }

/*    public Movie(int id, String title, String posterPath, float popularity,
                 List<Integer> genreIds, String releaseDate) {
        this.id = id;
        this.title = title;
        this.poster_path = posterPath;
        this.popularity = popularity;
        this.release_date = releaseDate;
        this.genre_ids = genreIds;
    }*/

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {

        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    private Movie(Parcel in) {
        id = in.readInt();
        title = in.readString();
        poster_path = in.readString();
        popularity = in.readFloat();
        release_date = in.readString();
        in.readList(this.genre_ids, Integer.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.title);
        dest.writeString(this.poster_path);
        dest.writeValue(this.popularity);
        dest.writeString(this.release_date);
        dest.writeList(this.genre_ids);
    }
}
