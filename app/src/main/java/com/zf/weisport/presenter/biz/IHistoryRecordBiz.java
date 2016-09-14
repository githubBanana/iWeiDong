package com.zf.weisport.presenter.biz;

import com.xs.basic_mvvm.presenter.IBaseBiz;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-09-14 14:58
 * @email Xs.lin@foxmail.com
 */
public interface IHistoryRecordBiz extends IBaseBiz{

    /**
     * 获取历史转速数据
     */
    void getHistoryRecordData();
}
