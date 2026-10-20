package com.example.utsklp3mobile.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.utsklp3mobile.R;
import com.example.utsklp3mobile.data.repository.UserRepository;
import com.example.utsklp3mobile.ui.main.MainActivity;
import com.google.android.material.textfield.TextInputEditText;

public class RegisterActivity extends AppCompatActivity {

    private TextInputEditText etFullName, etEmail, etPhone, etPassword, etConfirmPassword;
    private CheckBox cbTerms;
    private Button btnSignUp, btnGoogleSignUp;
    private TextView tvLoginRedirect;
    private UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        userRepository = new UserRepository(this);

        etFullName = findViewById(R.id.etFullName);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        cbTerms = findViewById(R.id.cbTerms);
        btnSignUp = findViewById(R.id.btnSignUp);
        btnGoogleSignUp = findViewById(R.id.btnGoogleSignUp);
        tvLoginRedirect = findViewById(R.id.tvLoginRedirect);

        btnSignUp.setOnClickListener(v -> performRegister());

        btnGoogleSignUp.setOnClickListener(v ->
                Toast.makeText(this, "Sign up with Google clicked", Toast.LENGTH_SHORT).show()
        );

        tvLoginRedirect.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void performRegister() {
        String fullName = etFullName.getText() != null ? etFullName.getText().toString().trim() : "";
        String email = etEmail.getText() != null ? etEmail.getText().toString().trim() : "";
        String phone = etPhone.getText() != null ? etPhone.getText().toString().trim() : "";
        String password = etPassword.getText() != null ? etPassword.getText().toString().trim() : "";
        String confirmPassword = etConfirmPassword.getText() != null ? etConfirmPassword.getText().toString().trim() : "";

        if (TextUtils.isEmpty(fullName)) {
            etFullName.setError("Full name is required");
            etFullName.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(email)) {
            etEmail.setError("Email is required");
            etEmail.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(phone)) {
            etPhone.setError("Phone number is required");
            etPhone.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            etPassword.setError("Password is required");
            etPassword.requestFocus();
            return;
        }

        if (!password.equals(confirmPassword)) {
            etConfirmPassword.setError("Passwords do not match");
            etConfirmPassword.requestFocus();
            return;
        }

        if (!cbTerms.isChecked()) {
            Toast.makeText(this, "Please agree to the Terms & Privacy Policy", Toast.LENGTH_SHORT).show();
            return;
        }

        // Simpan user ke database
        userRepository.insertUser(fullName);

        Toast.makeText(this, "Account created successfully!", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
        intent.putExtra("USER_NAME", fullName);
        startActivity(intent);
        finish();
    }
}