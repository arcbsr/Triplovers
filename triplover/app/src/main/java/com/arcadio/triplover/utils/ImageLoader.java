package com.arcadio.triplover.utils;

import android.content.Context;
import android.widget.ImageView;

import com.arcadio.triplover.R;
import com.bumptech.glide.Glide;

public class ImageLoader {
    public static void loadImage(String name, ImageView imageView, Context context) {
        Glide
                .with(context)
                .load(Constants.ROOT_URL_THUMB + name + ".png")
                .placeholder(R.drawable.airline_thumb)
                .into(imageView);
    }
}
