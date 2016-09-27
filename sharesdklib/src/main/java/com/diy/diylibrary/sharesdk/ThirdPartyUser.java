package com.diy.diylibrary.sharesdk;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformDb;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;

/**
 * @version V1.0 <第三方平台用户信息>
 * @author: Xs
 * @date: 2016-03-25 11:06
 */
public class ThirdPartyUser {

    private static ThirdPartyUser instance = null;
    private ThirdPartyUser() {
    }
    public static ThirdPartyUser getInstance() {
        if (instance == null) {
            synchronized (ThirdPartyUser.class) {
                if (instance == null) {
                    instance = new ThirdPartyUser();
                }
            }
        }
        return instance;
    }

    private String accessToken;       // 获取授权 令牌
    private String accessTokenSecret; // 令牌密钥
    private String openId ;           // 获取用户在此平台的ID
    private String nickname;          // 获取用户昵称
    private String userIconUrl;       // 用户头像连接
    private String userGender;        // 性别

    private String platformName;          // 用户所属平台
    private int platfromVersion;   // 平台版本

    private int type;//登录平台(4-微信 5-QQ 6-微博)

    /**
     * 初始化第三方用户登录信息
     * @param platform
     */
    public void initPlatformUserInfo(Platform platform) {
        PlatformDb platformDb = platform.getDb();
        setAccessToken(platformDb.getToken());
        setOpenId(platformDb.getUserId());
        setNickname(platformDb.getUserName());
        setUserIconUrl(platformDb.getUserIcon());
        setAccessTokenSecret(platformDb.getTokenSecret());
        setPlatformName(platformDb.getPlatformNname());
        setPlatfromVersion(platformDb.getPlatformVersion());
        if ("m".equals(platformDb.getUserGender()))
            setUserGender("1");
        else
            setUserGender("0");
        if (QQ.NAME.equals(platform.getName()))
            setType(5);
        else if (Wechat.NAME.equals(platform.getName()))
            setType(4);
        else if (SinaWeibo.NAME.equals(platform.getName()))
            setType(6);
        else {}
    }

    public String getPlatformName() {
        return platformName;
    }

    public void setPlatformName(String platformName) {
        this.platformName = platformName;
    }

    public int getPlatfromVersion() {
        return platfromVersion;
    }

    public void setPlatfromVersion(int platfromVersion) {
        this.platfromVersion = platfromVersion;
    }

    public String getAccessTokenSecret() {
        return accessTokenSecret;
    }

    public void setAccessTokenSecret(String accessTokenSecret) {
        this.accessTokenSecret = accessTokenSecret;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getUserGender() {
        return userGender;
    }

    public String getOpenId() {
        return openId;
    }

    public String getNickname() {
        return nickname;
    }

    public String getUserIconUrl() {
        return userIconUrl;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setUserIconUrl(String userIconUrl) {
        this.userIconUrl = userIconUrl;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {

        return "\n############### thirdPartyUser infor start ##################\n"+"["+"accessToken:"+accessToken+"\nopenId:"+openId+
                "\nnickname:"+nickname+"\nuserIconUrl:"+userIconUrl+"\nuserGender:"+userGender+"\ntokenSecret:"+accessTokenSecret+
                "\nplatformName:"+platformName+"\nplatformVersion:"+platfromVersion+"\ntype:"+type+
                "]\n############### thirdPartyUser infor end ##################";
    }
}
