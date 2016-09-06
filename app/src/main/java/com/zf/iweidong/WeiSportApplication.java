package com.zf.iweidong;

import android.app.Application;
import android.content.Context;

import com.xs.net.retrofit.NetConfig;
import com.zf.iweidong.manager.net.RequestHelper;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-09-05 18:34
 * @email Xs.lin@foxmail.com
 */
public class WeiSportApplication extends Application {

    public static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        this.mContext = this;
        /**
         * 配置服务器链接
         */
        new NetConfig.Builder().webService(RequestHelper.WEB_SERVICE).build();
    }

    /**
     *  全局上下文
     * @return
     */
    public static Context getContext() {
        return mContext;
    }
}
