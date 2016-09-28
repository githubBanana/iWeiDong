package com.zf.weisport.manager.other;

import com.zf.weisport.manager.util.TimeUtil;
import com.zf.weisport.manager.util.UnixTimeStamp;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-04-20 11:43
 * @email Xs.lin@foxmail.com
 */
public class GameTimeCount {

    private Timer timer ;
    private TimerTask task;
    private TimeCountCallBack mTimeCount;
    private boolean isCountting = false;
    private int count = 0;

    public GameTimeCount( ) {}

    public void start() {
        if (!isCountting) {
            mTimeCount.OnGameStart(TimeUtil.getCurrTime_Hour_Minute_Second_String(),String.valueOf(TimeUtil.getCurrTime()));
            count = 0;
            timer = new Timer();
            task = new TimerTask() {
                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    count++;
                    mTimeCount.OnGameRun(UnixTimeStamp.stampToNormal(count),count);
                }
            };
            timer.schedule(task, 0, 1000);
            isCountting = true;
        }
    }
    public void stop(boolean IsNormal) {
        if (isCountting) {
            timer.cancel();
            task.cancel();
            isCountting = false;
            mTimeCount.OnGameOver(IsNormal,TimeUtil.getCurrTime_Hour_Minute_Second_String(),String.valueOf(count));
        }
    }

    public void setTimeCountListen(TimeCountCallBack timeCountCallBack) {
        mTimeCount = timeCountCallBack;
    }
    public interface TimeCountCallBack{
        void OnGameRun(String run, int second);
        void OnGameOver(boolean IsNormal, String end_string, String end_second);
        void OnGameStart(String startTime, String startTimeStamp);
    }
}
