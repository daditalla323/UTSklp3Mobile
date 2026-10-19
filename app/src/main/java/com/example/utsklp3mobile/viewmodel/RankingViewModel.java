package com.example.utsklp3mobile.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.utsklp3mobile.Data.model.Ranking;
import com.example.utsklp3mobile.Data.repository.RankingRepository;

import java.util.List;

public class RankingViewModel extends AndroidViewModel {

    private final RankingRepository rankingRepository;
    private final MutableLiveData<List<Ranking>> rankingList = new MutableLiveData<>();

    public RankingViewModel(@NonNull Application application) {
        super(application);
        rankingRepository = new RankingRepository(application);
        loadRanking();
    }

    public LiveData<List<Ranking>> getRankingList() {
        return rankingList;
    }

    public void loadRanking() {
        List<Ranking> data = rankingRepository.getAllRanking();
        rankingList.setValue(data);
    }
}