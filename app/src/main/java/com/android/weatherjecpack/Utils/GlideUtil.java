package com.android.weatherjecpack.Utils;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class GlideUtil {

    public static void glideImage(Context context, String url, ImageView view){
        Glide.with(context).load(url).into(view);
    }

    public static void glideImage(Context context, int url, ImageView view){
        Glide.with(context).load(url).into(view);
    }
}
