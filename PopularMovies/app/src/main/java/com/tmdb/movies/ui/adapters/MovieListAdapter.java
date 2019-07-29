package com.tmdb.movies.ui.adapters;

import android.arch.paging.PagedListAdapter;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tmdb.movies.R;
import com.tmdb.movies.databinding.MovieListItemBinding;
import com.tmdb.movies.model.Movie;
import com.tmdb.movies.utils.Constants;
import com.tmdb.movies.utils.GenresMapper;
import com.tmdb.movies.utils.MovieUtils;

public class MovieListAdapter extends PagedListAdapter<Movie, MovieListAdapter.MoviePagedViewHolder> {

    private SparseArray<String> mGenreMap;

    private final MovieListAdapter.MovieListAdapterOnClickHandler mOnClickHandler;

    public interface MovieListAdapterOnClickHandler {
        void onItemClick(Movie movie);
    }

    public MovieListAdapter(MovieListAdapterOnClickHandler onClickHandler) {
        super(DIFF_CALLBACK);
        mOnClickHandler = onClickHandler;
    }

    private static DiffUtil.ItemCallback<Movie> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Movie>() {
                @Override
                public boolean areItemsTheSame(Movie oldItem, Movie newItem) {
                    return oldItem.getId() == newItem.getId();
                }

                @Override
                public boolean areContentsTheSame(Movie oldItem, Movie newItem) {
                    return oldItem.equals(newItem);
                }
            };


    @NonNull
    @Override
    public MoviePagedViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
       MovieListItemBinding mMovieItemBinding = DataBindingUtil.inflate(
                layoutInflater, R.layout.movie_list_item, viewGroup, false);

        return new MoviePagedViewHolder(mMovieItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviePagedViewHolder viewHolder, int position) {

        Movie movie = getItem(position);
        String thumbnail = Constants.IMAGE_BASE_URL + Constants.IMAGE_FILE_SIZE + movie.getPoster_path();
        String genreValue = GenresMapper.getGenreNames(mGenreMap, movie.getGenre_ids(), 3);

        Picasso.with(viewHolder.itemView.getContext())
                .load(thumbnail)
                .error(R.drawable.image)
                .fit()
                .into(viewHolder.thumbnail);

        viewHolder.title.setText(MovieUtils.
                getTileYear(movie.getTitle(), movie.getRelease_date()));
        viewHolder.popularity.setText(String.valueOf(position+1));
        viewHolder.genre.setText(genreValue);
    }

    public class MoviePagedViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{

        private MovieListItemBinding mMovieItemBinding;

        public ImageView thumbnail;
        public TextView title;
        public TextView popularity;
        public TextView genre;

        public MoviePagedViewHolder(MovieListItemBinding movieItemBinding) {

            super(movieItemBinding.getRoot());
            mMovieItemBinding = movieItemBinding;
            thumbnail = movieItemBinding.movieThumbnail;
            title = movieItemBinding.movieTitle;
            popularity = movieItemBinding.popularityValue;
            genre = movieItemBinding.genre;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Movie movie = getItem(adapterPosition);
            mOnClickHandler.onItemClick(movie);
        }
    }

    public void setmGenreMap(SparseArray<String> genreMap) {
        mGenreMap = genreMap;
    }
}
