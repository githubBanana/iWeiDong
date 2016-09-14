package com.zf.weisport.manager.util;

import android.support.annotation.NonNull;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * @version V1.0 <时间戳工具>
 * @author: Xs
 * @date: 2016-08-08 09:40
 * @email Xs.lin@foxmail.com
 */
public class UnixTimeStamp {

    /**
     * GMT = Greenwich Mean Time 格林威治标准时间 http://wwp.greenwichmeantime.com/
     +8:在标准时间前8小时
     -8:在标准时间后8小时
     *
     *
     */
    private static final String TAG = "UnixTimeStamp";

    public static String FORMAT1 = "dd/MM/yyyy HH:mm:ss";
    public static String FORMAT2 = "yyyy-MM-dd HH:mm:ss";
    public static String FORMAT3 = "yyyy-MM-dd";
    public static String FORMAT4 = "HH:mm:ss";
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
    private static SimpleDateFormat getSimpleDateFormat() {
        SimpleDateFormat format = (SimpleDateFormat) _threadLocal.get();
        if (format == null) {
            format = new SimpleDateFormat(FORMAT2, Locale.CHINA);
            format.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));//=TimeZone.getDefault()
            _threadLocal.set(format);
        }
        return format;
    }


    private static ThreadLocal _threadLocalStandard = new ThreadLocal() {
        @Override
        protected Object initialValue() {
            return null;
        }
    };
    /**
     * 格林威治标准时间【1970年01月01日00时00分00秒】
     * 这里是为了将时间戳格式的时间间隔转化为日期格式
     * @return
     */
    private static SimpleDateFormat getSimpleDateFormatStandard() {
        SimpleDateFormat format = (SimpleDateFormat) _threadLocalStandard.get();
        if (format == null) {
            format = new SimpleDateFormat(FORMAT2, Locale.CHINA);
            format.setTimeZone(TimeZone.getTimeZone("GMT+0")); //TO 1970-01-01 00:00:00
            _threadLocalStandard.set(format);
        }
        return format;
    }



    /**
     * 获取当前时间戳
     * @return
     */
    public static long getCurrTime() {
        return  (System.currentTimeMillis() / 1000);
    }

    /**
     * 2016-08-08 09:36:15  --> 1470620175
     *                          1470648975
     * 正常时间 to 时间戳
     * @param date
     */
    public static void normalToStamp(@NonNull String date) {
        try {
            long epoch = getSimpleDateFormat().parse(date).getTime() / 1000;
            Log.e(TAG, "normalToStamp: "+epoch );
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    /**
     * 时间戳 to 正常时间(支持格式FORMAT1/FORMAT2/FORMAT3,FORMAT4格式转化出来会多了8个小时)
     * @param timestamp
     */
    public static String stampToNormal(long timestamp,Type type) {
        if (timestamp < 0)
            try {
                throw new Exception("timestamp must be larger than 0");
            } catch (Exception e) {
                e.printStackTrace();
            }
        String date = getSimpleDateFormat().format(new Date(timestamp * 1000));
        if (type == Type.FORMAT2 || type == Type.FORMAT1)
            return date;
        else if (type == Type.FORMAT3) {
            return date.split(" ")[0];
        } else if (type == Type.FORMAT4) {
            return date.split(" ")[1];
        }
        return date;
    }

    /**
     * 支持FORMAT4格式的转化
     * @param timestamp
     * @return
     */
    public static String stampToNormalStandard(long timestamp) {
        if (timestamp < 0)
            try {
                throw new Exception("timestamp must be larger than 0");
            } catch (Exception e) {
                e.printStackTrace();
            }
        String date = getSimpleDateFormatStandard().format(new Date(timestamp * 1000));
        return date.split(" ")[1];
    }

    public static enum Type{
        FORMAT1,
        FORMAT2,
        FORMAT3,
        FORMAT4
    }
}
