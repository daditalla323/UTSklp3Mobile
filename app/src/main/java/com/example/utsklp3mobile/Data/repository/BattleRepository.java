package com.example.utsklp3mobile.data.repository;

import android.content.Context;
import com.example.utsklp3mobile.data.local.DatabaseHelper;
import com.example.utsklp3mobile.data.model.BattleHistory;
import java.util.List;

public class BattleRepository {

    private final DatabaseHelper dbHelper;

    public BattleRepository(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void insertHistory(String lawan, String mataKuliah, String hasil, int poin, String tanggal) {
        dbHelper.insertHistory(lawan, mataKuliah, hasil, poin, tanggal);
    }

    public List<BattleHistory> getAllHistory() {
        return dbHelper.getAllHistory();
    }

    public void updateUserAfterBattle(String nama, boolean isWin, int poinTambahan) {
        dbHelper.updateUserAfterBattle(nama, isWin, poinTambahan);
    }
}