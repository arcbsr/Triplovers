package com.arcadio.triplover.models.usermodel;

import android.app.Activity;

import com.arcadio.triplover.communication.TAsyntask;
import com.arcadio.triplover.utils.Constants;
import com.arcadio.triplover.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

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
                } catch (JsonSyntaxException e) {
                    response = null;
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
