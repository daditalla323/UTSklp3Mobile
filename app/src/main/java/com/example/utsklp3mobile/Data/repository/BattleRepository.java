package com.example.utsklp3mobile.data.repository;

import android.content.Context;
import com.example.utsklp3mobile.data.local.DatabaseHelper;

public class BattleRepository {
    private final DatabaseHelper dbHelper;

    public BattleRepository(Context context) {
        this.dbHelper = new DatabaseHelper(context);
    }
}