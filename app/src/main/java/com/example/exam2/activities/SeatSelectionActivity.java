package com.example.exam2.activities;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.exam2.R;
import com.example.exam2.dal.AppDB;
import com.example.exam2.dal.SharedPrefManager;
import com.example.exam2.entities.Showtime;
import com.example.exam2.entities.Ticket;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SeatSelectionActivity extends AppCompatActivity {
    private String selectedSeat = "";
    private Showtime showtime;
    private AppDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat_selection);

        db = AppDB.getInstance(this);
        showtime = (Showtime) getIntent().getSerializableExtra("showtime");
        
        GridLayout grid = findViewById(R.id.gridSeats);
        TextView tvSeat = findViewById(R.id.tvSelectedSeat);
        Button btnConfirm = findViewById(R.id.btnConfirmBooking);

        if (showtime == null) return;

        // Lấy danh sách các ghế đã được đặt
        List<String> bookedSeats = db.ticketDAO().getBookedSeatsByShowtime(showtime.id);

        grid.setColumnCount(5); // Đảm bảo có 5 cột

        for (int i = 0; i < 20; i++) {
            final String name = "Ghế " + (i + 1);
            Button b = new Button(this);
            b.setText(name);
            b.setTextSize(12);
            
            // Thiết lập vị trí trong Grid
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = 0;
            params.height = GridLayout.LayoutParams.WRAP_CONTENT;
            params.setGravity(Gravity.CENTER);
            params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
            params.rowSpec = GridLayout.spec(GridLayout.UNDEFINED);
            params.setMargins(8, 8, 8, 8);
            b.setLayoutParams(params);

            if (bookedSeats.contains(name)) {
                b.setEnabled(false);
                b.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#F44336"))); // Đỏ
                b.setTextColor(Color.WHITE);
                b.setText(name + "\n(Hết)");
            } else {
                b.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#4CAF50"))); // Xanh
                b.setTextColor(Color.WHITE);
                
                b.setOnClickListener(v -> {
                    selectedSeat = name;
                    tvSeat.setText("Ghế chọn: " + name);
                    Toast.makeText(this, "Đã chọn " + name, Toast.LENGTH_SHORT).show();
                });
            }
            grid.addView(b);
        }

        btnConfirm.setOnClickListener(v -> {
            if (selectedSeat.isEmpty()) {
                Toast.makeText(this, "Vui lòng chọn ghế!", Toast.LENGTH_SHORT).show();
                return;
            }

            Ticket existing = db.ticketDAO().getTicketByShowtimeAndSeat(showtime.id, selectedSeat);
            if (existing != null) {
                Toast.makeText(this, "Ghế này vừa có người đặt!", Toast.LENGTH_LONG).show();
                recreate();
                return;
            }

            int userId = SharedPrefManager.getInstance(this).getUserId();
            String date = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(new Date());
            Ticket t = new Ticket(userId, showtime.id, selectedSeat, date);
            long id = db.ticketDAO().insert(t);
            
            Intent intent = new Intent(this, InvoiceActivity.class);
            intent.putExtra("ticketId", (int)id);
            startActivity(intent);
            finish();
        });
    }
}
