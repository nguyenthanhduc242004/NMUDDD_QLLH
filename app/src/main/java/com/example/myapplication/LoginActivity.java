package com.example.myapplication;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginActivity extends AppCompatActivity {

    DatabaseHandler databaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        databaseHandler = new DatabaseHandler(this);
        databaseHandler.addUser(new User("duc", "123"));

        TextView inputUsername = findViewById(R.id.usernameInput);
        TextView inputPassword = findViewById(R.id.passwordInput);
        Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(v -> {
            String username = inputUsername.getText().toString();
            String password = inputPassword.getText().toString();
            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }
            String realPassword = databaseHandler.getUserPassword(username);
            if (realPassword == null) {
                Toast.makeText(this, "Tài khoản không tồn tại", Toast.LENGTH_SHORT).show();
            }
            else if (!realPassword.equals(password)) {
                Toast.makeText(this, "Mật khẩu không đúng", Toast.LENGTH_SHORT).show();
            }
            else if (realPassword.equals(password)) {
                Toast.makeText(this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}