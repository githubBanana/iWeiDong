package com.zf.weisport.manager.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.telephony.TelephonyManager;

import java.lang.reflect.Field;
import java.util.UUID;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-09-05 18:28
 * @email Xs.lin@foxmail.com
 */
public class PhoneUtil {

    /**
     * 获取手机设备UUID
     * @param context
     * @return
     */
    public static String getUUID(Context context){
        final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        final String tmDevice, tmSerial, tmPhone, androidId;
        tmDevice = "" + tm.getDeviceId();
        tmSerial = "" + tm.getSimSerialNumber();
        androidId = "" + android.provider.Settings.Secure.getString(context.getContentResolver(),android.provider.Settings.Secure.ANDROID_ID);
        UUID deviceUuid = new UUID(androidId.hashCode(), ((long)tmDevice.hashCode() << 32) | tmSerial.hashCode());
        String uniqueId = deviceUuid.toString();
        return uniqueId;
    }

    /**
     * 获取手机的版本信息
     *
     * @return
     */
    public static String getVersionInfo(Context mContext) {
        StringBuilder sb = new StringBuilder();
        try {
            PackageManager pm = mContext.getPackageManager();
            PackageInfo info = pm.getPackageInfo(mContext.getPackageName(), 0);
            sb.append("软件版本:"+info.versionName+"\n");
            sb.append("设备型号:"+ Build.MODEL+"\n");//设备型号
            sb.append("设备SDK版本:"+Build.VERSION.SDK_INT+"\n");//设备SDK版本
            sb.append("设备的系统版本:"+Build.VERSION.RELEASE+"\n");//设备的系统版本
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "版本号未知";
        }
    }
    /**
     * 获取手机的硬件信息
     *
     * @return
     */
    public static String getMobileInfo(Context mContext) {
        StringBuffer sb = new StringBuffer();
        sb.append("硬件信息:"+"\n");
        // 通过反射获取系统的硬件信息
        try {

            Field[] fields = Build.class.getDeclaredFields();
            for (Field field : fields) {
                // 暴力反射 ,获取私有的信息
                field.setAccessible(true);
                String name = field.getName();
                String value = field.get(null).toString();
                sb.append(name + "=" + value);
                sb.append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }



}
