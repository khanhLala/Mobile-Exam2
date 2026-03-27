package com.example.exam2.activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.exam2.R;
import com.example.exam2.dal.AppDB;
import com.example.exam2.entities.Movie;
import com.example.exam2.entities.Showtime;
import com.example.exam2.entities.Theater;
import com.example.exam2.entities.Ticket;

import java.text.DecimalFormat;

public class InvoiceActivity extends AppCompatActivity {
    private static final DecimalFormat formatter = new DecimalFormat("#,###");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice);

        int id = getIntent().getIntExtra("ticketId", -1);
        AppDB db = AppDB.getInstance(this);
        Ticket t = db.ticketDAO().getById(id);

        if (t != null) {
            Showtime s = db.showtimeDAO().getById(t.showtimeId);
            Movie m = db.movieDAO().getById(s.movieId);
            Theater r = db.theaterDAO().getById(s.theaterId);

            ((TextView)findViewById(R.id.tvInvoiceTicketId)).setText("Vé #" + t.id);
            ((TextView)findViewById(R.id.tvInvoiceMovie)).setText(m.title);
            ((TextView)findViewById(R.id.tvInvoiceTheater)).setText(r.name);
            ((TextView)findViewById(R.id.tvInvoiceTime)).setText(s.date + " " + s.time);
            ((TextView)findViewById(R.id.tvInvoiceSeat)).setText("GHẾ: " + t.seat);
            ((TextView)findViewById(R.id.tvInvoicePrice)).setText("Tổng: " + formatter.format(s.price) + " VNĐ");
        }

        findViewById(R.id.btnBackHome).setOnClickListener(v -> finish());
    }
}
