package com.arcadio.triplover.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.List;

public class PreferencesHelpers {

    private static final String SHARED_PREFS = "sharedPrefs";
    public static final String SEARCH_QUERY = "query_flight"; //I want to save this string

    public String text;


    public static void saveStringData(Context context, String tag, String data) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(tag, data);
        editor.apply();
    }

    public static String loadStringData(Context context, String tag, String defValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        String text = sharedPreferences.getString(tag, defValue);
        return text;
    }

    public static String getToken(Context context) {
        KLog.w("get Pref Token: " + PreferencesHelpers.loadStringData(context, Constants.USER_TOKEN, ""));
        return PreferencesHelpers.loadStringData(context, Constants.USER_TOKEN, "");
    }

    public static void setToken(Context context, String token) {
        KLog.w("Pref Token: " + token);
        saveStringData(context, Constants.USER_TOKEN, token);
    }

    public static void saveBooleanData(Context context, String tag, boolean flag) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putBoolean(tag, flag);
        editor.apply();

    }

    public static boolean loadBooleanData(Context context, String tag, boolean defValue) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        boolean flag = sharedPreferences.getBoolean(tag, defValue);
        return flag;

    }

    public static void savePassemger(List<String> sKey, Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor mEdit1 = sp.edit();
        mEdit1.putInt("Status_size", sKey.size());
        for (int i = 0; i < sKey.size(); i++) {
            mEdit1.remove("Status_" + i);
            mEdit1.putString("Status_" + i, sKey.get(i));
        }
        mEdit1.apply();
    }

    public static List<String> loadPassenger(Context context) {
        List<String> sKey = new ArrayList<>();
        SharedPreferences mSharedPreference1 = PreferenceManager.getDefaultSharedPreferences(context);
        int size = mSharedPreference1.getInt("Status_size", 0);

        for (int i = 0; i < size; i++) {
            sKey.add(mSharedPreference1.getString("Status_" + i, null));
        }
        return sKey;
    }

}
