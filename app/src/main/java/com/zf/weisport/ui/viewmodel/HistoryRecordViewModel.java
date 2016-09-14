package com.zf.weisport.ui.viewmodel;

import android.support.annotation.IntRange;

import com.xs.basic_mvvm.presenter.BaseBiz;
import com.zf.weisport.model.StatisticsModel;
import com.zf.weisport.presenter.IHistoryRecordView;
import com.zf.weisport.presenter.biz.IHistoryRecordBiz;
import com.zf.weisport.presenter.biz.impl.HistoryRecordImpl;
import com.zf.weisport.ui.callback.IHistoryRecordCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-09-14 14:20
 * @email Xs.lin@foxmail.com
 */
public class HistoryRecordViewModel extends BaseViewModel<IHistoryRecordCallback,StatisticsModel> implements IHistoryRecordView {

    public String deviceType;
    public String type;
    public String accumulateHours;
    public String maxSpeed;
    public String startTime;
    public String endTime;

    private List<StatisticsModel>   _weekModels ;
    private List<StatisticsModel>   _monthModels ;
    private List<StatisticsModel>   _yearModels ;

    private IHistoryRecordBiz biz;

    public HistoryRecordViewModel(IHistoryRecordCallback iHistoryRecordCallback) {
        super(iHistoryRecordCallback);
        _weekModels = new ArrayList<>();
        _monthModels = new ArrayList<>();
        _yearModels = new ArrayList<>();
        biz = getBiz();
        biz.loadInitData();
    }

    @Override
    protected BaseBiz createBiz() {
        return new HistoryRecordImpl(this,this);
    }

    /**
     * 获取历史转速数据
     */
    public void getHistoryRecordData() {
        biz.getHistoryRecordData();
    }


    @Override
    public void onGetHistoryRecordDataCompleted(List<StatisticsModel> statisticsModel) {
        getCallback().onGetHistoryRecordDataSuccess(statisticsModel);
    }

    @Override
    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    @Override
    public String getDeviceType() {
        return this.deviceType;
    }

    @Override
    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String getType() {
        return this.type;
    }

    @Override
    public void setAccumulateHours(String accumulateHours) {
        this.accumulateHours = accumulateHours;
    }

    @Override
    public String getAccumulateHours() {
        return this.accumulateHours;
    }

    @Override
    public void setMaxSpeed(String maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    @Override
    public String getMaxSpeed() {
        return this.maxSpeed;
    }

    @Override
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    @Override
    public String getStartTime() {
        return this.startTime;
    }

    @Override
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @Override
    public String getEndTime() {
        return this.endTime;
    }

    @Override
    public void setWeekModels(List<StatisticsModel> statisticsModels) {
        this._weekModels = statisticsModels;
    }

    @Override
    public List<StatisticsModel> getWeekModels() {
        return _weekModels;
    }

    @Override
    public void setMonthModels(List<StatisticsModel> statisticsModels) {
        this._monthModels = statisticsModels;
    }

    @Override
    public List<StatisticsModel> getMonthModels() {
        return this._monthModels;
    }

    @Override
    public void setYearModes(List<StatisticsModel> statisticsModels) {
        this._yearModels = statisticsModels;
    }

    @Override
    public List<StatisticsModel> getYearModels() {
        return this._yearModels;
    }

    /**
     * 根据时间类型 判断对应集合是否为空 （空则需要从网络获取，反之，直接初始化view）
     * @param type
     * @return
     */
    public boolean isNeedToAccessNet(@IntRange(from = 1,to = 3)int type) {
        boolean isNeed = true;
        switch (type) {
            case 1:
                if (getWeekModels().size() > 0)
                    isNeed = false;
                break;
            case 2:
                if (getMonthModels().size() > 0)
                    isNeed = false;
                break;
            case 3:
                if (getYearModels().size() > 0)
                    isNeed = false;
                break;
        }
        return isNeed;
    }

    /**
     * 获取当前的集合
     * @param type
     * @return
     */
    public List<StatisticsModel> getCurrentList(@IntRange(from = 1,to = 3) int type) {
        List<StatisticsModel> list = null;
        switch (type) {
            case 1:
                list = getWeekModels();
                break;
            case 2:
                list = getMonthModels();
                break;
            case 3:
                list = getYearModels();
                break;
        }
        return list;
    }

    /**
     * 切换设备类型时  清除集合
     */
    public void clearModelList() {
        _weekModels.clear();
        _monthModels.clear();
        _yearModels.clear();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        _weekModels = null;
        _monthModels = null;
        _yearModels = null;
    }
}
