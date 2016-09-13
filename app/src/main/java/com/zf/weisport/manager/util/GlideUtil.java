package com.zf.weisport.manager.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.zf.weisport.R;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-09-13 11:09
 * @email Xs.lin@foxmail.com
 */
public class GlideUtil {

    /**
     * 显示圆形头像
     * @param url
     * @param imageView
     */
    public static void showHead(String url, ImageView imageView) {
        Context context = imageView.getContext();
        Uri uri = !TextUtils.isEmpty(url) ? Uri.parse(url) : null;
        Glide.with(context).load(uri).asBitmap().thumbnail(0.6f).centerCrop().placeholder(R.mipmap.ic_default_head).into(new BitmapImageViewTarget(imageView) {
            @Override
            protected void setResource(Bitmap resource) {
                super.setResource(resource);
                RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                roundedBitmapDrawable.setCircular(true);
                getView().setImageDrawable(roundedBitmapDrawable);
            }
        });
    }

    public static void showHead(Uri uri, ImageView imageView) {
        Context context = imageView.getContext();
        Glide.with(context).load(uri).asBitmap().thumbnail(0.6f).centerCrop().placeholder(R.mipmap.ic_default_head).into(new BitmapImageViewTarget(imageView) {
            @Override
            protected void setResource(Bitmap resource) {
                super.setResource(resource);
                RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                roundedBitmapDrawable.setCircular(true);
                getView().setImageDrawable(roundedBitmapDrawable);
            }
        });
    }
}
