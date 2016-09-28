package com.zf.weisport.presenter.biz;

import com.xs.basic_mvvm.presenter.IBaseBiz;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-09-22 16:55
 * @email Xs.lin@foxmail.com
 */
public interface IBattleBiz extends IBaseBiz{

    /**
     * 获取我的排名
     */
    void getMyRank();

    /**
     * 绑定设备
     * @param address
     * @param deviceName
     */
    void bindDevice(String address,String deviceName);

    /**
     * 上传 下位机历史记录
     */
    void upGameArr();

    /**
     * 游戏记录上传
     * @param userId
     * @param Calorie
     * @param Start_Time
     * @param Long_Time
     * @param Speed
     */
    void upGame(String userId,String Calorie,String Start_Time,String Long_Time,String Speed);
}
