package com.example.utsklp3mobile.ui.history;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.utsklp3mobile.R;
import com.example.utsklp3mobile.Data.model.BattleHistory;
import java.util.ArrayList;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private List<BattleHistory> historyList = new ArrayList<>();

    public void setHistoryList(List<BattleHistory> list) {
        this.historyList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BattleHistory history = historyList.get(position);
        holder.tvOpponent.setText("Lawan: " + history.getLawan());
        holder.tvResult.setText(history.getHasil());
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvOpponent, tvResult;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvOpponent = itemView.findViewById(R.id.tvHistoryOpponent);
            tvResult = itemView.findViewById(R.id.tvHistoryResult);
        }
    }
}