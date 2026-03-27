package com.example.exam2.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "movies")
public class Movie implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String title;
    public String genre;
    public String description;
    public int duration;

    public Movie(String title, String genre, String description, int duration) {
        this.title = title;
        this.genre = genre;
        this.description = description;
        this.duration = duration;
    }
}
