package com.arcadio.triplover.models.usermodel;

import android.app.Activity;

import com.arcadio.triplover.communication.TAsyntask;
import com.arcadio.triplover.models.SingleToneData;
import com.arcadio.triplover.models.uidecoration.UiDecoration;
import com.arcadio.triplover.utils.Constants;
import com.arcadio.triplover.utils.PreferencesHelpers;
import com.arcadio.triplover.utils.Utils;

public class UserLoginController {
    public interface onLoginListener {
        void onSuccess(LoginResponse response);

        void onFail(int errorCode);
    }

    public void LoginData(Activity activity, LoginReq loginReq, onLoginListener listener) {

        new TAsyntask(activity, new TAsyntask.KAsyncListener() {
            String error = "";
            LoginResponse response;

            @Override
            public void onPreListener() {

            }

            @Override
            public void onThreadListener(String data) {
                try {

                    TAsyntask.ResponseResult result = TAsyntask.postRequest(Utils.getGson().toJson(loginReq), Constants.ROOT_URL_AUTH);
                    if (result.code == 200) {
                        response = Utils.getGson().fromJson(result.result, LoginResponse.class);
                        if (response.getToken() == null) {
                            response = null;
                        }
                    }
                } catch (Exception e) {
                    response = null;
                }

                try {
                    if (PreferencesHelpers.isUiDecorationIsExpired(activity)) {
//                    String jsonFileString = Utils.getJsonFromAssets(activity, "ui_decoration.json");
                        TAsyntask.ResponseResult responseResult =
                                TAsyntask.getRequestUrl("https://triplover-app-data.s3.ap-southeast-1.amazonaws.com/ui_decoration.json");
                        if (responseResult.code == 200 && responseResult.result != null) {
                            UiDecoration uiDecoration = Utils.getGson().fromJson(responseResult.result, UiDecoration.class);
                            if (uiDecoration != null) {
                                SingleToneData.setUiDecorationData(uiDecoration);
                                PreferencesHelpers.saveUiDecorationData(activity, responseResult.result);
                            }
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCompleteListener() {
                if (response != null)
                    listener.onSuccess(response);
                else
                    listener.onFail(0);

            }

            @Override
            public void onErrorListener(String msg) {
                error = msg;
            }
        }).execute();

    }
}
