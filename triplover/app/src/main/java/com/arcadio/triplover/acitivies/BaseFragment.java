package com.arcadio.triplover.acitivies;

import android.graphics.Color;
import android.os.Build;

import androidx.fragment.app.Fragment;

public class BaseFragment extends Fragment {
    public int getColorFromResource(int resourceId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return getResources().getColor(resourceId, getContext().getTheme());
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return getResources().getColor(resourceId);
        }
        return Color.WHITE;
    }
}
