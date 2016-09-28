package com.zf.weisport.manager.util;

import android.text.TextUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-09-18 16:34
 * @email Xs.lin@foxmail.com
 */
public class TimeUtil {

    private static ThreadLocal _threadLocal = new ThreadLocal() {
        @Override
        protected Object initialValue() {
            return null;
        }
    };

    /**
     * 东八时区 SimpleDateFormat
     * @return
     */
    public static SimpleDateFormat getSimpleDateFormat() {
        SimpleDateFormat format = (SimpleDateFormat) _threadLocal.get();
        if (format == null) {
            format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.CHINA);
            format.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));//=TimeZone.getDefault()
            _threadLocal.set(format);
        }
        return format;
    }

    public static String getCurrTime_Minute_Second_String() {
        return getCurrFormatTime().substring(14,19);
    }
    public static String getCurrTime_Hour_Minute_Second_String() {
        return getCurrFormatTime().substring(11,19);
    }


    /**
     * 获取当前时间
     * @return
     */
    public static String getCurrFormatTime(String formatStr) {
        Date date = new Date(System.currentTimeMillis());
        String currTime = UnixTimeStamp.getSimpleDateFormat(formatStr).format(date);
        return currTime;
    }

    public static String getCurrFormatTime() {
        Date date = new Date(System.currentTimeMillis());
        String currTime = getSimpleDateFormat().format(date);
        return currTime;
    }
    public static long getCurrTime() {
        return  (System.currentTimeMillis() / 1000);
    }

    /**
     * 朋友圈时间显示 ps:待优化
     * @param dateString
     * @return
     */
    public static String sortAddTime(String dateString) {// 2016/5/24 15:18:45
        if (TextUtils.isEmpty(dateString))
            return "";
        String now = getCurrFormatTime("yyyy/MM/dd HH:mm:ss");
        String[] arrayNow = now.split(" ");
        String[] dateNow = arrayNow[0].split("/");
        String[] timeNow = arrayNow[1].split(":");
        int yearNow = Integer.valueOf(dateNow[0]);
        int monthNow = Integer.valueOf(dateNow[1]);
        int dayNow = Integer.valueOf(dateNow[2]);
        int hourNow = Integer.valueOf(timeNow[0]);
        int minuteNow = Integer.valueOf(timeNow[1]);
        int secodeNow = Integer.valueOf(timeNow[2]);

        String[] array = dateString.split(" ");
        String[] date = array[0].split("/");
        String[] time = array[1].split(":");
        int year = Integer.valueOf(date[0]);
        int month = Integer.valueOf(date[1]);
        int day = Integer.valueOf(date[2]);
        int hour = Integer.valueOf(time[0]);
        int minute = Integer.valueOf(time[1]);
        int secode = Integer.valueOf(time[2]);
        String returnValue = "";
        if (year == yearNow) {
            if (month == monthNow) {
                int a = Math.abs(day - dayNow);
                switch (a) {
                    case 0:
                        if (hour == hourNow) {
                            if (minute == minuteNow) {
                                returnValue =  "刚刚";
                            } else {
                                returnValue =  Math.abs(minute - minuteNow) + "分钟前";
                            }
                        } else {
                            returnValue =  Math.abs(hour - hourNow) + "小时前";
                        }
                        break;
                    case 1:
                        returnValue =  "昨天";
                        break;
                    default:
                        returnValue =  a+"天前";
                        break;
                }
            } else {
                return Math.abs(month - monthNow) + "个月前";
            }
        } else {
            return Math.abs(year - yearNow) +"年前";
        }
        return returnValue;
    }

}
