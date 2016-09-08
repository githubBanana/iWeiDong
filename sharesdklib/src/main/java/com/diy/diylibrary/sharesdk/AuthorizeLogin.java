package com.diy.diylibrary.sharesdk;

import android.content.Context;
import android.util.Log;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;

/**
 * Created by siushen on 2016/3/18.
 */
public class AuthorizeLogin {
    public static int MANUAL = 0;
    public static int AUTOMATIC = 1;
    private Context mContext;
    private AuthorizeLogin(Context mContext) {
        this.mContext = mContext;
        ShareSDK.initSDK(mContext);
    }
    public static AuthorizeLogin getPresenter(Context mContext) {
                    return new AuthorizeLogin(mContext);
    }
    /**
    此方法会让账号系统一直依赖第三方平台
    1、点击您应用的“登录”按钮
    2、调用authorize引导用户授权
    3、在PlatformActionListener回调内 成功使用getDb().getUserId()来获取此用户在此平台上的id
    4、如果id不为空，就视为用户已经登录
     */
    public void authorize(int mode,final PlatformActionListener paListener,String platform) {
        Platform plat = ShareSDK.getPlatform(platform);
       if(mode == AUTOMATIC) {
           plat.SSOSetting(false);  //设置false表示使用SSO授权方式
        } else {
           plat.SSOSetting(true);  //truez表示不使用SSO授权方式(手机客户端来完成授权)
       }
        if(paListener != null)
            plat.setPlatformActionListener(new PlatformActionListener() {
                @Override
                public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                    paListener.onComplete(platform,i,hashMap);
                }

                @Override
                public void onError(Platform platform, int i, Throwable throwable) {
                    paListener.onError(platform,i,throwable);
                }

                @Override
                public void onCancel(Platform platform, int i) {
                    paListener.onCancel(platform,i);
                }
            });
        plat.authorize();
    }

    /**
     此方法您的应用需要有自己的账号系统
     1、点击您应用的“登录”按钮
     2、通过用户指定的平台，使用getDb().getUserId()来得到用户在此平台上的id
     3、如果id不为空，则提交给您的登录接口，否则调用showUser请求用户的资料
     4、在PlatformActionListener回调内 成功使用getDb().getUserId()来获取此用户在此平台上的id
     5、服务器接收到id以后判断用户是否已经注册，若已注册，认为登录成功，否则引导客户端进入注册流程
     6、客户端进入注册流程以后，将从showUser得到的资料填写到注册页面，用户完善资料以后，将其id和资料一并提交给您应用的服务器
     7、如果注册成功，引导用户进入客户端应用
     */
    public void showUser(final PlatformActionListener paListener,String platform) {
        Platform plat = ShareSDK.getPlatform(mContext, platform);
        if (paListener != null)
            plat.setPlatformActionListener(new PlatformActionListener() {
                @Override
                public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                    Log.i("tes","siushen:onComplete:"+platform.getName());

                    paListener.onComplete(platform, i, hashMap);
                }

                @Override
                public void onError(Platform platform, int i, Throwable throwable) {
                    Log.i("tes", "siushen:error:" + throwable.toString());
                    paListener.onError(platform, i, throwable);
                }

                @Override
                public void onCancel(Platform platform, int i) {
                    Log.i("tes","siushen:onCancel:"+platform.getName());

                    paListener.onCancel(platform, i);
                }
            });
//        plat.SSOSetting(true);
        plat.showUser(null);//执行登录，登录后在回调里面获取用户资料
        //weibo.showUser(“3189087725”);//获取账号为“3189087725”的资料
    }
    public void cancleAuthorize(String platform) {
        Platform plat = ShareSDK.getPlatform(mContext, platform);
        if (plat.isValid ()) {
            plat.removeAccount();
        }
    }

    public String getUserAccessToken(String platform) {
        Platform plat = ShareSDK.getPlatform(mContext, platform);
        String accessToken = plat.getDb().getToken(); // 获取授权token
        return  accessToken;
    }
    public String getUserOpenId(String platform) {
        Platform plat = ShareSDK.getPlatform(mContext, platform);
        String openId = plat.getDb().getUserId(); // 获取用户在此平台的ID
        return openId;
    }
    public String getUserNickname(String platform) {
        Platform plat = ShareSDK.getPlatform(mContext, platform);
        String nickname = plat.getDb().get("nickname"); // 获取用户昵称
        return nickname;
    }
}
