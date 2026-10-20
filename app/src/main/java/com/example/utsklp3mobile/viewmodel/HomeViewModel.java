package com.example.utsklp3mobile.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.utsklp3mobile.data.model.User;
import com.example.utsklp3mobile.data.repository.UserRepository;

public class HomeViewModel extends AndroidViewModel {

    private final UserRepository userRepository;
    private final MutableLiveData<User> currentUser = new MutableLiveData<>();

    public HomeViewModel(@NonNull Application application) {
        super(application);
        userRepository = new UserRepository(application);
    }

    public LiveData<User> getCurrentUser() {
        return currentUser;
    }

    public void loadUser(String nama) {
        User user = userRepository.getUserByName(nama);
        if (user == null) {
            userRepository.insertUser(nama);
            user = userRepository.getUserByName(nama);
        }
        currentUser.setValue(user);
    }
}