package com.zf.weisport.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.zf.weisport.R;
import com.zf.weisport.ui.activity.base.BaseActivity;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-09-21 14:02
 * @email Xs.lin@foxmail.com
 */
public class ForgetPsdNextActivity extends BaseActivity {

    public static void start(Activity activity) {
        Intent intent = new Intent(activity,ForgetPsdNextActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_psd_next,true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setCenterTitle(R.string.forget_psd_title);
    }
}

