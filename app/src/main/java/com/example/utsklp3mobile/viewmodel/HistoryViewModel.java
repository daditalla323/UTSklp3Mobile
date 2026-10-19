package com.example.utsklp3mobile.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.utsklp3mobile.data.model.BattleHistory;
import com.example.utsklp3mobile.data.repository.BattleRepository;

import java.util.List;

public class HistoryViewModel extends AndroidViewModel {

    private final BattleRepository battleRepository;
    private final MutableLiveData<List<BattleHistory>> historyList = new MutableLiveData<>();

    public HistoryViewModel(@NonNull Application application) {
        super(application);
        battleRepository = new BattleRepository(application);
        loadHistory();
    }

    public LiveData<List<BattleHistory>> getHistoryList() {
        return historyList;
    }

    public void loadHistory() {
        historyList.setValue(battleRepository.getAllHistory());
    }
}