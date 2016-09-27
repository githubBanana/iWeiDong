package com.zf.weisport.manager.util;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.zf.weisport.R;
import com.zf.weisport.WeiSportApplication;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-09-06 16:03
 * @email Xs.lin@foxmail.com
 */
public class UIUtil {

    /**
     * 获取全局上下文
     * @return
     */
    public static Context getContext() {
        return WeiSportApplication.getContext();
    }

    /**
     * 获取资源
     */
    public static Resources getResources()
    {
        return getContext().getResources();
    }

    public static String getString(int resId)
    {
        return getResources().getString(resId);
    }

    public static String getString(int resId, Object... formatArgs)
    {
        return getResources().getString(resId, formatArgs);
    }

}


