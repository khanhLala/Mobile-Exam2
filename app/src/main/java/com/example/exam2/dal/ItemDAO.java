package com.example.exam2.dal;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.exam2.entities.Item;

import java.util.List;

@Dao
public interface ItemDAO {
    @Query("SELECT * FROM items")
    List<Item> getAll();

    @Insert
    void insert(Item item);

    @Update
    void update(Item item);

    @Delete
    void delete(Item item);

    @Query("SELECT * FROM items WHERE id = :id")
    Item getItemById(int id);
}
