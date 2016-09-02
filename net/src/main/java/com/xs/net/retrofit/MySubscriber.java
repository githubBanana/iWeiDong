package com.xs.net.retrofit;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;

import rx.Subscriber;

/**
 * Created by CY on 2016/4/8.
 *
 */
public abstract class MySubscriber<T> extends Subscriber<T> {

    private Context mContext;
    private View mView;

    public MySubscriber(Context context) {
        mContext = context;
    }

    public MySubscriber(View view) {
        mView = view;
    }

    public MySubscriber() {
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();

        if (mContext != null) {
            Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
        } else if (mView != null) {
            Snackbar.make(mView, e.getMessage(), Snackbar.LENGTH_SHORT).show();
        }
    }
}
