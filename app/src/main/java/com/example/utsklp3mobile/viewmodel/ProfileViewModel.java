package com.example.utsklp3mobile.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.utsklp3mobile.data.model.BattleHistory;
import com.example.utsklp3mobile.data.model.User;
import com.example.utsklp3mobile.data.repository.BattleRepository;
import com.example.utsklp3mobile.data.repository.UserRepository;

import java.util.List;

public class ProfileViewModel extends AndroidViewModel {

    private final BattleRepository battleRepository;
    private final UserRepository userRepository;

    private final MutableLiveData<List<BattleHistory>> historyList = new MutableLiveData<>();
    private final MutableLiveData<User> userProfile = new MutableLiveData<>();

    public ProfileViewModel(@NonNull Application application) {
        super(application);
        battleRepository = new BattleRepository(application);
        userRepository = new UserRepository(application);
    }

    public LiveData<List<BattleHistory>> getHistoryList() {
        return historyList;
    }

    public LiveData<User> getUserProfile() {
        return userProfile;
    }

    public void loadProfile(String userName) {
        User user = userRepository.getUserByName(userName);
        if (user != null) {
            userProfile.setValue(user);
        }
        if (userName != null && !userName.isEmpty()) {
            historyList.setValue(battleRepository.getHistoryByUser(userName));
        } else {
            historyList.setValue(battleRepository.getAllHistory());
        }
    }
}