package com.example.exam2.dal;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.exam2.entities.User;

@Dao
public interface UserDAO {
    @Query("SELECT * FROM users WHERE username = :user AND password = :pass LIMIT 1")
    User login(String user, String pass);

    @Insert
    long register(User user);

    @Query("SELECT * FROM users WHERE id = :id")
    User getUserById(int id);
}
