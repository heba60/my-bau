package com.uni.bau.ui.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.uni.bau.R;


public class MarksTabsFragment extends Fragment {
    Activity activity;
    View view;
    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;
    SwipeRefreshLayout swipeRefresh;
    public MarksTabsFragment() {
    }

    public static MarksTabsFragment newInstance() {
        return new MarksTabsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_marks_tabs, container, false);
        viewPager = view.findViewById(R.id.tab_viewpager);
        tabLayout = view.findViewById(R.id.tab_tablayout);
        viewPagerAdapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
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
                fragment = new MarksFragment();
            else if (position == 1)
                fragment = new AchievementsFragment();

            return fragment;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            String title = null;
            if (position == 0)
                title = getString(R.string.mark);
            else if (position == 1)
                title = "خطة الطالب";
            return title;
        }
    }

}