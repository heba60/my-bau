package com.uni.bau.ui.Activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.uni.bau.R;
import com.uni.bau.ui.Fragments.AttachmentImageFragment;

public class AttachmentActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;
    String lecID = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attachment);
        viewPager = findViewById(R.id.tab_viewpager);
        tabLayout = findViewById(R.id.tab_tablayout);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        lecID = getIntent().getStringExtra("lecID");
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
            {
                Bundle bundle = new Bundle();
                bundle.putString("lecID", lecID);
                bundle.putString("typeID", "2");
                AttachmentImageFragment fragobj = new AttachmentImageFragment();
                fragobj.setArguments(bundle);
                return fragobj;
            }
            else if (position == 1){
                Bundle bundle = new Bundle();
                bundle.putString("lecID",lecID);
                bundle.putString("typeID", "1");
                AttachmentImageFragment fragobj = new AttachmentImageFragment();
                fragobj.setArguments(bundle);
                return fragobj;
            }

            else if (position == 2){
                Bundle bundle = new Bundle();
                bundle.putString("lecID",lecID);
                bundle.putString("typeID", "3");
                AttachmentImageFragment fragobj = new AttachmentImageFragment();
                fragobj.setArguments(bundle);
                return fragobj;
            }


            return fragment;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            String title = null;
            if (position == 0)
                title = "ملفات";
            else if (position == 1)
                title = "صور";
            else if (position == 2)
                title = "روابط";
            return title;
        }
    }

}