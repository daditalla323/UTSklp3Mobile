package com.example.utsklp3mobile.ui.history;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.utsklp3mobile.R;
import com.example.utsklp3mobile.viewmodel.HistoryViewModel;

public class HistoryFragment extends Fragment {

    private HistoryViewModel historyViewModel;
    private RecyclerView rvHistory;
    private HistoryAdapter historyAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvHistory = view.findViewById(R.id.rvHistory);
        historyAdapter = new HistoryAdapter();
        rvHistory.setLayoutManager(new LinearLayoutManager(requireContext()));
        rvHistory.setAdapter(historyAdapter);

        historyViewModel = new ViewModelProvider(this).get(HistoryViewModel.class);
        historyViewModel.getHistoryList().observe(getViewLifecycleOwner(), list -> {
            if (list != null) {
                historyAdapter.setHistoryList(list);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (historyViewModel != null) {
            historyViewModel.loadHistory();
        }
    }
}
