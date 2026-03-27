package com.example.exam2.activities;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.exam2.entities.Showtime;

import java.util.List;

@Dao
public interface ShowtimeDAO {
    @Query("SELECT * FROM showtimes WHERE movieId = :movieId")
    List<Showtime> getByMovie(int movieId);

    @Query("SELECT * FROM showtimes")
    List<Showtime> getAll();

    @Query("SELECT * FROM showtimes WHERE id = :id")
    Showtime getById(int id);

    @Insert
    void insert(Showtime showtime);
}
