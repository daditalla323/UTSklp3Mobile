package com.example.utsklp3mobile.ui.history;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.utsklp3mobile.R;
import com.example.utsklp3mobile.data.model.BattleHistory;
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

        holder.tvOpponent.setText("Lawan: " + (history.getLawan() != null ? history.getLawan() : "Bot"));
        holder.tvMatkul.setText(history.getMataKuliah() != null ? history.getMataKuliah() : "-");
        holder.tvDate.setText(history.getTanggal() != null ? history.getTanggal() : "");
        holder.tvResult.setText(history.getHasil() != null ? history.getHasil() : "-");
        holder.tvPoint.setText("+" + history.getPoinDidapat() + " Poin");

        if ("Menang".equalsIgnoreCase(history.getHasil())) {
            holder.tvResult.setBackgroundColor(Color.parseColor("#2E7D32")); // Green
        } else {
            holder.tvResult.setBackgroundColor(Color.parseColor("#C62828")); // Red
        }
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvOpponent, tvMatkul, tvDate, tvResult, tvPoint;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvOpponent = itemView.findViewById(R.id.tvHistoryOpponent);
            tvMatkul = itemView.findViewById(R.id.tvHistoryMatkul);
            tvDate = itemView.findViewById(R.id.tvHistoryDate);
            tvResult = itemView.findViewById(R.id.tvHistoryResult);
            tvPoint = itemView.findViewById(R.id.tvHistoryPoint);
        }
    }
}
