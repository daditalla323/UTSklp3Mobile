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

    private TextInputEditText etEmail, etPassword;
    private Button btnLogin, btnGoogleLogin;
    private TextView tvRegisterRedirect;
    private UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userRepository = new UserRepository(this);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnGoogleLogin = findViewById(R.id.btnGoogleLogin);
        tvRegisterRedirect = findViewById(R.id.tvRegisterRedirect);

        btnLogin.setOnClickListener(v -> performLogin());

        btnGoogleLogin.setOnClickListener(v ->
                Toast.makeText(this, "Sign in with Google clicked", Toast.LENGTH_SHORT).show()
        );

        tvRegisterRedirect.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    private void performLogin() {
        String email = etEmail.getText() != null ? etEmail.getText().toString().trim() : "";
        String password = etPassword.getText() != null ? etPassword.getText().toString().trim() : "";

        if (TextUtils.isEmpty(email)) {
            etEmail.setError("Email is required");
            etEmail.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            etPassword.setError("Password is required");
            etPassword.requestFocus();
            return;
        }

        // Ambil nama dari bagian depan email (sebelum @)
        String userName = email.contains("@") ? email.split("@")[0] : email;

        // Buat/ambil user di database
        if (userRepository.getUserByName(userName) == null) {
            userRepository.insertUser(userName);
        }

        Toast.makeText(this, "Login successful! Welcome " + userName, Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.putExtra("USER_NAME", userName);
        startActivity(intent);
        finish();
    }
}