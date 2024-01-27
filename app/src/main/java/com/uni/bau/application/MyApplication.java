package com.uni.bau.application;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.preference.PreferenceManager;


import com.google.firebase.FirebaseApp;

import java.util.Locale;

public class MyApplication extends Application {
    private static Context context;
    private static MyApplication mInstance;

    private SharedPreferences preferences;

    public void onCreate() {
        super.onCreate();
        MyApplication.context = getApplicationContext();
        mInstance = this;
        FirebaseApp.initializeApp(context);
        updateLanguage(context);

    }

    public synchronized SharedPreferences getPreferences() {
        if (preferences == null)
            preferences = PreferenceManager.getDefaultSharedPreferences(this);
        return preferences;
    }

    public static Context getAppContext() {
        return MyApplication.context;
    }

    public static MyApplication getInstance() {
        return mInstance;
    }

    public static void updateLanguage(Context ctx) {
        updateLanguage(ctx, "ar");
    }


    public static void updateLanguage(Context ctx, String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = ctx.getResources().getConfiguration();
        config.setLocale(locale);
        ctx.createConfigurationContext(config);
        ctx.getResources().updateConfiguration(config, ctx.getResources().getDisplayMetrics());

    }
}
