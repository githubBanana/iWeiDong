package com.zf.weisport.ui.callback;

import com.zf.weisport.model.StatisticsModel;

import java.util.List;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-09-14 14:21
 * @email Xs.lin@foxmail.com
 */
public interface IHistoryRecordCallback extends IBaseCallback {

    /**
     * 获取历史转速记录数据成功
     * @param statisticsModel
     */
    void onGetHistoryRecordDataSuccess(List<StatisticsModel> statisticsModel);
}
