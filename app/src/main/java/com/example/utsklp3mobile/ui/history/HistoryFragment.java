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
import com.example.utsklp3mobile.ui.main.MainActivity;
import com.example.utsklp3mobile.viewmodel.HistoryViewModel;

public class HistoryFragment extends Fragment {

    private HistoryViewModel historyViewModel;
    private RecyclerView rvHistory;
    private View layoutEmptyHistory;
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
        layoutEmptyHistory = view.findViewById(R.id.layoutEmptyHistory);

        historyAdapter = new HistoryAdapter();
        if (rvHistory != null) {
            rvHistory.setLayoutManager(new LinearLayoutManager(requireContext()));
            rvHistory.setAdapter(historyAdapter);
        }

        String userName = "Pemain";
        if (getActivity() instanceof MainActivity) {
            userName = ((MainActivity) getActivity()).getUserName();
        } else if (getActivity() != null && getActivity().getIntent() != null) {
            String intentUser = getActivity().getIntent().getStringExtra("USER_NAME");
            if (intentUser != null && !intentUser.isEmpty()) {
                userName = intentUser;
            }
        }

        historyViewModel = new ViewModelProvider(this).get(HistoryViewModel.class);
        historyViewModel.loadHistory(userName);
        historyViewModel.getHistoryList().observe(getViewLifecycleOwner(), list -> {
            if (list != null && !list.isEmpty()) {
                if (layoutEmptyHistory != null) layoutEmptyHistory.setVisibility(View.GONE);
                if (rvHistory != null) {
                    rvHistory.setVisibility(View.VISIBLE);
                    historyAdapter.setHistoryList(list);
                }
            } else {
                if (layoutEmptyHistory != null) layoutEmptyHistory.setVisibility(View.VISIBLE);
                if (rvHistory != null) rvHistory.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (historyViewModel != null) {
            String userName = "Pemain";
            if (getActivity() instanceof MainActivity) {
                userName = ((MainActivity) getActivity()).getUserName();
            } else if (getActivity() != null && getActivity().getIntent() != null) {
                String intentUser = getActivity().getIntent().getStringExtra("USER_NAME");
                if (intentUser != null && !intentUser.isEmpty()) {
                    userName = intentUser;
                }
            }
            historyViewModel.loadHistory(userName);
        }
    }
}