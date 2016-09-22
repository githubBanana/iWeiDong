package com.zf.weisport.ui.callback;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-09-22 16:48
 * @email Xs.lin@foxmail.com
 */
public interface IBattleCallback extends IBaseCallback{

    /**
     * 获取我的排名成功
     * @param
     */
    void onGetMyRankSuccess(String levelString, int speedLevel);
}
