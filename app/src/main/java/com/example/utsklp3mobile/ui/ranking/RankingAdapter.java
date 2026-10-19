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
        holder.tvName.setText(ranking.getNama());
        holder.tvPoints.setText(ranking.getTotalPoin() + " Poin");
    }

    @Override
    public int getItemCount() {
        return rankingList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvPoints;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvRankingName);
            tvPoints = itemView.findViewById(R.id.tvRankingPoints);
        }
    }
}