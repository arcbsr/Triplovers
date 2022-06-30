package com.arcadio.triplover.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.arcadio.triplover.models.uidecoration.UiDecoration;

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

    public static void saveLongData(Context context, String tag, long val) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putLong(tag, val);
        editor.apply();

    }

    public static long loadLongData(Context context, String tag, long defValue) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        long val = sharedPreferences.getLong(tag, defValue);
        return val;

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

    public static void saveUiDecorationData(Context context, String data) {
        //KLog.w(data);
        saveStringData(context, "ui_decoration", data);
        saveLongData(context, "ui_decoration_time", System.currentTimeMillis());
    }

    public static boolean isUiDecorationIsExpired(Context context) {
        long lastSaved = loadLongData(context, "ui_decoration_time", 0);
        long twentyFourHours = 24 * 60 * 60 * 1000;
        if (lastSaved == 0 || (System.currentTimeMillis() - lastSaved) >= twentyFourHours) {
            return true;
        }
        return false;
    }

    public static UiDecoration loadUiDecoration(Context context) {
        String val = loadStringData(context, "ui_decoration", "");
        if (val != null && !val.isEmpty()) {
            try {
                return Utils.getGson().fromJson(val, UiDecoration.class);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
        return new UiDecoration();

    }

    public static void removeData(Context context, String tag) {
        SharedPreferences settings = context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        settings.edit().remove(tag).commit();
        // clear all pref data...
//        SharedPreferences settings = context.getSharedPreferences("PreferencesName", Context.MODE_PRIVATE);
//        settings.edit().clear().commit();
    }

}
