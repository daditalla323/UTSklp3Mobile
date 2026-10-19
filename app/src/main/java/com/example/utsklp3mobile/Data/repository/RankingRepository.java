package com.example.utsklp3mobile.Data.repository;

import android.content.Context;
import com.example.utsklp3mobile.Data.local.DatabaseHelper;
import com.example.utsklp3mobile.Data.model.Ranking;
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