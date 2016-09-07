package com.zf.iweidong.manager.net;

import com.xs.net.retrofit.RetrofitClient;
import com.zf.iweidong.model.LoginModel;
import com.zf.iweidong.model.RegisterModel;
import com.zf.iweidong.model.SMSCodeModel;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;

public class RequestHelper {


    public static final String HOST = "http://192.168.1.222:8080";
    public static final String WEB_SERVICE = "http://wd.zfwsc.com/WebService/WeiYunDong.asmx/";
    public static final String ABOUT_US = "http://weidongzn.com/Web/App/AboutUs";


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



}

