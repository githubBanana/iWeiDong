package com.zf.weisport.manager.db.model;

import android.content.Context;
import android.util.Log;

import com.zf.weisport.manager.db.bean.User;
import com.zf.weisport.manager.util.UserUtil;


/**
 * @version V1.0 <初始化用户等全局数据>
 * @author: Xs
 * @date: 2016-03-31 15:30
 * @email Xs.lin@foxmail.com
 */
public class GlobalData {
    public static void init(Context context) {
        if (UserUtil.isLogin(context)) {
            if (AppDatabaseCache.getCache(context).queryUser() != null) {
                User.getUser().setUser(AppDatabaseCache.getCache(context).queryUser());
                Log.i("test", "用户已经登录：" + User.getUser().toString());
            }
        } else {
        }
    }

}
