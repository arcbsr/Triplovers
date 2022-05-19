package com.arcadio.triplover.utils;

import android.content.Context;
import android.content.SharedPreferences;

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

}
