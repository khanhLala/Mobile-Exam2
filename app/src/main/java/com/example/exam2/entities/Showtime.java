package com.example.exam2.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "showtimes",
        foreignKeys = {
                @ForeignKey(entity = Movie.class, parentColumns = "id", childColumns = "movieId", onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = Theater.class, parentColumns = "id", childColumns = "theaterId", onDelete = ForeignKey.CASCADE)
        })
public class Showtime implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public int movieId;
    public int theaterId;
    public String date;
    public String time;
    public double price;

    public Showtime(int movieId, int theaterId, String date, String time, double price) {
        this.movieId = movieId;
        this.theaterId = theaterId;
        this.date = date;
        this.time = time;
        this.price = price;
    }
}
