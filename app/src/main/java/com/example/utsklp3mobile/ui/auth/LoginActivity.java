package com.example.utsklp3mobile.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.utsklp3mobile.R;
import com.example.utsklp3mobile.data.repository.UserRepository;
import com.example.utsklp3mobile.ui.main.MainActivity;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText etUsername, etPassword;
    private Button btnLogin, btnGoogleLogin;
    private TextView tvRegister;
    private UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userRepository = new UserRepository(this);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnGoogleLogin = findViewById(R.id.btnGoogleLogin);
        tvRegister = findViewById(R.id.tvRegister);

        btnLogin.setOnClickListener(v -> performLogin());

        btnGoogleLogin.setOnClickListener(v ->
                Toast.makeText(this, "Sign in with Google clicked", Toast.LENGTH_SHORT).show()
        );

        tvRegister.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    private void performLogin() {
        String username = etUsername.getText() != null ? etUsername.getText().toString().trim() : "";
        String password = etPassword.getText() != null ? etPassword.getText().toString().trim() : "";

        if (TextUtils.isEmpty(username)) {
            etUsername.setError("Nama Pengguna / Email wajib diisi");
            etUsername.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            etPassword.setError("Kata sandi wajib diisi");
            etPassword.requestFocus();
            return;
        }

        // Ambil nama pengguna (sebelum @ jika memasukkan email)
        String userName = username.contains("@") ? username.split("@")[0] : username;

        // Buat/ambil user di database
        if (userRepository.getUserByName(userName) == null) {
            userRepository.insertUser(userName);
        }

        Toast.makeText(this, "Login berhasil! Selamat datang " + userName, Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.putExtra("USER_NAME", userName);
        startActivity(intent);
        finish();
    }
}