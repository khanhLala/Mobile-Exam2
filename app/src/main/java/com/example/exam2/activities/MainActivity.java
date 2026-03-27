package com.example.exam2.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.exam2.R;
import com.example.exam2.adapters.MovieAdapter;
import com.example.exam2.adapters.ShowtimeAdapter;
import com.example.exam2.dal.AppDB;
import com.example.exam2.dal.SharedPrefManager;
import com.example.exam2.entities.Movie;
import com.example.exam2.entities.Showtime;
import com.example.exam2.entities.Theater;
import com.example.exam2.entities.User;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rvMainContent;
    private MovieAdapter movieAdapter;
    private ShowtimeAdapter showtimeAdapter;
    private AppDB db;
    private SharedPrefManager pref;
    private TextView tvWelcome;
    private Button btnLogin, btnShowAll, btnShowTheaters, btnShowShowtimes;
    private ImageButton btnHistory, btnLogout;
    private View theaterScroll;
    private LinearLayout layoutTheaters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = AppDB.getInstance(this);
        pref = SharedPrefManager.getInstance(this);

        tvWelcome = findViewById(R.id.tvWelcome);
        btnLogin = findViewById(R.id.btnLoginMain);
        btnHistory = findViewById(R.id.btnHistory);
        btnLogout = findViewById(R.id.btnLogout);
        rvMainContent = findViewById(R.id.rvMainContent);
        theaterScroll = findViewById(R.id.theaterScroll);
        layoutTheaters = findViewById(R.id.layoutTheaters);

        btnShowAll = findViewById(R.id.btnShowAll);
        btnShowTheaters = findViewById(R.id.btnShowTheaters);
        btnShowShowtimes = findViewById(R.id.btnShowShowtimes);

        seedData();

        // Adapter cho Phim
        movieAdapter = new MovieAdapter(movie -> {
            Intent intent = new Intent(this, ShowtimesActivity.class);
            intent.putExtra("movie", movie);
            startActivity(intent);
        });

        // Adapter cho Lịch chiếu
        showtimeAdapter = new ShowtimeAdapter(db, showtime -> {
            if (pref.isLoggedIn()) {
                Intent intent = new Intent(this, SeatSelectionActivity.class);
                intent.putExtra("showtime", showtime);
                startActivity(intent);
            } else {
                Toast.makeText(this, getString(R.string.login_required), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, LoginActivity.class));
            }
        });

        rvMainContent.setLayoutManager(new LinearLayoutManager(this));

        // Mặc định: Chế độ Tất cả phim
        showAllMovies();

        btnShowAll.setOnClickListener(v -> showAllMovies());
        btnShowTheaters.setOnClickListener(v -> showTheatersMode());
        btnShowShowtimes.setOnClickListener(v -> showAllShowtimes());

        btnLogin.setOnClickListener(v -> startActivity(new Intent(this, LoginActivity.class)));
        btnHistory.setOnClickListener(v -> startActivity(new Intent(this, OrderHistoryActivity.class)));
        btnLogout.setOnClickListener(v -> {
            pref.logout();
            updateUI();
        });
    }

    private void showAllMovies() {
        theaterScroll.setVisibility(View.GONE);
        rvMainContent.setAdapter(movieAdapter);
        movieAdapter.setMovies(db.movieDAO().getAll());
    }

    private void showTheatersMode() {
        theaterScroll.setVisibility(View.VISIBLE);
        rvMainContent.setAdapter(movieAdapter);
        loadTheaterFilterButtons();
        
        List<Theater> list = db.theaterDAO().getAll();
        if (!list.isEmpty()) {
            loadMoviesByTheater(list.get(0).id); // Load rạp đầu tiên mặc định
        } else {
            movieAdapter.setMovies(null);
        }
    }

    private void showAllShowtimes() {
        theaterScroll.setVisibility(View.GONE);
        rvMainContent.setAdapter(showtimeAdapter);
        showtimeAdapter.setShowtimes(db.showtimeDAO().getAll());
    }

    private void loadTheaterFilterButtons() {
        layoutTheaters.removeAllViews();
        List<Theater> theaters = db.theaterDAO().getAll();
        for (Theater t : theaters) {
            Button btn = new Button(this);
            btn.setText(t.name);
            btn.setAllCaps(false);
            btn.setOnClickListener(v -> loadMoviesByTheater(t.id));
            layoutTheaters.addView(btn);
        }
    }

    private void loadMoviesByTheater(int theaterId) {
        List<Movie> movies = db.movieDAO().getMoviesByTheater(theaterId);
        movieAdapter.setMovies(movies);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI() {
        if (pref.isLoggedIn()) {
            tvWelcome.setText(String.format(getString(R.string.welcome_user), pref.getUsername()));
            btnLogin.setVisibility(View.GONE);
            btnHistory.setVisibility(View.VISIBLE);
            btnLogout.setVisibility(View.VISIBLE);
        } else {
            tvWelcome.setText(getString(R.string.welcome_guest));
            btnLogin.setVisibility(View.VISIBLE);
            btnHistory.setVisibility(View.GONE);
            btnLogout.setVisibility(View.GONE);
        }
    }

    private void seedData() {
        new Thread(() -> {
            if (db.userDAO().login("user", "1234") == null) {
                db.userDAO().register(new User("user", "1234"));
            }
            if (db.movieDAO().getAll().isEmpty()) {
                db.movieDAO().insert(new Movie("Avengers: Endgame", "Hành động", "Siêu anh hùng đối đầu Thanos", 181));
                db.movieDAO().insert(new Movie("Inception", "Khoa học viễn tưởng", "Kẻ trộm giấc mơ", 148));
                db.movieDAO().insert(new Movie("The Dark Knight", "Hành động", "Batman đấu với Joker", 152));
                db.movieDAO().insert(new Movie("Interstellar", "Khoa học viễn tưởng", "Hành trình xuyên không gian", 169));
                db.movieDAO().insert(new Movie("Parasite", "Tâm lý", "Gia đình nghèo thâm nhập gia đình giàu", 132));
                db.movieDAO().insert(new Movie("Spider-Man: No Way Home", "Hành động", "Đa vũ trụ nhện", 148));
                db.movieDAO().insert(new Movie("Dune: Part Two", "Khoa học viễn tưởng", "Cuộc chiến trên hành tinh cát", 166));
                db.movieDAO().insert(new Movie("Oppenheimer", "Tiểu sử", "Cha đẻ bom nguyên tử", 180));
                db.movieDAO().insert(new Movie("Lật Mặt 7", "Gia đình", "Một điều ước của mẹ", 138));
                db.movieDAO().insert(new Movie("Mai", "Tâm lý", "Câu chuyện tình yêu của Mai", 131));

                db.theaterDAO().insert(new Theater("CGV Vincom Center", "Quận 1, TP.HCM"));
                db.theaterDAO().insert(new Theater("Lotte Cinema Cantavil", "Quận 2, TP.HCM"));
                db.theaterDAO().insert(new Theater("BHD Star Thảo Điền", "Quận 2, TP.HCM"));

                String date = "2023-12-25";
                for (int i = 1; i <= 3; i++) {
                    db.showtimeDAO().insert(new Showtime(i, 1, date, "10:00", 120000));
                    db.showtimeDAO().insert(new Showtime(i, 2, date, "14:30", 110000));
                    db.showtimeDAO().insert(new Showtime(i, 3, date, "19:00", 150000));
                }
                for (int i = 4; i <= 10; i++) {
                    db.showtimeDAO().insert(new Showtime(i, (i % 3) + 1, date, "18:00", 100000));
                }
            }
        }).start();
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}
