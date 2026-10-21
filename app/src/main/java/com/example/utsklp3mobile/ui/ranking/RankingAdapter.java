package com.example.utsklp3mobile.ui.ranking;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.utsklp3mobile.R;
import com.example.utsklp3mobile.data.model.Ranking;
import java.util.ArrayList;
import java.util.List;

public class RankingAdapter extends RecyclerView.Adapter<RankingAdapter.ViewHolder> {

    private List<Ranking> rankingList = new ArrayList<>();
    private int startRank = 4;

    public void setRankingList(List<Ranking> list, int startRank) {
        this.rankingList = list != null ? list : new ArrayList<>();
        this.startRank = startRank;
        notifyDataSetChanged();
    }

    public void setRankingList(List<Ranking> list) {
        setRankingList(list, 4);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ranking, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Ranking ranking = rankingList.get(position);
        if (holder.tvRankNumber != null) {
            holder.tvRankNumber.setText(String.valueOf(position + startRank));
        }
        if (holder.tvPlayerName != null) {
            holder.tvPlayerName.setText(ranking.getNama() != null ? ranking.getNama() : "Pemain");
        }
        if (holder.tvPlayerMatkul != null) {
            holder.tvPlayerMatkul.setText(ranking.getJumlahMenang() + " Kemenangan");
        }
        if (holder.tvScore != null) {
            holder.tvScore.setText(String.valueOf(ranking.getTotalPoin()));
        }
    }

    @Override
    public int getItemCount() {
        return rankingList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvRankNumber, tvPlayerName, tvPlayerMatkul, tvScore;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRankNumber = itemView.findViewById(R.id.tvRankNumber);
            tvPlayerName = itemView.findViewById(R.id.tvPlayerName);
            tvPlayerMatkul = itemView.findViewById(R.id.tvPlayerMatkul);
            tvScore = itemView.findViewById(R.id.tvScore);
        }
    }
}