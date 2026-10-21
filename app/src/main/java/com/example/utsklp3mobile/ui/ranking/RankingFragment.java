package com.example.utsklp3mobile.ui.ranking;

import android.content.Intent;
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
import com.example.utsklp3mobile.data.model.Ranking;
import com.example.utsklp3mobile.ui.battle.BattleActivity;
import com.example.utsklp3mobile.viewmodel.RankingViewModel;

import java.util.ArrayList;
import java.util.List;

public class RankingFragment extends Fragment {

    private RankingViewModel rankingViewModel;

    private RecyclerView rvRanking;
    private RankingAdapter rankingAdapter;
    private TextView tvEmptyRankingLainnya;

    private RecyclerView rvTerbaru;
    private RankingAdapter terbaruAdapter;
    private View cardEmptyTerbaru;

    private TextView tvPodium1Name, tvPodium1Points;
    private TextView tvPodium2Name, tvPodium2Points;
    private TextView tvPodium3Name, tvPodium3Points;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ranking, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Inisialisasi komponen Filter
        TextView btnTerbaru = view.findViewById(R.id.btnFilterTerbaru);
        TextView btnSemua = view.findViewById(R.id.btnFilterSemua);
        View layoutTerbaru = view.findViewById(R.id.layoutTerbaru);
        View layoutSemua = view.findViewById(R.id.layoutSemua);

        if (btnTerbaru != null && btnSemua != null && layoutTerbaru != null && layoutSemua != null) {
            btnTerbaru.setOnClickListener(v -> {
                layoutSemua.setVisibility(View.GONE);
                layoutTerbaru.setVisibility(View.VISIBLE);
                btnTerbaru.setAlpha(1.0f);
                btnSemua.setAlpha(0.6f);
            });

            btnSemua.setOnClickListener(v -> {
                layoutTerbaru.setVisibility(View.GONE);
                layoutSemua.setVisibility(View.VISIBLE);
                btnTerbaru.setAlpha(0.6f);
                btnSemua.setAlpha(1.0f);
            });
        }

        // Tombol Mulai Battle di Empty State
        View btnStartBattle = view.findViewById(R.id.btnStartBattle);
        if (btnStartBattle != null) {
            btnStartBattle.setOnClickListener(v -> {
                Intent intent = new Intent(requireContext(), BattleActivity.class);
                startActivity(intent);
            });
        }

        // Inisialisasi Peringkat Terbaru
        cardEmptyTerbaru = view.findViewById(R.id.cardEmptyTerbaru);
        rvTerbaru = view.findViewById(R.id.rvTerbaru);
        if (rvTerbaru != null) {
            terbaruAdapter = new RankingAdapter();
            rvTerbaru.setLayoutManager(new LinearLayoutManager(requireContext()));
            rvTerbaru.setAdapter(terbaruAdapter);
        }

        // Inisialisasi Podium & List Semua (#4 ke bawah)
        rvRanking = view.findViewById(R.id.rvRanking);
        tvEmptyRankingLainnya = view.findViewById(R.id.tvEmptyRankingLainnya);
        tvPodium1Name = view.findViewById(R.id.tvPodium1Name);
        tvPodium1Points = view.findViewById(R.id.tvPodium1Points);
        tvPodium2Name = view.findViewById(R.id.tvPodium2Name);
        tvPodium2Points = view.findViewById(R.id.tvPodium2Points);
        tvPodium3Name = view.findViewById(R.id.tvPodium3Name);
        tvPodium3Points = view.findViewById(R.id.tvPodium3Points);

        if (rvRanking != null) {
            rankingAdapter = new RankingAdapter();
            rvRanking.setLayoutManager(new LinearLayoutManager(requireContext()));
            rvRanking.setAdapter(rankingAdapter);
        }

        rankingViewModel = new ViewModelProvider(this).get(RankingViewModel.class);
        rankingViewModel.getRankingList().observe(getViewLifecycleOwner(), list -> {
            if (list != null && !list.isEmpty()) {
                updatePodiumAndList(list);
                updateTerbaruRanking(list);
            } else {
                updatePodiumAndList(new ArrayList<>());
                updateTerbaruRanking(new ArrayList<>());
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (rankingViewModel != null) {
            rankingViewModel.loadRanking();
        }
    }

    private void updateTerbaruRanking(List<Ranking> list) {
        if (list == null || list.isEmpty()) {
            if (cardEmptyTerbaru != null) cardEmptyTerbaru.setVisibility(View.VISIBLE);
            if (rvTerbaru != null) rvTerbaru.setVisibility(View.GONE);
        } else {
            if (cardEmptyTerbaru != null) cardEmptyTerbaru.setVisibility(View.GONE);
            if (rvTerbaru != null) rvTerbaru.setVisibility(View.VISIBLE);
            if (terbaruAdapter != null) terbaruAdapter.setRankingList(list, 1);
        }
    }

    private void updatePodiumAndList(List<Ranking> list) {
        if (list == null || list.isEmpty()) {
            if (tvPodium1Name != null) tvPodium1Name.setText("-");
            if (tvPodium1Points != null) tvPodium1Points.setText("0 Poin");
            if (tvPodium2Name != null) tvPodium2Name.setText("-");
            if (tvPodium2Points != null) tvPodium2Points.setText("0 Poin");
            if (tvPodium3Name != null) tvPodium3Name.setText("-");
            if (tvPodium3Points != null) tvPodium3Points.setText("0 Poin");

            if (rankingAdapter != null) rankingAdapter.setRankingList(new ArrayList<>(), 4);
            if (rvRanking != null) rvRanking.setVisibility(View.GONE);
            if (tvEmptyRankingLainnya != null) tvEmptyRankingLainnya.setVisibility(View.VISIBLE);
            return;
        }

        // Podium Juara 1
        if (list.size() > 0 && tvPodium1Name != null) {
            Ranking r1 = list.get(0);
            tvPodium1Name.setText(r1.getNama());
            if (tvPodium1Points != null) tvPodium1Points.setText(r1.getTotalPoin() + " Poin");
        }

        // Podium Juara 2
        if (list.size() > 1 && tvPodium2Name != null) {
            Ranking r2 = list.get(1);
            tvPodium2Name.setText(r2.getNama());
            if (tvPodium2Points != null) tvPodium2Points.setText(r2.getTotalPoin() + " Poin");
        } else if (tvPodium2Name != null) {
            tvPodium2Name.setText("-");
            if (tvPodium2Points != null) tvPodium2Points.setText("0 Poin");
        }

        // Podium Juara 3
        if (list.size() > 2 && tvPodium3Name != null) {
            Ranking r3 = list.get(2);
            tvPodium3Name.setText(r3.getNama());
            if (tvPodium3Points != null) tvPodium3Points.setText(r3.getTotalPoin() + " Poin");
        } else if (tvPodium3Name != null) {
            tvPodium3Name.setText("-");
            if (tvPodium3Points != null) tvPodium3Points.setText("0 Poin");
        }

        // List Peringkat Lainnya (#4 ke bawah)
        if (rankingAdapter != null) {
            if (list.size() > 3) {
                List<Ranking> subList = new ArrayList<>(list.subList(3, list.size()));
                rankingAdapter.setRankingList(subList, 4);
                if (rvRanking != null) rvRanking.setVisibility(View.VISIBLE);
                if (tvEmptyRankingLainnya != null) tvEmptyRankingLainnya.setVisibility(View.GONE);
            } else {
                // Jika tidak ada pemain rank 4 ke atas
                rankingAdapter.setRankingList(new ArrayList<>(), 4);
                if (rvRanking != null) rvRanking.setVisibility(View.GONE);
                if (tvEmptyRankingLainnya != null) tvEmptyRankingLainnya.setVisibility(View.VISIBLE);
            }
        }
    }
}