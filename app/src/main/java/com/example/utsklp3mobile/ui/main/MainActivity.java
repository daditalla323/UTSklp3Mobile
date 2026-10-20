package com.example.utsklp3mobile.ui.main;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.utsklp3mobile.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private String userName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getIntent() != null && getIntent().hasExtra("USER_NAME")) {
            userName = getIntent().getStringExtra("USER_NAME");
        }

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        // Pasang adapter ke ViewPager2
        ViewPagerAdapter adapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(adapter);

        // Hubungkan TabLayout dengan ViewPager2 dan pasang custom view ikon
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            View customView = LayoutInflater.from(this).inflate(R.layout.custom_tab_item, null);
            ImageView ivIcon = customView.findViewById(R.id.ivTabIcon);

            switch (position) {
                case 0:
                    ivIcon.setImageResource(R.drawable.ic_nav_home);
                    break;
                case 1:
                    ivIcon.setImageResource(R.drawable.ic_nav_ranking);
                    break;
                case 2:
                    ivIcon.setImageResource(R.drawable.ic_nav_history);
                    break;
            }
            tab.setCustomView(customView);
        }).attach();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                updateTabState(tab, true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                updateTabState(tab, false);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

        // Inisialisasi status terpilih untuk custom view tab
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            if (tab != null) {
                updateTabState(tab, i == viewPager.getCurrentItem());
            }
        }
    }

    private void updateTabState(TabLayout.Tab tab, boolean isSelected) {
        if (tab != null && tab.getCustomView() != null) {
            ImageView ivIcon = tab.getCustomView().findViewById(R.id.ivTabIcon);
            if (ivIcon != null) {
                ivIcon.setSelected(isSelected);
                if (isSelected) {
                    ivIcon.setColorFilter(Color.WHITE);
                } else {
                    ivIcon.setColorFilter(Color.parseColor("#293681"));
                }
            }
        }
    }

    public String getUserName() {
        return userName != null && !userName.isEmpty() ? userName : "Pemain";
    }

    public void selectTab(int position) {
        if (viewPager != null) {
            viewPager.setCurrentItem(position, true);
        }
    }
}
