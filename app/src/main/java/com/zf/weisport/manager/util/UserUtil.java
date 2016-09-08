package com.zf.weisport.manager.util;

import android.content.Context;
import android.util.Log;

import com.zf.weisport.manager.db.bean.User;
import com.zf.weisport.model.LoginModel;

/**
 * @version V1.0 <判断用户是否登录>
 * @author: Xs
 * @date: 2016-04-06 10:50
 * @email Xs.lin@foxmail.com
 */
public class UserUtil {
    private static String LOGIN             = "login";
    private static String LOGIN_THIRDPARTY  = "loginThirdParty";

    /**
     * 判断用户是否登录
     * @param context
     * @return
     */
    public static boolean isLogin(Context context) {
        context = UIUtil.getContext();
            if (context == null)
                Log.e("test","context == nullllll");
        if (SPUtil.readNormalData(context,LOGIN,0) != 1)//1代表登录
            return false;
          else
            return true;
    }
    public static boolean isThirdPartyLogin(Context context) {
        if (SPUtil.readNormalData(context,LOGIN_THIRDPARTY,0) != 1)//1代表第三方登录
            return false;
        else
            return true;
    }

    /**
     * 注册登录时需设置
     */
    public static void setLogin() {
        SPUtil.saveNormalData(UIUtil.getContext(),LOGIN,1);
        SPUtil.saveNormalData(UIUtil.getContext(),LOGIN_THIRDPARTY,0);
    }

    /**
     * 第三方登录时需设置
     */
    public static void setLoginThirdparty() {
        SPUtil.saveNormalData(UIUtil.getContext(),LOGIN,1);
        SPUtil.saveNormalData(UIUtil.getContext(),LOGIN_THIRDPARTY,1);
    }

    /**
     * 用户退出时需设置
     */
    public static void setLoginOut() {
        SPUtil.saveNormalData(UIUtil.getContext(),LOGIN,0);
        SPUtil.saveNormalData(UIUtil.getContext(),LOGIN_THIRDPARTY,0);
        User.getUser().clear();
    }

    /**
     * 初始化用户信息  用户信息应与登录接口返回字段一一对应
     * @param loginModel
     */
    public static void initUser(LoginModel loginModel)
    {
        User user = User.getUser();
        user.setId(loginModel.ID);                  //ID  --> user.Net_id
        user.setIS_Sync(loginModel.IS_Sync);        //IS_Sync
        user.setMOpenId(loginModel.MOpenId);        //MOpenId
        user.setQOpenId(loginModel.QOpenId);        //QOpenId
        user.setWOpenId(loginModel.WOpenId);        //WOpenId
        user.setName(loginModel.Name);              //Name
        user.setPhone(loginModel.LoginName);        //LoginName  --> user.Phone
        user.setHeadUrl(loginModel.Head_Img);       //Head_Img   --> user.HeadUrl
        user.setLevel(loginModel.Level);            //Level
        user.setSex(loginModel.Sex);                //Sex

        Log.e("UserUtil","UserUtil.init: \n"+User.getUser().toString());
    }
}
