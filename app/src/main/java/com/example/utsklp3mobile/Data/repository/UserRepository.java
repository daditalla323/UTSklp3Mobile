package com.example.utsklp3mobile.Data.repository;

import android.content.Context;
import com.example.utsklp3mobile.data.local.DatabaseHelper;

public class UserRepository {
    private final DatabaseHelper dbHelper;

    public UserRepository(Context context) {
        this.dbHelper = new DatabaseHelper(context);
    }
}