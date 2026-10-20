package com.example.utsklp3mobile.ui.profile;

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

        String resultText = history.getHasil() != null ? history.getHasil().toUpperCase() : "N/A";
        holder.tvResult.setText(resultText);

        if ("MENANG".equalsIgnoreCase(history.getHasil()) || "WIN".equalsIgnoreCase(history.getHasil())) {
            holder.tvResult.setTextColor(Color.parseColor("#1B5E20"));
            holder.tvResult.setBackgroundResource(R.drawable.bg_circle_accent_soft);
        } else {
            holder.tvResult.setTextColor(Color.parseColor("#B71C1C"));
            holder.tvResult.setBackgroundResource(R.drawable.bg_circle_accent_bright);
        }

        holder.tvMatkul.setText(history.getMataKuliah() != null ? history.getMataKuliah() : "Quiz Arena");

        String opponentAndDate = "vs " + (history.getLawan() != null ? history.getLawan() : "Bot");
        if (history.getTanggal() != null && !history.getTanggal().isEmpty()) {
            opponentAndDate += " • " + history.getTanggal();
        }
        holder.tvOpponent.setText(opponentAndDate);

        holder.tvPoints.setText("+" + history.getPoinDidapat() + " Pts");
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvResult, tvMatkul, tvOpponent, tvPoints;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvResult = itemView.findViewById(R.id.tvHistoryResult);
            tvMatkul = itemView.findViewById(R.id.tvHistoryMatkul);
            tvOpponent = itemView.findViewById(R.id.tvHistoryOpponent);
            tvPoints = itemView.findViewById(R.id.tvHistoryPoints);
        }
    }
}