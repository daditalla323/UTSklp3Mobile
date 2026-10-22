package com.example.utsklp3mobile.ui.home;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.utsklp3mobile.R;
import com.example.utsklp3mobile.ui.battle.BattleActivity;
import com.example.utsklp3mobile.ui.battle.ChooseModeActivity;
import com.example.utsklp3mobile.ui.friend.SearchFriendActivity;
import com.example.utsklp3mobile.ui.main.MainActivity;
import com.example.utsklp3mobile.viewmodel.HomeViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private TextView tvHomeUserName, tvProfileName, tvHomePoints, tvAddFriend;
    private TextView tvMatkulMobile, tvMatkulJarkom, tvMatkulSistop, tvMatkulBasdat;
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
        tvAddFriend = view.findViewById(R.id.tvAddFriend);

        tvMatkulMobile = view.findViewById(R.id.tvMatkulMobile);
        tvMatkulJarkom = view.findViewById(R.id.tvMatkulJarkom);
        tvMatkulSistop = view.findViewById(R.id.tvMatkulSistop);
        tvMatkulBasdat = view.findViewById(R.id.tvMatkulBasdat);

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

        // Setup Klik Pilihan Mata Kuliah
        setupMatkulSelection();

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

        // Navigasi ke SearchFriendActivity saat "+ Cari Teman" ditekan
        if (tvAddFriend != null) {
            tvAddFriend.setOnClickListener(v -> {
                Intent intent = new Intent(requireContext(), SearchFriendActivity.class);
                intent.putExtra("USER_NAME", currentUserName);
                startActivity(intent);
            });
        }

        // Observe ViewModel User Data
        homeViewModel.getCurrentUser().observe(getViewLifecycleOwner(), user -> {
            if (user != null) {
                if (tvHomePoints != null) {
                    tvHomePoints.setText(user.getTotalPoin() + " Pts");
                }
            }
        });

        // Event Tombol Mulai Battle -> Buka ChooseModeActivity untuk memilih Mode Battle
        View.OnClickListener openChooseModeListener = v -> {
            Intent intent = new Intent(requireContext(), ChooseModeActivity.class);
            intent.putExtra("USER_NAME", currentUserName);
            intent.putExtra("MATKUL", selectedMatkul);
            startActivity(intent);
        };

        if (cardStartBattle != null) {
            cardStartBattle.setOnClickListener(openChooseModeListener);
        }
        if (btnPlayNow != null) {
            btnPlayNow.setOnClickListener(openChooseModeListener);
        }
        if (cardRankedBattle != null) {
            cardRankedBattle.setOnClickListener(v -> launchBattle("RANKED"));
        }
        if (cardAiQuiz != null) {
            cardAiQuiz.setOnClickListener(v -> launchBattle("AI_CHALLENGE"));
        }
    }

    private void launchBattle(String mode) {
        Intent intent = new Intent(requireContext(), BattleActivity.class);
        intent.putExtra("USER_NAME", currentUserName);
        intent.putExtra("MATKUL", selectedMatkul);
        intent.putExtra("BATTLE_MODE", mode);
        startActivity(intent);
    }

    private void setupMatkulSelection() {
        if (tvMatkulMobile != null) {
            tvMatkulMobile.setOnClickListener(v -> selectMatkul("Pemrograman Mobile", tvMatkulMobile));
        }
        if (tvMatkulJarkom != null) {
            tvMatkulJarkom.setOnClickListener(v -> selectMatkul("Jaringan Komputer", tvMatkulJarkom));
        }
        if (tvMatkulSistop != null) {
            tvMatkulSistop.setOnClickListener(v -> selectMatkul("Sistem Operasi", tvMatkulSistop));
        }
        if (tvMatkulBasdat != null) {
            tvMatkulBasdat.setOnClickListener(v -> selectMatkul("Basis Data", tvMatkulBasdat));
        }
    }

    private void selectMatkul(String matkulName, TextView selectedTextView) {
        this.selectedMatkul = matkulName;

        // Reset semua chip ke unselected
        resetMatkulChip(tvMatkulMobile);
        resetMatkulChip(tvMatkulJarkom);
        resetMatkulChip(tvMatkulSistop);
        resetMatkulChip(tvMatkulBasdat);

        // Highlight chip yang dipilih
        if (selectedTextView != null) {
            selectedTextView.setBackgroundResource(R.drawable.bg_chip_primary);
            selectedTextView.setTextColor(Color.WHITE);
            selectedTextView.setTypeface(null, Typeface.BOLD);
        }

        Toast.makeText(requireContext(), "Mata Kuliah: " + matkulName, Toast.LENGTH_SHORT).show();
    }

    private void resetMatkulChip(TextView chip) {
        if (chip != null) {
            chip.setBackgroundResource(R.drawable.bg_chip_unselected);
            chip.setTextColor(Color.BLACK);
            chip.setTypeface(null, Typeface.NORMAL);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() instanceof MainActivity) {
            currentUserName = ((MainActivity) getActivity()).getUserName();
        }
        if (homeViewModel != null) {
            homeViewModel.loadUser(currentUserName);
        }
    }
}