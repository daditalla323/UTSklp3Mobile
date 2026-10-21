package com.example.utsklp3mobile.ui.battle;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.utsklp3mobile.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

public class ChooseModeActivity extends AppCompatActivity {

    private ImageButton btnBack;
    private TextView tvSelectedMatkul;
    private MaterialCardView cardRankedMode, cardAiMode;
    private MaterialButton btnSelectRanked, btnSelectAi;

    private String currentUserName = "Pemain";
    private String selectedMatkul = "Pemrograman Mobile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_mode);

        if (getIntent() != null) {
            if (getIntent().hasExtra("USER_NAME")) {
                currentUserName = getIntent().getStringExtra("USER_NAME");
            }
            if (getIntent().hasExtra("MATKUL")) {
                selectedMatkul = getIntent().getStringExtra("MATKUL");
            }
        }

        btnBack = findViewById(R.id.btnBack);
        tvSelectedMatkul = findViewById(R.id.tvSelectedMatkul);
        cardRankedMode = findViewById(R.id.cardRankedMode);
        cardAiMode = findViewById(R.id.cardAiMode);
        btnSelectRanked = findViewById(R.id.btnSelectRanked);
        btnSelectAi = findViewById(R.id.btnSelectAi);

        if (tvSelectedMatkul != null) {
            tvSelectedMatkul.setText("Mata Kuliah: " + selectedMatkul);
        }

        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        // Event pilih Mode Ranked Arena
        if (cardRankedMode != null) {
            cardRankedMode.setOnClickListener(v -> launchBattleMode("RANKED"));
        }
        if (btnSelectRanked != null) {
            btnSelectRanked.setOnClickListener(v -> launchBattleMode("RANKED"));
        }

        // Event pilih Mode AI Challenge
        if (cardAiMode != null) {
            cardAiMode.setOnClickListener(v -> launchBattleMode("AI_CHALLENGE"));
        }
        if (btnSelectAi != null) {
            btnSelectAi.setOnClickListener(v -> launchBattleMode("AI_CHALLENGE"));
        }
    }

    private void launchBattleMode(String battleMode) {
        Intent intent = new Intent(this, BattleActivity.class);
        intent.putExtra("USER_NAME", currentUserName);
        intent.putExtra("MATKUL", selectedMatkul);
        intent.putExtra("BATTLE_MODE", battleMode);
        startActivity(intent);
        finish();
    }
}