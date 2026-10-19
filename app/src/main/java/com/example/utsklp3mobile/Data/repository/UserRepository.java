package com.example.utsklp3mobile.Data.repository;

import android.content.Context;
import com.example.utsklp3mobile.Data.local.DatabaseHelper;
import com.example.utsklp3mobile.Data.model.User;

public class UserRepository {

    private final DatabaseHelper dbHelper;

    public UserRepository(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public long insertUser(String nama) {
        return dbHelper.insertUser(nama);
    }

    public User getUserByName(String nama) {
        return dbHelper.getUserByName(nama);
    }

    public void updateUserAfterBattle(String nama, boolean isWin, int poinTambahan) {
        dbHelper.updateUserAfterBattle(nama, isWin, poinTambahan);
    }
}