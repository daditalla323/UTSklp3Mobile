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

    public void setRankingList(List<Ranking> list) {
        this.rankingList = list;
        notifyDataSetChanged();
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
            holder.tvRankNumber.setText(String.valueOf(position + 4));
        }
        if (holder.tvPlayerName != null) {
            holder.tvPlayerName.setText(ranking.getNama() != null ? ranking.getNama() : "Player");
        }
        if (holder.tvScore != null) {
            holder.tvScore.setText(ranking.getTotalPoin() + " Poin");
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