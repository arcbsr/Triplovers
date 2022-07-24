package com.arcadio.triplover.utils;

import android.content.Context;
import android.widget.ImageView;

import com.arcadio.triplover.R;
import com.arcadio.triplover.config.BuildConfiguration;
import com.arcadio.triplover.models.SingleToneData;
import com.bumptech.glide.Glide;

public class ImageLoader {
    public static void loadImage(String name, ImageView imageView, Context context) {
        Glide
                .with(context)
                .load(BuildConfiguration.getThumbURL() + name + ".png")
                .placeholder(R.drawable.airline_thumb)
                .into(imageView);
    }

    public static void loadImageUrl(String url, ImageView imageView, Context context, int defImage) {
        if (url == null) {
            url = "";
        }
        KLog.w(url);
        Glide
                .with(context)
                .load(url)
                .placeholder(defImage)
                .into(imageView);
    }

    public static void loadImageBackground(ImageView imageView, Context context) {
        loadImageBackground(imageView, context, R.drawable.home_background);
    }

    public static void loadImageBackground(ImageView imageView, Context context, int defaultDrawable) {

        Glide
                .with(context)
                .load(SingleToneData.getInstance().getUiDecorationData(context).getInit().getHome().getBackground())
                .placeholder(defaultDrawable)
                .into(imageView);
    }

    public static void loadImageSplash(ImageView imageView, Context context) {

        Glide
                .with(context)
                .load(SingleToneData.getInstance().getUiDecorationData(context).getInit().getHome().getSplash())
                .placeholder(R.drawable.splash_screen)
                .into(imageView);
    }
}
