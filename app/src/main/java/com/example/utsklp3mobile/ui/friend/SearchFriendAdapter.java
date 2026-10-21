package com.example.utsklp3mobile.ui.friend;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.utsklp3mobile.R;
import com.example.utsklp3mobile.data.model.User;
import com.example.utsklp3mobile.data.repository.UserRepository;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class SearchFriendAdapter extends RecyclerView.Adapter<SearchFriendAdapter.ViewHolder> {

    private List<User> userList = new ArrayList<>();
    private final String currentUserName;
    private final UserRepository userRepository;
    private final Context context;

    private final int[] avatarBgs = new int[]{
            R.drawable.bg_circle_avatar_1,
            R.drawable.bg_circle_avatar_2,
            R.drawable.bg_circle_avatar_3,
            R.drawable.bg_circle_avatar_4
    };

    public SearchFriendAdapter(Context context, String currentUserName) {
        this.context = context;
        this.currentUserName = currentUserName;
        this.userRepository = new UserRepository(context);
    }

    public void setUserList(List<User> list) {
        this.userList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_friend, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = userList.get(position);

        holder.tvSearchUserName.setText(user.getNama() != null ? user.getNama() : "Pemain");
        holder.tvSearchUserPoints.setText(user.getTotalPoin() + " Pts • Active");

        // Set avatar background
        holder.ivSearchUserAvatar.setBackgroundResource(avatarBgs[position % avatarBgs.length]);

        // Cek status berteman
        boolean isFriend = userRepository.isFriend(currentUserName, user.getNama());
        updateButtonState(holder.btnAddFriend, isFriend);

        holder.btnAddFriend.setOnClickListener(v -> {
            boolean success = userRepository.addFriend(currentUserName, user.getNama());
            if (success) {
                Toast.makeText(context, "Berhasil menambahkan " + user.getNama() + " sebagai teman!", Toast.LENGTH_SHORT).show();
                updateButtonState(holder.btnAddFriend, true);
            } else {
                Toast.makeText(context, user.getNama() + " sudah ada di daftar teman Anda.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateButtonState(MaterialButton button, boolean isFriend) {
        if (isFriend) {
            button.setText("✔ Berteman");
            button.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#E2E8F0")));
            button.setTextColor(Color.parseColor("#475569"));
            button.setEnabled(false);
        } else {
            button.setText("+ Tambah");
            button.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.primary)));
            button.setTextColor(Color.WHITE);
            button.setEnabled(true);
        }
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivSearchUserAvatar;
        TextView tvSearchUserName, tvSearchUserPoints;
        MaterialButton btnAddFriend;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivSearchUserAvatar = itemView.findViewById(R.id.ivSearchUserAvatar);
            tvSearchUserName = itemView.findViewById(R.id.tvSearchUserName);
            tvSearchUserPoints = itemView.findViewById(R.id.tvSearchUserPoints);
            btnAddFriend = itemView.findViewById(R.id.btnAddFriend);
        }
    }
}