package com.zf.weisport.manager.util;

import android.text.TextUtils;

import java.util.Date;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-09-18 16:34
 * @email Xs.lin@foxmail.com
 */
public class TimeUtil {

    /**
     * 获取当前时间
     * @return
     */
    public static String getCurrFormatTime() {
        Date date = new Date(System.currentTimeMillis());
        String currTime = UnixTimeStamp.getSimpleDateFormat(UnixTimeStamp.FORMAT1).format(date);
        return currTime;
    }
    /**
     * 朋友圈时间显示 ps:待优化
     * @param dateString
     * @return
     */
    public static String sortAddTime(String dateString) {// 2016/5/24 15:18:45
        if (TextUtils.isEmpty(dateString))
            return "";
        String now = getCurrFormatTime();
        String[] arrayNow = now.split(" ");
        String[] dateNow = arrayNow[0].split("/");
        String[] timeNow = arrayNow[1].split(":");
        int yearNow = Integer.valueOf(dateNow[0]).intValue();
        int monthNow = Integer.valueOf(dateNow[1]).intValue();
        int dayNow = Integer.valueOf(dateNow[2]).intValue();
        int hourNow = Integer.valueOf(timeNow[0]).intValue();
        int minuteNow = Integer.valueOf(timeNow[1]).intValue();
        int secodeNow = Integer.valueOf(timeNow[2]).intValue();

        String[] array = dateString.split(" ");
        String[] date = array[0].split("/");
        String[] time = array[1].split(":");
        int year = Integer.valueOf(date[0]).intValue();
        int month = Integer.valueOf(date[1]).intValue();
        int day = Integer.valueOf(date[2]).intValue();
        int hour = Integer.valueOf(time[0]).intValue();
        int minute = Integer.valueOf(time[1]).intValue();
        int secode = Integer.valueOf(time[2]).intValue();
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
