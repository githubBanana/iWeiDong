package com.xs.net.retrofit;

import android.text.TextUtils;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-09-01 17:18
 * @email Xs.lin@foxmail.com
 */
public class NetConfig {

    private static String HOST;         // "http://192.168.1.222:8080"
    private static String WEB_SERVICE ; // "http://wd.zfwsc.com/WebService/WeiYunDong.asmx/"
    private static String ABOUT_US;     // "http://weidongzn.com/Web/App/AboutUs"
    private static String FILE_UPLOAD_WEB_SERVICE; // "http://wd.zfwsc.com/Admin/File/"

    public static final class Builder {

        public Builder host(String host) {
            HOST = host;
            return this;
        }

        public Builder webService(String webService) {
            WEB_SERVICE = webService;
            return this;
        }

        public Builder aboutUs(String aboutUs){
            ABOUT_US = aboutUs;
            return this;
        }

        public Builder fileUploadWebService(String fileUploadWebService) {
            FILE_UPLOAD_WEB_SERVICE = fileUploadWebService;
            return this;
        }

        public void build(){
        }

    }

    public static String getHOST() {
        if (TextUtils.isEmpty(HOST))
            throw new NullPointerException("HOST is null");
        return HOST;
    }

    public static String getWebService() {
        if (TextUtils.isEmpty(WEB_SERVICE))
            throw new NullPointerException("WEB_SERVICE is null");
        return WEB_SERVICE;
    }

    public static String getAboutUs() {
        if (TextUtils.isEmpty(ABOUT_US))
            throw new NullPointerException("ABOUT_US is null");
        return ABOUT_US;
    }


    public static String getFileUploadWebService() {
        if (TextUtils.isEmpty(FILE_UPLOAD_WEB_SERVICE))
            throw new NullPointerException("FILE_UPLOAD_WEB_SERVICE is null");
        return FILE_UPLOAD_WEB_SERVICE;
    }

}
