package com.example.utsklp3mobile.data.repository;

import android.content.Context;
import com.example.utsklp3mobile.data.local.DatabaseHelper;
import com.example.utsklp3mobile.data.model.Ranking;
import java.util.List;

public class RankingRepository {

    private final DatabaseHelper dbHelper;

    public RankingRepository(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public List<Ranking> getAllRanking() {
        return dbHelper.getAllRanking();
    }
}