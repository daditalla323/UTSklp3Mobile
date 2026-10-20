package com.example.utsklp3mobile.ui.ranking;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.utsklp3mobile.R;

public class RankingFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ranking, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Inisialisasi komponen
        TextView btnTerbaru = view.findViewById(R.id.btnFilterTerbaru);
        TextView btnSemua = view.findViewById(R.id.btnFilterSemua);

        // Layout yang akan ditampilkan/disembunyikan
        View layoutTerbaru = view.findViewById(R.id.layoutTerbaru);
        View layoutSemua = view.findViewById(R.id.layoutSemua);

        // Logika saat tombol "Terbaru" diklik
        btnTerbaru.setOnClickListener(v -> {
            // Sembunyikan tampilan Semua, tampilkan tampilan Terbaru
            layoutSemua.setVisibility(View.GONE);
            layoutTerbaru.setVisibility(View.VISIBLE);

            // Ubah warna tombol (opsional, agar terlihat aktif)
            btnTerbaru.setAlpha(1.0f);
            btnSemua.setAlpha(0.6f);
        });

        // Logika saat tombol "Semua" diklik
        btnSemua.setOnClickListener(v -> {
            // Sembunyikan tampilan Terbaru, tampilkan tampilan Semua
            layoutTerbaru.setVisibility(View.GONE);
            layoutSemua.setVisibility(View.VISIBLE);

            // Ubah warna tombol (opsional, agar terlihat aktif)
            btnTerbaru.setAlpha(0.6f);
            btnSemua.setAlpha(1.0f);
        });
    }
}