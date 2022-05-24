package com.arcadio.triplover.models.usermodel;

import android.app.Activity;

import com.arcadio.triplover.communication.TAsyntask;
import com.arcadio.triplover.utils.Constants;
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
                    String result = TAsyntask.postRequest(new Gson().toJson(loginReq), Constants.ROOT_URL_AUTH);
                    response = new Gson().fromJson(result, LoginResponse.class);
                    if (response.getToken() == null) {
                        response = null;
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
