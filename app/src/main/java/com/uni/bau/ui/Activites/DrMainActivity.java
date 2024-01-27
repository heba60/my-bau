package com.uni.bau.ui.Activites;

import android.os.Bundle;
import android.provider.Settings;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.uni.bau.R;
import com.uni.bau.application.MyApplication;
import com.uni.bau.helpers.ApiConstants;
import com.uni.bau.helpers.SharedPreferencesHelper;
import com.uni.bau.managers.Api;
import com.uni.bau.managers.ApiCallResponse;
import com.uni.bau.managers.BusinessManager;
import com.uni.bau.managers.UniBusinessManager;
import com.uni.bau.models.RegisterDeviceParams;
import com.uni.bau.ui.Fragments.DrHomeFragment;
import com.uni.bau.ui.Fragments.MarksTabsFragment;
import com.uni.bau.ui.Fragments.MoreDrFragment;
import com.uni.bau.ui.Fragments.MoreFragment;
import com.uni.bau.ui.Fragments.StuCalenderFragment;
import com.uni.bau.utilities.CustomTastyToast;
import com.uni.bau.utilities.MyClient;
import com.uni.bau.utilities.Utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.Stack;

public class DrMainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    BottomNavigationView bottomNavigationView;
    private boolean doubleBackToExitPressedOnce = false;
    Fragment active = null;
    public Stack<String> titleStack = new Stack<>();
    DrHomeFragment homeFragment = null;
    MarksTabsFragment marksTabsFragment = null;
    MoreDrFragment moreFragment = null;
    private FragmentManager fragmentManager;
    public static DrMainActivity mainActivity;
    StuCalenderFragment stuCalenderFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.updateLanguage(this);
        Api.init(this);
        MyClient.reset();
        setContentView(R.layout.dr_main_activity);
        mainActivity = this;

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.home);
        if (moreFragment == null) {
            moreFragment = MoreDrFragment.newInstance();
            getSupportFragmentManager().beginTransaction().add(R.id.container, moreFragment, moreFragment.getClass().getName()).commit();
        }
        openFragment(moreFragment);
        setDeviceID();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.home:

                if (moreFragment == null) {
                    moreFragment = MoreDrFragment.newInstance();
                    getSupportFragmentManager().beginTransaction().add(R.id.container, moreFragment, moreFragment.getClass().getName()).commit();
                }
                openFragment(moreFragment);
                active = moreFragment;
                titleStack.push("");

                return true;
            case R.id.more:
                if (homeFragment == null) {
                    homeFragment = DrHomeFragment.newInstance();
                    getSupportFragmentManager().beginTransaction().add(R.id.container, homeFragment, homeFragment.getClass().getName()).commit();
                }
                openFragment(homeFragment);
                active = homeFragment;
                titleStack.push("");
                return true;
            case R.id.calenderss:
                if (stuCalenderFragment == null) {
                    stuCalenderFragment = StuCalenderFragment.newInstance();
                    getSupportFragmentManager().beginTransaction().add(R.id.container, stuCalenderFragment, stuCalenderFragment.getClass().getName()).commit();
                }
                openFragment(stuCalenderFragment);
                active = stuCalenderFragment;
                titleStack.push("");
                return true;
        }
        return false;
    }

    private void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (active == null) {
            transaction.show(fragment);
        } else {
            transaction.hide(active).show(fragment);
        }
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        try {
            {
                if (doubleBackToExitPressedOnce) {
                    finishAffinity();
                }
                this.doubleBackToExitPressedOnce = true;
                CustomTastyToast.makeText(DrMainActivity.this, getResources().getString(R.string.press_again_to_exit), CustomTastyToast.LENGTH_LONG, CustomTastyToast.WARNING).show();
            }
        } catch (Exception e) {
        }
    }


    private void setDeviceID() {

        String deviceId = "";
        String token = SharedPreferencesHelper.getSharedPreferencesString(DrMainActivity.this, ApiConstants.notificationKey, "");
        RegisterDeviceParams params = new RegisterDeviceParams();
        try {
            deviceId = Settings.Secure.getString(DrMainActivity.this.getContentResolver(), Settings.Secure.ANDROID_ID);
        } catch (Exception e) {
            deviceId = "";
        }
        params.setDeviceId(deviceId);
        try {
            params.setMobileDeviceId(new Utils().getUserData(DrMainActivity.this).getName().toString());
        } catch (Exception e) {
            params.setMobileDeviceId("");
            e.printStackTrace();
        }
        params.setOS("Android");
        params.setToken(token);
        new UniBusinessManager().registerDevice(DrMainActivity.this, params, new ApiCallResponse() {
            @Override
            public void onSuccess(Object responseObject, String responseMessage) {

            }

            @Override
            public void onFailure(String errorResponse) {

            }
        });
    }
}