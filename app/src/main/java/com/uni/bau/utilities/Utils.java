package com.uni.bau.utilities;

import static android.content.Context.INPUT_METHOD_SERVICE;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;

import com.google.android.gms.common.GoogleApiAvailability;
import com.uni.bau.helpers.LanguageHelper;
import com.uni.bau.helpers.SharedPrefConstants;
import com.uni.bau.helpers.SharedPreferencesHelper;
import com.uni.bau.models.CacheKeys;
import com.uni.bau.models.UniLoginModel;
import com.uni.bau.models.UserProfileModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.uni.bau.helpers.PrefConstant.captaValue;
import static com.uni.bau.helpers.PrefConstant.identifierValue;

public class Utils {


    public void setCaptaValue(String value, Context context) {
        SharedPreferencesHelper.putSharedPreferencesString(context, captaValue, value);
    }
    public String getCaptaValue(Context context)
    {
       return SharedPreferencesHelper.getSharedPreferencesString(context, captaValue, "");
    }


    public void setIdentifierValue(String userID, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor edit = preferences.edit();
        edit.putString(identifierValue, userID);
        edit.commit();
    }

    public static boolean isLogin(Context context) {
        UserProfileModel userLoginModel = new UserProfileModel();
        userLoginModel = (UserProfileModel) SharedPreferencesHelper.getSharedPreferencesObject(context, SharedPrefConstants.login, UserProfileModel.class);
        if (userLoginModel == null || userLoginModel.equals("")) {
            return false;
        } else {
            return true;
        }
    }


    public UserProfileModel getUserData(Context context) {
        UserProfileModel userLoginModel = new UserProfileModel();
        userLoginModel = (UserProfileModel) SharedPreferencesHelper.getSharedPreferencesObject(context, SharedPrefConstants.login, UserProfileModel.class);
        return userLoginModel;
    }

    public CacheKeys getKeys(Context context) {
        CacheKeys userLoginModel = new CacheKeys();
        userLoginModel = (CacheKeys) SharedPreferencesHelper.getSharedPreferencesObject(context, "cascheKeys", CacheKeys.class);
        return userLoginModel;
    }

    public String getUserToken(Context context) {
        UniLoginModel userLoginModel = new UniLoginModel();
        try {
            userLoginModel = (UniLoginModel) SharedPreferencesHelper.getSharedPreferencesObject(context, SharedPrefConstants.loginUni, UniLoginModel.class);
        } catch (Exception e) {
            return "";
        }
        if (userLoginModel == null) {
            return "";
        }
        return "Bearer " + userLoginModel.getData().getToken();
    }

    public UniLoginModel getUNILOGIN(Context context) {
        UniLoginModel userLoginModel = new UniLoginModel();
        userLoginModel = (UniLoginModel) SharedPreferencesHelper.getSharedPreferencesObject(context, SharedPrefConstants.loginUni, UniLoginModel.class);
        return userLoginModel;


    }

    public void updateLangauge(Context context) {
        Locale locale = new Locale(LanguageHelper.getCurrentLanguage(context));
        Locale.setDefault(locale);
        Resources resources = context.getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);
        resources.updateConfiguration(config, resources.getDisplayMetrics());

    }


    public static Typeface SetTFace(Context context) {
        Typeface font;
        font = Typeface.createFromAsset(context.getAssets(), "normal.otf");
        return font;
    }

    public String getDate(long time) {
        SimpleDateFormat sdf = null;
        try {
            sdf = new SimpleDateFormat("dd/MM/yyyy");
        } catch (Exception e) {
            return "";
        }
        try {
            sdf.format(new Date(time));
            return sdf.format(time);
        } catch (Exception e) {
            return "";
        }


    }

    public void hideKeyboardOnSubmit(Activity activity) {
        if (activity.getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
            Log.e("HIDE_KEY", "hideKeyboardOnSubmit: ");
        }
    }

    public boolean isGMSAvailable(Context context) {
        try {
            GoogleApiAvailability gms = GoogleApiAvailability.getInstance();
            int isGMS = gms.isGooglePlayServicesAvailable(context);
            return isGMS == com.google.android.gms.common.ConnectionResult.SUCCESS;
        } catch (Exception e) {
            return true;
        }

    }
}
