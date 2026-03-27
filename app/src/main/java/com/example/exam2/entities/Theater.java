package com.example.exam2.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "theaters")
public class Theater implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String name;
    public String location;

    public Theater(String name, String location) {
        this.name = name;
        this.location = location;
    }
}
