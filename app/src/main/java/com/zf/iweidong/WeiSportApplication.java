package com.zf.iweidong;

import android.app.Application;

import com.xs.net.retrofit.NetConfig;
import com.xs.net.retrofit.RequestHelper;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-09-05 18:34
 * @email Xs.lin@foxmail.com
 */
public class WeiSportApplication extends Application {
    private static final String TAG = "WeiSportApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        new NetConfig.Builder().webService(RequestHelper.WEB_SERVICE).build();
    }
}
