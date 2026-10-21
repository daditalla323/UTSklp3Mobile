package com.example.utsklp3mobile.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.utsklp3mobile.R;
import com.example.utsklp3mobile.ui.battle.BattleActivity;
import com.example.utsklp3mobile.ui.main.MainActivity;
import com.example.utsklp3mobile.viewmodel.HomeViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private TextView tvHomeUserName, tvProfileName, tvHomePoints;
    private MaterialCardView cardProfileRight, cardStartBattle, cardRankedBattle, cardAiQuiz;
    private MaterialButton btnPlayNow;
    private ImageView ivProfileHeaderBg;

    private String selectedMatkul = "Pemrograman Mobile";
    private String currentUserName = "Pemain";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        tvHomeUserName = view.findViewById(R.id.tvHomeUserName);
        tvProfileName = view.findViewById(R.id.tvProfileName);
        tvHomePoints = view.findViewById(R.id.tvHomePoints);

        cardProfileRight = view.findViewById(R.id.cardProfileRight);
        ivProfileHeaderBg = view.findViewById(R.id.ivProfileHeaderBg);
        cardStartBattle = view.findViewById(R.id.cardStartBattle);
        cardRankedBattle = view.findViewById(R.id.cardRankedBattle);
        cardAiQuiz = view.findViewById(R.id.cardAiQuiz);
        btnPlayNow = view.findViewById(R.id.btnPlayNow);

        if (getActivity() instanceof MainActivity) {
            currentUserName = ((MainActivity) getActivity()).getUserName();
        }

        if (tvHomeUserName != null) {
            tvHomeUserName.setText("Selamat Pagi, " + currentUserName + "! 👋");
        }
        if (tvProfileName != null) {
            tvProfileName.setText(currentUserName);
        }

        // Navigasi ke ProfileFragment saat profil ditekan
        View.OnClickListener goToProfileListener = v -> {
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).selectTab(3);
            }
        };

        if (cardProfileRight != null) {
            cardProfileRight.setOnClickListener(goToProfileListener);
        }
        if (ivProfileHeaderBg != null) {
            ivProfileHeaderBg.setOnClickListener(goToProfileListener);
        }

        // Observe ViewModel User Data
        homeViewModel.getCurrentUser().observe(getViewLifecycleOwner(), user -> {
            if (user != null) {
                if (tvHomePoints != null) {
                    tvHomePoints.setText(user.getTotalPoin() + " Pts");
                }
            }
        });

        // Event Tombol Mulai Battle
        View.OnClickListener startBattleListener = v -> {
            Intent intent = new Intent(requireContext(), BattleActivity.class);
            intent.putExtra("USER_NAME", currentUserName);
            intent.putExtra("MATKUL", selectedMatkul);
            startActivity(intent);
        };

        if (cardStartBattle != null) cardStartBattle.setOnClickListener(startBattleListener);
        if (btnPlayNow != null) btnPlayNow.setOnClickListener(startBattleListener);
        if (cardRankedBattle != null) cardRankedBattle.setOnClickListener(startBattleListener);
        if (cardAiQuiz != null) cardAiQuiz.setOnClickListener(startBattleListener);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (homeViewModel != null) {
            homeViewModel.loadUser(currentUserName);
        }
    }
}