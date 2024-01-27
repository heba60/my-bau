package com.uni.bau.ui.Activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.tabs.TabLayout;
import com.uni.bau.R;
import com.uni.bau.application.MyApplication;
import com.uni.bau.ui.Fragments.AnouncementFragment;
import com.uni.bau.ui.Fragments.NewsFragment;

public class NewsAnnouncementActivity extends AppCompatActivity {
    Activity activity;

    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.updateLanguage(this);
        setContentView(R.layout.activity_news_announcement_);
        viewPager = findViewById(R.id.tab_viewpager);
        tabLayout = findViewById(R.id.tab_tablayout);

        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);

        tabLayout.setupWithViewPager(viewPager);

        findViewById(R.id.ivBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public class ViewPagerAdapter
            extends FragmentPagerAdapter {
        public ViewPagerAdapter(
                @NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            if (position == 0)
                fragment = new NewsFragment();
            else if (position == 1)
                fragment = new AnouncementFragment();

            return fragment;
        }

        @Override
        public int getCount() {
            return 1;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            String title = null;
            if (position == 0)
                title = getString(R.string.news_);
            else if (position == 1)
                title = getString(R.string.annoucment);
            return title;
        }
    }
}