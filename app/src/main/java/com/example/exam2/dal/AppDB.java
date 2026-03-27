package com.example.exam2.dal;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.exam2.entities.Movie;
import com.example.exam2.entities.Showtime;
import com.example.exam2.entities.Theater;
import com.example.exam2.entities.Ticket;
import com.example.exam2.entities.User;

@Database(entities = {User.class, Movie.class, Theater.class, Showtime.class, Ticket.class}, version = 4)
public abstract class AppDB extends RoomDatabase {
    private static AppDB instance;
    public abstract UserDAO userDAO();
    public abstract MovieDAO movieDAO();
    public abstract TheaterDAO theaterDAO();
    public abstract ShowtimeDAO showtimeDAO();
    public abstract TicketDAO ticketDAO();

    public static synchronized AppDB getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    AppDB.class, "TicketBookingDB")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
