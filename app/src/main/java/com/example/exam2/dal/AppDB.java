package com.example.exam2.dal;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.exam2.entities.Item;

@Database(entities = {Item.class}, version = 1)
public abstract class AppDB extends RoomDatabase {
    private static AppDB instance;

    public abstract ItemDAO itemDAO();

    public static synchronized AppDB getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDB.class, "ExamDB")
                    .allowMainThreadQueries() // Only for simplicity in this base project
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
