package com.zf.weisport.presenter;

import com.xs.basic_mvvm.presenter.IBaseView;
import com.zf.weisport.model.MyRankModel;
import com.zf.weisport.model.UpGameModel;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-09-22 16:50
 * @email Xs.lin@foxmail.com
 */
public interface IBattleView extends IBaseView{

    /**
     * 获取我的排名完成
     * @param myRankModel
     */
    void onGetMyRankCompleted(MyRankModel myRankModel);

    /**
     * 上传 下位机历史数据完成
     */
    void onUpGameArrCompleted();

    /**
     * 上传 游戏记录完成
     * @param upGameModel
     */
    void onUpGameCompleted(UpGameModel upGameModel);

    /**
     * 设置设备地址
     * @param deviceAddress
     */
    void setDeviceAddress(String deviceAddress);

    /**
     * 获取设备地址
     * @return
     */
    String getDeviceAddress();

    /**
     * 设置设备名称
     * @param deviceName
     */
    void setDeviceName(String deviceName);

    /**
     * 获取设备名称
     * @return
     */
    String getDeviceName();

    /**
     * 设置设备ID
     * @param deviceId
     */
    void setDeviceId(String deviceId);

    /**
     * 获取设备ID
     * @return
     */
    String getDeviceId();

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

}
