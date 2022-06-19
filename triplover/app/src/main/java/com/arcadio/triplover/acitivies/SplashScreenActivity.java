package com.arcadio.triplover.acitivies;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.arcadio.triplover.BaseActivity;
import com.arcadio.triplover.R;
import com.arcadio.triplover.models.usermodel.LoginReq;
import com.arcadio.triplover.models.usermodel.LoginResponse;
import com.arcadio.triplover.models.usermodel.UserLoginController;
import com.arcadio.triplover.utils.Constants;
import com.arcadio.triplover.utils.PreferencesHelpers;

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
        final LoginReq loginReq = new LoginReq(userId, userPass);
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
    }

    private void startApp() {
        startActivity(new Intent(getContext(), MainTabActivity.class));
        finish();
    }

    @Override
    public void onBackPressed() {

    }
}