package com.zf.weisport;

import android.app.Application;
import android.content.Context;

import com.xs.net.retrofit.NetConfig;
import com.xs.xglib.XgConfig;
import com.zf.weisport.manager.db.model.GlobalData;
import com.zf.weisport.manager.net.RequestFileHelper;
import com.zf.weisport.manager.net.RequestHelper;
import com.zf.weisport.manager.util.CrashHandler;
import com.zf.weisport.manager.util.ImageLoaderUtil;
import com.zhy.autolayout.config.AutoLayoutConifg;

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
        new NetConfig.Builder().webService(RequestHelper.WEB_SERVICE).
                fileUploadWebService(RequestFileHelper.WEB_SERVICE).build();

        AutoLayoutConifg.getInstance().useDeviceSize();

        ImageLoaderUtil.init(this);

        GlobalData.init(this);

        CrashHandler.instance().init(this);

        XgConfig.init(this);
    }

    /**
     *  全局上下文
     * @return
     */
    public static Context getContext() {
        return mContext;
    }
}
