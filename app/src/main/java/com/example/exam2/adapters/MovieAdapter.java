package com.example.exam2.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.exam2.R;
import com.example.exam2.entities.Movie;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private List<Movie> movies = new ArrayList<>();
    private OnMovieClickListener listener;

    public interface OnMovieClickListener {
        void onShowtimesClick(Movie movie);
    }

    public MovieAdapter(OnMovieClickListener listener) {
        this.listener = listener;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = movies.get(position);
        holder.tvTitle.setText(movie.title);
        holder.tvGenre.setText(movie.genre + " | " + movie.duration + " min");
        holder.btnShowtimes.setOnClickListener(v -> listener.onShowtimesClick(movie));
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    static class MovieViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvGenre;
        Button btnShowtimes;
        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvMovieTitle);
            tvGenre = itemView.findViewById(R.id.tvMovieGenre);
            btnShowtimes = itemView.findViewById(R.id.btnViewShowtimes);
        }
    }
}
