package com.example.exam2.dal;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.exam2.entities.Theater;

import java.util.List;

@Dao
public interface TheaterDAO {
    @Query("SELECT * FROM theaters")
    List<Theater> getAll();

    @Query("SELECT * FROM theaters WHERE id = :id")
    Theater getById(int id);

    @Insert
    void insert(Theater theater);
}
