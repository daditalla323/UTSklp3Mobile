package com.example.utsklp3mobile.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.utsklp3mobile.R;
import com.example.utsklp3mobile.ui.main.MainActivity;
import com.example.utsklp3mobile.viewmodel.ProfileViewModel;

public class ProfileFragment extends Fragment {

    private ProfileViewModel profileViewModel;
    private HistoryAdapter historyAdapter;

    private TextView tvAvatarInitials, tvProfileName, tvProfileStatus;
    private TextView tvTotalBattles, tvTotalWins, tvTotalPoints;
    private TextView tvHistoryCount, tvEmptyHistory;
    private TextView btnGoLeaderboard;
    private RecyclerView rvHistory;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        tvAvatarInitials = view.findViewById(R.id.tvAvatarInitials);
        tvProfileName = view.findViewById(R.id.tvProfileName);
        tvProfileStatus = view.findViewById(R.id.tvProfileStatus);
        tvTotalBattles = view.findViewById(R.id.tvTotalBattles);
        tvTotalWins = view.findViewById(R.id.tvTotalWins);
        tvTotalPoints = view.findViewById(R.id.tvTotalPoints);
        tvHistoryCount = view.findViewById(R.id.tvHistoryCount);
        tvEmptyHistory = view.findViewById(R.id.tvEmptyHistory);
        btnGoLeaderboard = view.findViewById(R.id.btnGoLeaderboard);
        rvHistory = view.findViewById(R.id.rvHistory);

        rvHistory.setLayoutManager(new LinearLayoutManager(getContext()));
        historyAdapter = new HistoryAdapter();
        rvHistory.setAdapter(historyAdapter);

        // Tombol cepat ke halaman Leaderboard / Ranking
        if (btnGoLeaderboard != null) {
            btnGoLeaderboard.setOnClickListener(v -> {
                if (getActivity() instanceof MainActivity) {
                    ((MainActivity) getActivity()).selectTab(1); // Posisi 1 adalah Ranking/Leaderboard
                }
            });
        }

        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);

        String userName = "Player";
        if (getActivity() != null && getActivity().getIntent() != null) {
            String intentUser = getActivity().getIntent().getStringExtra("USER_NAME");
            if (intentUser != null && !intentUser.isEmpty()) {
                userName = intentUser;
            }
        }

        profileViewModel.getUserProfile().observe(getViewLifecycleOwner(), user -> {
            if (user != null) {
                tvProfileName.setText(user.getNama());
                if (user.getNama() != null && !user.getNama().isEmpty()) {
                    tvAvatarInitials.setText(user.getNama().substring(0, 1).toUpperCase());
                }
                tvTotalBattles.setText(String.valueOf(user.getJumlahMain()));
                tvTotalWins.setText(String.valueOf(user.getJumlahMenang()));
                tvTotalPoints.setText(user.getTotalPoin() + " Pts");
            }
        });

        profileViewModel.getHistoryList().observe(getViewLifecycleOwner(), historyList -> {
            if (historyList != null && !historyList.isEmpty()) {
                tvEmptyHistory.setVisibility(View.GONE);
                rvHistory.setVisibility(View.VISIBLE);
                historyAdapter.setHistoryList(historyList);
                tvHistoryCount.setText(historyList.size() + " Matches");
            } else {
                tvEmptyHistory.setVisibility(View.VISIBLE);
                rvHistory.setVisibility(View.GONE);
                tvHistoryCount.setText("0 Matches");
            }
        });

        profileViewModel.loadProfile(userName);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (profileViewModel != null) {
            String userName = "Player";
            if (getActivity() != null && getActivity().getIntent() != null) {
                String intentUser = getActivity().getIntent().getStringExtra("USER_NAME");
                if (intentUser != null && !intentUser.isEmpty()) {
                    userName = intentUser;
                }
            }
            profileViewModel.loadProfile(userName);
        }
    }
}