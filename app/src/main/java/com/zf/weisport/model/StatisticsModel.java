package com.zf.weisport.model;

import com.xs.basic_mvvm.model.BaseModel;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-04-14 17:09
 * @email Xs.lin@foxmail.com
 */
public class StatisticsModel extends BaseModel<StatisticsModel> {

    public String Time;
    public String Speed;
    public String Duration;

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getSpeed() {
        return Speed;
    }

    @Override
    public String toString() {
        return "StatisticsModel{" +
                "Time='" + Time + '\'' +
                ", Speed='" + Speed + '\'' +
                ", Duration='" + Duration + '\'' +
                '}';
    }

    public void setSpeed(String speed) {
        Speed = speed;
    }

    public String getDuration() {
        return Duration;
    }

    public void setDuration(String duration) {
        Duration = duration;
    }
}
