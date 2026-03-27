package com.example.exam2.dal;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.exam2.entities.Ticket;

import java.util.List;

@Dao
public interface TicketDAO {
    @Insert
    long insert(Ticket ticket);

    @Query("SELECT * FROM tickets WHERE userId = :userId ORDER BY bookingDate DESC")
    List<Ticket> getByUser(int userId);

    @Query("SELECT * FROM tickets WHERE showtimeId = :showtimeId AND seat = :seat LIMIT 1")
    Ticket getTicketByShowtimeAndSeat(int showtimeId, String seat);

    @Query("SELECT seat FROM tickets WHERE showtimeId = :showtimeId")
    List<String> getBookedSeatsByShowtime(int showtimeId);

    @Query("SELECT * FROM tickets WHERE id = :id")
    Ticket getById(int id);
}
