package com.arcadio.triplover.acitivies;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;

import com.arcadio.triplover.BaseActivity;
import com.arcadio.triplover.R;
import com.arcadio.triplover.models.SingleToneData;
import com.arcadio.triplover.models.usermodel.LoginReq;
import com.arcadio.triplover.models.usermodel.LoginResponse;
import com.arcadio.triplover.models.usermodel.UserLoginController;
import com.arcadio.triplover.utils.Constants;
import com.arcadio.triplover.utils.ImageLoader;
import com.arcadio.triplover.utils.PreferencesHelpers;
import com.arcadio.triplover.utils.Utils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

public class SplashScreenActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        String userId = PreferencesHelpers.loadStringData(getContext(), Constants.USER_ID, "");
        String userPass = PreferencesHelpers.loadStringData(getContext(), Constants.USER_PASS, "");
        if (userId.isEmpty() || userPass.isEmpty()) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startApp();
                }
            }, 2000);
            return;
        }
        PreferencesHelpers.removeData(getContext(), "ui_decoration");
        PreferencesHelpers.removeData(getContext(), "ui_decoration_time");
        final LoginReq loginReq = new LoginReq(userId, userPass, Utils.getDeviceID(getContext()));
        new UserLoginController().LoginData(getActivity(), loginReq, new UserLoginController.onLoginListener() {
            @Override
            public void onSuccess(LoginResponse response) {
                if (response.getToken() != null) {
                    PreferencesHelpers.setToken(getContext(), response.getToken());
                }
                startApp();
            }

            @Override
            public void onFail(int errorCode) {
                startApp();
            }
        });
        ImageLoader.loadImageSplash(findViewById(R.id.homebackground), getContext());
    }

    private void startApp() {
        Glide
                .with(getContext())
                .load(SingleToneData.getInstance().getUiDecorationData(getContext()).getInit().getHome().getBackground())
                .submit();
        Glide
                .with(getContext())
                .load(SingleToneData.getInstance().getUiDecorationData(getContext()).getInit().getHome().getBackground())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        startActivity(new Intent(getContext(), MainTabActivity.class));
                        finish();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        startActivity(new Intent(getContext(), MainTabActivity.class));
                        finish();
                        return false;
                    }
                })
                .submit();

    }

    @Override
    public void onBackPressed() {

    }
}