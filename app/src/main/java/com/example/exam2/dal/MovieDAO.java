package com.example.exam2.dal;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.exam2.entities.Movie;

import java.util.List;

@Dao
public interface MovieDAO {
    @Query("SELECT * FROM movies")
    List<Movie> getAll();

    @Query("SELECT * FROM movies WHERE id = :id")
    Movie getById(int id);

    @Query("SELECT DISTINCT movies.* FROM movies INNER JOIN showtimes ON movies.id = showtimes.movieId WHERE showtimes.theaterId = :theaterId")
    List<Movie> getMoviesByTheater(int theaterId);

    @Insert
    void insert(Movie movie);
}
