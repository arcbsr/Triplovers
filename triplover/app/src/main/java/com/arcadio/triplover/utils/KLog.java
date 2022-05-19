package com.arcadio.triplover.utils;

import android.util.Log;

public class KLog {
    private static final String LOG_TAG = "kioskeLog";

    public static void w(String msg) {
        if (!Settings.SHOW_LOG) {
            return;
        }
        Log.w(LOG_TAG, msg);
    }
}
