package com.example.utsklp3mobile.ui.home;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.utsklp3mobile.R;
import com.example.utsklp3mobile.ui.battle.BattleActivity;
import com.example.utsklp3mobile.ui.main.MainActivity;
import com.example.utsklp3mobile.viewmodel.HomeViewModel;
import com.google.android.material.card.MaterialCardView;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private TextView tvHomeUserName, tvHomePoints, tvHomeWins, tvHomeBattles;
    private MaterialCardView cardMobile, cardJaringan, cardOS, cardDatabase;
    private Button btnStartBattle;
    private View cardGoRanking, cardGoHistory;

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
        tvHomePoints = view.findViewById(R.id.tvHomePoints);
        tvHomeWins = view.findViewById(R.id.tvHomeWins);
        tvHomeBattles = view.findViewById(R.id.tvHomeBattles);

        cardMobile = view.findViewById(R.id.cardMatkulMobile);
        cardJaringan = view.findViewById(R.id.cardMatkulJaringan);
        cardOS = view.findViewById(R.id.cardMatkulOS);
        cardDatabase = view.findViewById(R.id.cardMatkulDatabase);

        btnStartBattle = view.findViewById(R.id.btnStartBattle);
        cardGoRanking = view.findViewById(R.id.cardGoRanking);
        cardGoHistory = view.findViewById(R.id.cardGoHistory);

        if (getActivity() instanceof MainActivity) {
            currentUserName = ((MainActivity) getActivity()).getUserName();
        }

        tvHomeUserName.setText("Halo, " + currentUserName + "!");

        // Observe ViewModel User Data
        homeViewModel.getCurrentUser().observe(getViewLifecycleOwner(), user -> {
            if (user != null) {
                tvHomePoints.setText(user.getTotalPoin() + " Poin");
                tvHomeWins.setText(user.getJumlahMenang() + " Menang");
                tvHomeBattles.setText(user.getJumlahMain() + " Main");
            }
        });

        setupMatkulSelection();

        btnStartBattle.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), BattleActivity.class);
            intent.putExtra("USER_NAME", currentUserName);
            intent.putExtra("MATKUL", selectedMatkul);
            startActivity(intent);
        });

        if (cardGoRanking != null) {
            cardGoRanking.setOnClickListener(v -> {
                if (getActivity() instanceof MainActivity) {
                    ((MainActivity) getActivity()).selectTab(1);
                }
            });
        }

        if (cardGoHistory != null) {
            cardGoHistory.setOnClickListener(v -> {
                if (getActivity() instanceof MainActivity) {
                    ((MainActivity) getActivity()).selectTab(2);
                }
            });
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (homeViewModel != null) {
            homeViewModel.loadUser(currentUserName);
        }
    }

    private void setupMatkulSelection() {
        cardMobile.setOnClickListener(v -> selectCard(cardMobile, "Pemrograman Mobile"));
        cardJaringan.setOnClickListener(v -> selectCard(cardJaringan, "Jaringan Komputer"));
        cardOS.setOnClickListener(v -> selectCard(cardOS, "Sistem Operasi"));
        cardDatabase.setOnClickListener(v -> selectCard(cardDatabase, "Basis Data"));
    }

    private void selectCard(MaterialCardView selectedCard, String matkulName) {
        this.selectedMatkul = matkulName;

        resetCardStyle(cardMobile);
        resetCardStyle(cardJaringan);
        resetCardStyle(cardOS);
        resetCardStyle(cardDatabase);

        selectedCard.setStrokeColor(Color.parseColor("#293681"));
        selectedCard.setStrokeWidth(6);
    }

    private void resetCardStyle(MaterialCardView card) {
        card.setStrokeColor(Color.TRANSPARENT);
        card.setStrokeWidth(0);
    }
}
