package com.zf.weisport.presenter;

import com.xs.basic_mvvm.presenter.IBaseView;

import java.util.HashMap;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-08-29 14:00
 * @email Xs.lin@foxmail.com
 */
public interface IBaseSuperView<DataType> extends IBaseView {

    /**
     * 数据加载回调
     *
     * @param dataType
     */
    void onDataLoad(DataType dataType);

    /**
     * 提交完成
     */
    void onSubmitCompleted();
    /**
     * 获取提交数据
     * @return
     */
    HashMap<String, Object> getSubmitData();

}
