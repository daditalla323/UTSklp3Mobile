package com.example.utsklp3mobile.ui.friend;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.utsklp3mobile.R;
import com.example.utsklp3mobile.data.model.User;
import com.example.utsklp3mobile.data.repository.UserRepository;

import java.util.List;

public class SearchFriendActivity extends AppCompatActivity {

    private ImageButton btnBack, btnClearSearch;
    private EditText etSearchQuery;
    private RecyclerView rvSearchResults;
    private LinearLayout llEmptyState;
    private TextView tvEmptyTitle, tvEmptyDesc;

    private UserRepository userRepository;
    private SearchFriendAdapter adapter;
    private String currentUserName = "Pemain";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_friend);

        if (getIntent() != null && getIntent().hasExtra("USER_NAME")) {
            currentUserName = getIntent().getStringExtra("USER_NAME");
        }

        userRepository = new UserRepository(this);

        btnBack = findViewById(R.id.btnBack);
        btnClearSearch = findViewById(R.id.btnClearSearch);
        etSearchQuery = findViewById(R.id.etSearchQuery);
        rvSearchResults = findViewById(R.id.rvSearchResults);
        llEmptyState = findViewById(R.id.llEmptyState);
        tvEmptyTitle = findViewById(R.id.tvEmptyTitle);
        tvEmptyDesc = findViewById(R.id.tvEmptyDesc);

        btnBack.setOnClickListener(v -> finish());

        btnClearSearch.setOnClickListener(v -> etSearchQuery.setText(""));

        adapter = new SearchFriendAdapter(this, currentUserName);
        rvSearchResults.setLayoutManager(new LinearLayoutManager(this));
        rvSearchResults.setAdapter(adapter);

        etSearchQuery.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String query = s.toString().trim();
                if (!query.isEmpty()) {
                    btnClearSearch.setVisibility(View.VISIBLE);
                    performSearch(query);
                } else {
                    btnClearSearch.setVisibility(View.GONE);
                    showDefaultEmptyState();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        showDefaultEmptyState();
    }

    private void performSearch(String query) {
        List<User> results = userRepository.searchUsersByNickname(query, currentUserName);
        if (results != null && !results.isEmpty()) {
            llEmptyState.setVisibility(View.GONE);
            rvSearchResults.setVisibility(View.VISIBLE);
            adapter.setUserList(results);
        } else {
            rvSearchResults.setVisibility(View.GONE);
            llEmptyState.setVisibility(View.VISIBLE);
            tvEmptyTitle.setText("Tidak Ditemukan");
            tvEmptyDesc.setText("Tidak ada pemain dengan nickname \"" + query + "\"");
        }
    }

    private void showDefaultEmptyState() {
        rvSearchResults.setVisibility(View.GONE);
        llEmptyState.setVisibility(View.VISIBLE);
        tvEmptyTitle.setText("Cari Teman Baru");
        tvEmptyDesc.setText("Ketik nickname teman pada kolom pencarian di atas untuk menemukan pemain lain.");
    }
}