package com.example.exam2.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.exam2.R;
import com.example.exam2.dal.AppDB;
import com.example.exam2.dal.SharedPrefManager;
import com.example.exam2.entities.User;

public class LoginActivity extends AppCompatActivity {
    private EditText etUsername, etPassword;
    private AppDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = AppDB.getInstance(this);
        
        // Khởi tạo dữ liệu mẫu ngay tại màn hình Login
        seedUsers();

        // Nếu đã login rồi thì vào thẳng MainActivity
        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
            return;
        }

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        Button btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(v -> {
            String userStr = etUsername.getText().toString();
            String passStr = etPassword.getText().toString();

            User loggedUser = db.userDAO().login(userStr, passStr);
            if (loggedUser != null) {
                // Sửa lỗi: Truy cập trực tiếp vào public fields thay vì dùng getter
                SharedPrefManager.getInstance(this).saveUser(loggedUser.id, loggedUser.username);
                Toast.makeText(this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                
                startActivity(new Intent(this, MainActivity.class));
                finish();
            } else {
                Toast.makeText(this, "Sai tài khoản hoặc mật khẩu", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void seedUsers() {
        new Thread(() -> {
            if (db.userDAO().login("admin", "admin") == null) {
                db.userDAO().register(new User("admin", "admin"));
            }
            if (db.userDAO().login("user", "1234") == null) {
                db.userDAO().register(new User("user", "1234"));
            }
        }).start();
    }
}
