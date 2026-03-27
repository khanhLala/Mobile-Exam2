package com.example.exam2.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.exam2.R;
import com.example.exam2.adapters.OrderHistoryAdapter;
import com.example.exam2.dal.AppDB;
import com.example.exam2.dal.SharedPrefManager;
import com.example.exam2.entities.Ticket;

import java.util.List;

public class OrderHistoryActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

        RecyclerView rv = findViewById(R.id.rvOrderHistory);
        rv.setLayoutManager(new LinearLayoutManager(this));
        OrderHistoryAdapter adapter = new OrderHistoryAdapter();
        rv.setAdapter(adapter);

        adapter.setOnItemClickListener(t -> {
            Intent intent = new Intent(this, InvoiceActivity.class);
            intent.putExtra("ticketId", t.id);
            startActivity(intent);
        });

        int userId = SharedPrefManager.getInstance(this).getUserId();
        List<Ticket> list = AppDB.getInstance(this).ticketDAO().getByUser(userId);
        adapter.setTickets(list);
    }
}
