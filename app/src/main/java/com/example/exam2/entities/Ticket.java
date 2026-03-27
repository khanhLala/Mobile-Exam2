package com.example.exam2.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "tickets",
        foreignKeys = {
                @ForeignKey(entity = User.class, parentColumns = "id", childColumns = "userId", onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = Showtime.class, parentColumns = "id", childColumns = "showtimeId", onDelete = ForeignKey.CASCADE)
        })
public class Ticket implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public int userId;
    public int showtimeId;
    public String seat;
    public String bookingDate;

    public Ticket(int userId, int showtimeId, String seat, String bookingDate) {
        this.userId = userId;
        this.showtimeId = showtimeId;
        this.seat = seat;
        this.bookingDate = bookingDate;
    }
}
