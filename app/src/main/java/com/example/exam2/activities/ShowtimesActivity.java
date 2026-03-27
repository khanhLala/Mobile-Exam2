package com.example.exam2.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.exam2.R;
import com.example.exam2.adapters.ShowtimeAdapter;
import com.example.exam2.dal.AppDB;
import com.example.exam2.dal.SharedPrefManager;
import com.example.exam2.entities.Movie;

public class ShowtimesActivity extends AppCompatActivity {
    private AppDB db;
    private Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showtimes);

        db = AppDB.getInstance(this);
        movie = (Movie) getIntent().getSerializableExtra("movie");

        TextView tvTitle = findViewById(R.id.tvShowtimesMovieTitle);
        tvTitle.setText(movie.title);

        RecyclerView rv = findViewById(R.id.rvShowtimes);
        ShowtimeAdapter adapter = new ShowtimeAdapter(db, showtime -> {
            if (SharedPrefManager.getInstance(this).isLoggedIn()) {
                Intent intent = new Intent(this, SeatSelectionActivity.class);
                intent.putExtra("showtime", showtime);
                startActivity(intent);
            } else {
                Toast.makeText(this, getString(R.string.login_required), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, LoginActivity.class));
            }
        });
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);

        adapter.setShowtimes(db.showtimeDAO().getByMovie(movie.id));
    }
}
