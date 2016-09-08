package com.zf.weisport.manager.db.bean;

import java.io.Serializable;
import java.util.HashMap;

/**
 * @version V1.0 <UpGameBean 不做本地保存>
 * @author: Xs
 * @date: 2016-05-05 11:31
 * @email Xs.lin@foxmail.com
 */
public class UpGameBean implements Serializable {

    private final String USER_ID = "User_ID";
    private final String DEVICE_ID = "Device_ID";
    private final String CALORIE = "Calorie";
    private final String DEVICE_TYPE = "Device_Type";
    private final String START_TIME = "Start_Time";
    private final String LONG_TIME = "Long_Time";
    private final String SPEED = "Speed";

    private String User_ID;         //用户ID
    private String Device_ID;       //设备ID
    private String Calorie;         //消耗卡路里
    private String Device_Type;     //设备类型
    private String Start_Time;      //开始时间(时间戳格式)
    private String Long_Time;       //时长
    private String Speed;           //速度

    public UpGameBean(){}
    public UpGameBean(String User_ID,String Device_ID,String Calorie,String Device_Type,String Start_Time,String Long_Time,String Speed){
        this.User_ID = User_ID;
        this.Device_ID = Device_ID;
        this.Calorie = Calorie;
        this.Device_Type = Device_Type;
        this.Start_Time = Start_Time;
        this.Long_Time = Long_Time;
        this.Speed = Speed;
    }

    public String getUser_ID() {
        return User_ID;
    }

    public void setUser_ID(String user_ID) {
        this.User_ID = user_ID;
    }

    public String getDevice_ID() {
        return Device_ID;
    }

    public void setDevice_ID(String device_ID) {
        Device_ID = device_ID;
    }

    public String getCalorie() {
        return Calorie;
    }

    public void setCalorie(String calorie) {
        Calorie = calorie;
    }

    public String getDevice_Type() {
        return Device_Type;
    }

    public void setDevice_Type(String device_Type) {
        Device_Type = device_Type;
    }

    public String getStart_Time() {
        return Start_Time;
    }

    public void setStart_Time(String start_Time) {
        Start_Time = start_Time;
    }

    public String getLong_Time() {
        return Long_Time;
    }

    public void setLong_Time(String long_Time) {
        Long_Time = long_Time;
    }

    public String getSpeed() {
        return Speed;
    }

    public void setSpeed(String speed) {
        Speed = speed;
    }

    public HashMap<String,Object> toMap() {
        HashMap<String,Object> map = new HashMap<>();
        map.put(USER_ID,this.User_ID);
        map.put(DEVICE_ID,this.Device_ID);
        map.put(CALORIE,this.Calorie);
        map.put(DEVICE_TYPE,this.Device_Type);
        map.put(START_TIME, this.Start_Time);
        map.put(LONG_TIME, this.Long_Time);
        map.put(SPEED, this.Speed);
        return map;
    }
    @Override
    public String toString() {
        return "UpGameBean{" +
                "User_ID='" + User_ID + '\'' +
                ", Device_ID='" + Device_ID + '\'' +
                ", Calorie='" + Calorie + '\'' +
                ", Device_Type='" + Device_Type + '\'' +
                ", Start_Time='" + Start_Time + '\'' +
                ", Long_Time='" + Long_Time + '\'' +
                ", Speed='" + Speed + '\'' +
                '}';
    }
}
