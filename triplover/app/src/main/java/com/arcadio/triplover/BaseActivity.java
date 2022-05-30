package com.arcadio.triplover;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.arcadio.triplover.utils.Utils;
import com.google.gson.Gson;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        if (Build.VERSION.SDK_INT >= 21) {
//            Window window = getWindow();
//            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(getColorFromResource(R.color.tab_bar));
//        }
    }

    public int getColorFromResource(int resourceId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return getResources().getColor(resourceId, this.getTheme());
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return getResources().getColor(resourceId);
        }
        return Color.WHITE;
    }

    protected Context getContext() {
        return this;
    }

    protected Activity getActivity() {
        return this;
    }

    protected Gson getGson() {
        return Utils.getGson();
    }
}