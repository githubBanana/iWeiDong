package com.zf.weisport.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.WindowManager;

import com.zf.weisport.R;
import com.zf.weisport.manager.util.SPUtil;
import com.zf.weisport.ui.activity.base.BaseActivity;

/**
 * @version V1.0 <APP启动界面>
 * @author: Xs
 * @date: 2016-09-21 14:45
 * @email Xs.lin@foxmail.com
 */
public class StartupActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_startup);
        new Handler().postDelayed(()->{
            if (SPUtil.readIntData("started") != 1)
                NavigationActivity.start(this);
            else
                MainActivity.start(this);
            finish();
        },0);
    }
}
