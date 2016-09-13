package com.zf.weisport.manager.net;

import com.xs.net.retrofit.RetrofitClient;
import com.zf.weisport.model.GetTopModel;
import com.zf.weisport.model.GetVideoModel;
import com.zf.weisport.model.LabelTopicModel;
import com.zf.weisport.model.LoginModel;
import com.zf.weisport.model.RegisterModel;
import com.zf.weisport.model.SMSCodeModel;
import com.zf.weisport.model.SyncInfoModel;
import com.zf.weisport.model.UpdUserInfoModel;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;

public class RequestHelper {


    public static final String HOST = "http://192.168.1.222:8080";
    public static final String WEB_SERVICE = "http://wd.zfwsc.com/WebService/WeiYunDong.asmx/";
    public static final String ABOUT_US = "http://weidongzn.com/Web/App/AboutUs";
    public static final int PAGESIZE = 10;

    private static final RequestHelper INSTANCE = new RequestHelper();
    private RequestHelper() {}
    public static final RequestHelper getInstance() {
        return INSTANCE;
    }

    /**
     * Login
     * 登陆
     * LoginName:登陆名(手机号)
     * Password:密码
     * UUID:设备唯一标识
     * AppType:App类型(1-安卓 2-IOS)
     *
     * @param LoginName
     * @param Password
     * @param UUID
     * @param AppType
     * @return
     */
    public Observable<LoginModel> login(String LoginName, String Password, String UUID, int AppType) {
        Map<String, Object> map = new HashMap<>();
        map.put("LoginName", LoginName);
        map.put("Password", Password);
        map.put("UUID", UUID);
        map.put("AppType", String.valueOf(AppType));
        return new RetrofitClient.Builder().method("Login").map(map).post(LoginModel.class);
    }

    /**
     * OpenLogin
     * 第三方登录
     * OpenId:第三方用户唯一标识
     * Type:4-微信 5-QQ 6-微博
     * UUID:设备唯一标识
     * AppType:App类型(1-安卓 2-IOS)
     *
     * @param OpenId
     * @param Type
     * @param UUID
     * @param AppType
     * @return
     */
    public static Observable<LoginModel> openLogin(String OpenId, int Type, String UUID, int AppType) {
        Map<String, Object> map = new HashMap<>();
        map.put("OpenId", OpenId);
        map.put("Type", String.valueOf(Type));
        map.put("UUID", UUID);
        map.put("AppType", String.valueOf(AppType));
        return new RetrofitClient.Builder().method("OpenLogin").map(map).post(LoginModel.class);
    }

    /**
     * SyncInfo
     * 第三方同步资料
     * UserId:用户ID
     * HeadImg:用户头像URL
     * Sex：性别
     * Name:用户昵称
     *
     * @param UserId
     * @param HeadImg
     * @param Sex
     * @param Name
     * @return
     */
    public Observable<SyncInfoModel> SyncInfo(String UserId, String HeadImg, String Sex, String Name) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("UserId", UserId);
        map.put("HeadImg", HeadImg);
        map.put("Sex", Sex);
        map.put("Name", Name);
        return new RetrofitClient.Builder().method("SyncInfo").map(map).post(SyncInfoModel.class);
    }

    /**
     * Register
     * 手机号注册接口
     * Mobile:手机号
     * Password:密码
     *
     * @param Mobile
     * @param Password
     * @return
     */
    public Observable<RegisterModel> register(String Mobile, String Password) {
        Map<String, Object> map = new HashMap<>();
        map.put("Mobile", Mobile);
        map.put("Password", Password);
        return new RetrofitClient.Builder().method("Register").map(map).post(RegisterModel.class);
    }

    /**
     * GetSMSCode
     * 获取短信验证码
     * Mobile:手机号
     * Type:类型 (1:注册 2:找回密码 3:第三方绑定)
     *
     * @param Mobile
     * @param Type
     * @return
     */
    public Observable<SMSCodeModel> getSMSCode(String Mobile, int Type) {
        Map<String, Object> map = new HashMap<>();
        map.put("Mobile", Mobile);
        map.put("Type", String.valueOf(Type));
        return new RetrofitClient.Builder().method("GetSMSCode").map(map).post(SMSCodeModel.class);
    }

    /**
     * GetTop
     * 获取轮播图
     *
     * @return
     */
    public Observable<GetTopModel> getTop() {
        return new RetrofitClient.Builder().method("GetTop").post2(GetTopModel.class);
    }

    /**
     * GetLabelTopic
     * 获取标签朋友圈消息
     *
     * @return
     */
    public Observable<LabelTopicModel> getLabelTopic() {
        return new RetrofitClient.Builder().method("GetLabelTopic").post2(LabelTopicModel.class);
    }

    /**
     * GetVideo
     * 获取视频列表
     * Type:0-推荐视频 1-健身教学 2-创新玩法 3-超神竞技
     * PageIndex:页码
     * PageSize:页数量
     *
     * @param Type
     * @param PageIndex
     * @return
     */
    public static Observable<GetVideoModel> getVideo(int Type, int PageIndex) {
        Map<String, Object> map = new HashMap<>();
        map.put("Type", Type);
        map.put("PageIndex", PageIndex);
        map.put("PageSize", PAGESIZE);
        return new RetrofitClient.Builder().method("GetVideo").map(map).post(GetVideoModel.class);
    }


    /**
     * UpdUser
     * 更新用户资料
     * User_ID:用户ID
     * Head_Img:上传图片的ID
     * Name:昵称
     * Sex:性别(0:女 1:男)
     *
     * @param User_ID
     * @param Head_Img
     * @param Name
     * @param Sex
     * @return
     */
    public Observable<UpdUserInfoModel> updUser(String User_ID, String Head_Img, String Name, String Sex) {
        Map<String, Object> map = new HashMap<>();
        map.put("User_ID", User_ID);
        map.put("Head_Img", Head_Img);
        map.put("Name", Name);
        map.put("Sex", Sex);
        return new RetrofitClient.Builder().method("UpdUser").map(map).post(UpdUserInfoModel.class);
    }


}

