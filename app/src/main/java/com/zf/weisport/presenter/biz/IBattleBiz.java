package com.zf.weisport.presenter.biz;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-09-22 16:55
 * @email Xs.lin@foxmail.com
 */
public interface IBattleBiz {

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
}
