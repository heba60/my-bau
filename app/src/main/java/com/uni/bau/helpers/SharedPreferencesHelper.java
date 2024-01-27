package com.uni.bau.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Created by moayed on 9/26/17.
 */

public class SharedPreferencesHelper {


    public static void putSharedPreferencesObject(Context context, String key, Object shared_object) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor edit = preferences.edit();
        edit.remove(key).commit();
        Gson gson = new Gson();
        String json = gson.toJson(shared_object); // myObject - instance of MyObject
        edit.putString(key, json);
        edit.commit();
    }

    public static Object getSharedPreferencesObject(Context context, String key, Class neededClass) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = preferences.getString(key, "");
        Object obj = gson.fromJson(json, neededClass);
        return obj;
    }


    public static void putSharedPreferencesString(Context context, String key, String val) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor edit = preferences.edit();
        edit.putString(key, val);
        edit.commit();
    }

    public static String getSharedPreferencesString(Context context, String key, String _default) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, _default);
    }


    //    public static void saveToWishList(Context context, String key, List<MainCatigoryModel.Item> list) {
//        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
//        SharedPreferences.Editor edit = preferences.edit();
//        Gson gson = new Gson();
//        String jsonFavorites = gson.toJson(list);
//        edit.putString(key, jsonFavorites);
//        edit.commit();
//    }
//
//    public static void saveBookmarkList(Context context, String key, List<MainCatigoryModel.Item> list) {
//        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
//        SharedPreferences.Editor edit = preferences.edit();
//        Gson gson = new Gson();
//        String jsonFavorites = gson.toJson(list);
//        edit.putString(key, jsonFavorites);
//        edit.commit();
//    }
//
//
//    public static ArrayList<MainCatigoryModel.Item> getBookmarkList(Context context, String key) {
//        List<MainCatigoryModel.Item> expertModels;
//        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
//
//        if (preferences.contains(key)) {
//            String json = preferences.getString(key, null);
//            Gson gson = new Gson();
//            MainCatigoryModel.Item[] expertModels1 = gson.fromJson(json,
//                    MainCatigoryModel.Item[].class);
//            expertModels = Arrays.asList(expertModels1);
//            expertModels = new ArrayList<MainCatigoryModel.Item>(expertModels);
//        } else
//            return null;
//
//        return (ArrayList<MainCatigoryModel.Item>) expertModels;
//    }
//
//

    public static void saveDeletedItems(Context context, String key, List<String> list) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor edit = preferences.edit();
        Gson gson = new Gson();
        String jsonFavorites = gson.toJson(list);
        edit.putString(key, jsonFavorites);
        edit.commit();
    }

    public static ArrayList<String> getDeletedItems(Context context, String key) {
        List<String> expertModels;
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);

        if (preferences.contains(key)) {
            String json = preferences.getString(key, null);
            Gson gson = new Gson();
            String[] expertModels1 = gson.fromJson(json,
                    String[].class);
            expertModels = Arrays.asList(expertModels1);
            expertModels = new ArrayList<String>(expertModels);
        } else
            return null;

        return (ArrayList<String>) expertModels;
    }




    public static boolean clear(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.edit()
                .clear()
                .commit();

    }


}
