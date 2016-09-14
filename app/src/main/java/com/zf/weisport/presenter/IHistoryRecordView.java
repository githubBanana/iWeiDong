package com.zf.weisport.presenter;

import com.xs.basic_mvvm.presenter.IBaseView;
import com.zf.weisport.model.StatisticsModel;

import java.util.List;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-09-14 14:40
 * @email Xs.lin@foxmail.com
 */
public interface IHistoryRecordView extends IBaseView {

    /**
     * 获取历史转速数据完成回调
     * @param statisticsModel
     */
    void onGetHistoryRecordDataCompleted(List<StatisticsModel> statisticsModel);

    /**
     * 设置设备类型
     * @param deviceType
     */
    void setDeviceType(String deviceType);

    /**
     * 获取设备类型
     * @return
     */
    String getDeviceType();

    /**
     * 设置时间类型
     * @param type
     */
    void setType(String type);

    /**
     * 获取时间类型
     * @return
     */
    String getType();
    /**
     * 设置累计时长
     * @param accumulateHours
     */
    void setAccumulateHours(String accumulateHours);

    /**
     * 获取累计时长
     * @return
     */
    String getAccumulateHours();

    /**
     * 设置最大转速
     * @param maxSpeed
     */
    void setMaxSpeed(String maxSpeed);

    /**
     * 获取最大转速
     * @return
     */
    String getMaxSpeed();

    /**
     * 设置开始时间
     * @param startTime
     */
    void setStartTime(String startTime);

    /**
     * 获取开始时间
     * @return
     */
    String getStartTime();

    /**
     * 设置结束时间
     * @param endTime
     */
    void setEndTime(String endTime);

    /**
     * 获取结束时间
     * @return
     */
    String getEndTime();
    void setWeekModels(List<StatisticsModel> statisticsModels);

    List<StatisticsModel> getWeekModels();

    void setMonthModels(List<StatisticsModel> statisticsModels);

    List<StatisticsModel> getMonthModels();

    void setYearModes(List<StatisticsModel> statisticsModels);

    List<StatisticsModel> getYearModels();
}
