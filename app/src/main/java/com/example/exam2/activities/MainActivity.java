package com.example.exam2.activities;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.exam2.R;
import com.example.exam2.adapters.ItemAdapter;
import com.example.exam2.dal.AppDB;
import com.example.exam2.dal.ItemDAO;
import com.example.exam2.dal.SharedPrefManager;
import com.example.exam2.entities.Item;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rvItems;
    private ItemAdapter adapter;
    private ItemDAO dao;
    private SharedPrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // SharedPreferences Check
        prefManager = SharedPrefManager.getInstance(this);
        if (!prefManager.isLoggedIn()) {
            // Logic for login (e.g., set to true for testing or redirect)
            prefManager.setLoggedIn(true);
            Toast.makeText(this, "First time login!", Toast.LENGTH_SHORT).show();
        }

        rvItems = findViewById(R.id.rvItems);
        adapter = new ItemAdapter();
        rvItems.setLayoutManager(new LinearLayoutManager(this));
        rvItems.setAdapter(adapter);

        // Room DB Setup
        dao = AppDB.getInstance(this).itemDAO();

        // Sample data for Room
        new Thread(() -> {
            if (dao.getAll().isEmpty()) {
                dao.insert(new Item("Sample Room Item 1", "Description 1"));
                dao.insert(new Item("Sample Room Item 2", "Description 2"));
            }
            runOnUiThread(this::loadData);
        }).start();
    }

    private void loadData() {
        List<Item> items = dao.getAll();
        adapter.setItems(items);
    }
}
