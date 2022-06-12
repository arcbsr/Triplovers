package com.arcadio.triplover.utils;

import android.util.Log;

import com.arcadio.triplover.config.BuildConfiguration;

public class KLog {
    private static final String LOG_TAG = "Trip-lovers";

    public static void w(String msg) {
        if (BuildConfiguration.showLog()) {
            Log.w(LOG_TAG, msg);
        }
    }
}
