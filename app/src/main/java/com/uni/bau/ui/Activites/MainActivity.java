package com.uni.bau.ui.Activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.uni.bau.R;
import com.uni.bau.application.MyApplication;
import com.uni.bau.helpers.ApiConstants;
import com.uni.bau.helpers.SharedPrefConstants;
import com.uni.bau.helpers.SharedPreferencesHelper;
import com.uni.bau.managers.Api;
import com.uni.bau.managers.ApiCallResponse;
import com.uni.bau.managers.BusinessManager;
import com.uni.bau.managers.UniBusinessManager;
import com.uni.bau.models.RegisterDeviceParams;
import com.uni.bau.models.StudentData;
import com.uni.bau.models.UniLoginModel;
import com.uni.bau.models.UserProfileModel;
import com.uni.bau.ui.Fragments.HomeFragment;
import com.uni.bau.ui.Fragments.MoreFragment;
import com.uni.bau.ui.Fragments.MarksTabsFragment;
import com.uni.bau.ui.Fragments.StuCalenderFragment;
import com.uni.bau.utilities.CustomTastyToast;
import com.uni.bau.utilities.MyClient;
import com.uni.bau.utilities.ProgressUtil;
import com.uni.bau.utilities.Utils;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.Stack;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    BottomNavigationView bottomNavigationView;
    private boolean doubleBackToExitPressedOnce = false;
    Fragment active = null;
    public Stack<String> titleStack = new Stack<>();
    HomeFragment homeFragment = null;
    MarksTabsFragment marksTabsFragment = null;
    StuCalenderFragment stuCalenderFragment = null;
    MoreFragment moreFragment = null;
    private FragmentManager fragmentManager;
    public static MainActivity mainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.updateLanguage(this);
        Api.init(this);
        MyClient.reset();
        setContentView(R.layout.activity_main);
        mainActivity = this;

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.home);
        if (moreFragment == null) {
            moreFragment = MoreFragment.newInstance();
            getSupportFragmentManager().beginTransaction().add(R.id.container, moreFragment, moreFragment.getClass().getName()).commit();
        }
        openFragment(moreFragment);
        getCaptcha();
        setDeviceID();
    }

    private void postLogin() {
//        ProgressUtil.INSTANCE.showLoading(MainActivity.this);
        new BusinessManager().doLogin(MainActivity.this, new Utils().getUserData(this).getUsername().toString(), new Utils().getUserData(this).getPassword().toString(), new ApiCallResponse() {
            @Override
            public void onSuccess(Object responseObject, String responseMessage) {
                ProgressUtil.INSTANCE.hideLoading();
               // getStudentCall();
            }

            @Override
            public void onFailure(String errorResponse) {
                ProgressUtil.INSTANCE.hideLoading();
            }
        });
    }

    private void getStudentCall() {

        new BusinessManager().getStudent(this, new ApiCallResponse() {
            @Override
            public void onSuccess(Object responseObject, String responseMessage) {
                StudentData uniLoginModel = (StudentData) responseObject;
                UserProfileModel userLoginModel = new UserProfileModel();
                userLoginModel = (UserProfileModel) SharedPreferencesHelper.getSharedPreferencesObject(MainActivity.this, SharedPrefConstants.login, UserProfileModel.class);

                try {
                    String name = uniLoginModel.getArabicName().toString();
                    userLoginModel.setName(name);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    String major = uniLoginModel.getSpecificationId();
                    userLoginModel.setMajor(major);
                } catch (Exception e) {
                    userLoginModel.setMajor("app");
                    e.printStackTrace();
                }
                try {
                    String facility = uniLoginModel.getStatus() + "";
                    userLoginModel.setFacility(facility);
                } catch (Exception e) {
                    userLoginModel.setFacility("app");
                    e.printStackTrace();
                }



              //  SharedPreferencesHelper.putSharedPreferencesObject(MainActivity.this, SharedPrefConstants.login, userLoginModel);
            }

            @Override
            public void onFailure(String errorResponse) {

            }
        });
    }

    private void getCaptcha() {
        new BusinessManager().captchaAPI(MainActivity.this, new ApiCallResponse() {
            @Override
            public void onSuccess(Object responseObject, String responseMessage) {
                try {
                    postLogin();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String errorResponse) {
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.home:
                if (moreFragment == null) {
                    moreFragment = MoreFragment.newInstance();
                    getSupportFragmentManager().beginTransaction().add(R.id.container, moreFragment, moreFragment.getClass().getName()).commit();
                }
                openFragment(moreFragment);
                active = moreFragment;
                titleStack.push("");
                return true;

            case R.id.mark:
                if (marksTabsFragment == null) {
                    marksTabsFragment = MarksTabsFragment.newInstance();
                    getSupportFragmentManager().beginTransaction().add(R.id.container, marksTabsFragment, marksTabsFragment.getClass().getName()).commit();
                }
                openFragment(marksTabsFragment);
                active = marksTabsFragment;
                titleStack.push("");
                return true;

            case R.id.news:
                if (homeFragment == null) {
                    homeFragment = HomeFragment.newInstance();
                    getSupportFragmentManager().beginTransaction().add(R.id.container, homeFragment, homeFragment.getClass().getName()).commit();
                }
                openFragment(homeFragment);
                active = homeFragment;
                titleStack.push("");
                return true;

            case R.id.dates:
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
                CustomTastyToast.makeText(MainActivity.this, getResources().getString(R.string.press_again_to_exit), CustomTastyToast.LENGTH_LONG, CustomTastyToast.WARNING).show();
            }
        } catch (Exception e) {
        }
    }


    private void setDeviceID() {

        String deviceId = "";
        String token = SharedPreferencesHelper.getSharedPreferencesString(MainActivity.this, ApiConstants.notificationKey, "");
        RegisterDeviceParams params = new RegisterDeviceParams();
        try {
            deviceId = Settings.Secure.getString(MainActivity.this.getContentResolver(), Settings.Secure.ANDROID_ID);
        } catch (Exception e) {
            deviceId = "";
        }
        params.setDeviceId(deviceId);
        try {
            params.setMobileDeviceId(new Utils().getUserData(MainActivity.this).getName().toString());
        } catch (Exception e) {
            params.setMobileDeviceId("");
            e.printStackTrace();
        }
        params.setOS("Android");
        params.setToken(token);
        new UniBusinessManager().registerDevice(MainActivity.this, params, new ApiCallResponse() {
            @Override
            public void onSuccess(Object responseObject, String responseMessage) {

            }

            @Override
            public void onFailure(String errorResponse) {

            }
        });
    }
}