package com.arcadio.triplover.communication;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.arcadio.triplover.R;
import com.arcadio.triplover.utils.Constants;
import com.arcadio.triplover.utils.KLog;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TAsyntask extends AsyncTask<Void, Void, Void> {

    public static final String ERROR_CANCEL = "cancel";
    public static final String ERROR_NODATA = "nodata";

    public interface KAsyncListener {
        void onPreListener();

        void onThreadListener(String data);

        void onCompleteListener();

        void onErrorListener(String msg);
    }

    private KAsyncListener asyncListener;
    private Activity activity;
    Dialog dialog;

    public TAsyntask(Activity activity, KAsyncListener kAsyncListener) {
        this.activity = activity;
        this.asyncListener = kAsyncListener;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        asyncListener.onThreadListener("");
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = new Dialog(activity, R.style.DialogTheme);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(com.arcadio.triplover.R.layout.layout_loading_view);
//        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(true);
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                cancel(true);
            }
        });
        dialog.show();
        ObjectAnimator anim = animController(dialog.findViewById(R.id.loading_image_anim), -1000f, -300f, 3000);
//        Animation animSlide = AnimationUtils.loadAnimation(activity,
//                R.anim.slide_l_r);
//        dialog.findViewById(R.id.loading_image_anim).startAnimation(animSlide);
        asyncListener.onPreListener();
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        KLog.w("onCancelled: Cancelled");
        asyncListener.onErrorListener(ERROR_CANCEL);
    }

    @Override
    protected void onPostExecute(Void unused) {
        super.onPostExecute(unused);
        dialog.findViewById(R.id.loading_image_anim).clearAnimation();
        if (isCancelled()) {
            asyncListener.onErrorListener(ERROR_CANCEL);
            KLog.w("Cancelled");
            return;
        }
        try {
            if (dialog != null && dialog.isShowing()) {
                ObjectAnimator anim = animController(dialog.findViewById(R.id.loading_image_anim), dialog.findViewById(R.id.loading_image_anim)
                                .getX()
                        , 0f, 500);
                anim.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        dialog.findViewById(R.id.loading_image_anim).clearAnimation();
                        dialog.dismiss();
                        asyncListener.onCompleteListener();
                    }
                });

            }
        } catch (Exception e) {
            e.printStackTrace();
            dialog.dismiss();
            asyncListener.onErrorListener(e.getMessage());
        }

    }

    private ObjectAnimator animController(View view, float start, float end, int duration) {
        ObjectAnimator buttonAnimator = ObjectAnimator.ofFloat(view,
                "translationX", start, end);
        buttonAnimator.setDuration(duration);
        buttonAnimator.setInterpolator(new LinearInterpolator());
        buttonAnimator.start();

        return buttonAnimator;
    }

    public static ResponseResult postRequest(String searchAuery, String postUrl) {
        MediaType JSON
                = MediaType.get("application/json; charset=utf-8");

//        OkHttpClient client = new OkHttpClient();

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.MINUTES)
                .readTimeout(5, TimeUnit.MINUTES)
                .build();
        RequestBody body = RequestBody.create(searchAuery, JSON);
        Request request = new Request.Builder()
                .url(Constants.ROOT_URL + postUrl)
                .post(body)
                .build();
        ResponseResult responseResult = new ResponseResult();
        try (Response response = client.newCall(request).execute()) {
            String result = response.body().string();
            responseResult.code = response.code();
            responseResult.result = result;
            KLog.w(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseResult;
    }

    public static ResponseResult postRequestHeader(String searchAuery, String postUrl, String authToken) {
        MediaType JSON
                = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(searchAuery, JSON);
        Request request = new Request.Builder()
                .url(Constants.ROOT_URL + postUrl)
                .addHeader("Authorization", "Bearer " + authToken)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            ResponseResult result = new ResponseResult();
            result.code = response.code();
            result.result = response.body().string();
//            String result = response.body().string();
            KLog.w(result.result);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static class ResponseResult {
        public int code = -1;
        public String result = "";
    }
}