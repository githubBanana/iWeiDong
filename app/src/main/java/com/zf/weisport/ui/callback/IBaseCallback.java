package com.zf.weisport.ui.callback;

import com.xs.basic_mvvm.ui.callback.ICallBck;

/**
 * @version V1.0 <根据实际项目业务需求扩展接口>
 * @author: Xs
 * @date: 2016-08-29 11:57
 * @email Xs.lin@foxmail.com
 */
public interface IBaseCallback extends ICallBck {


    /**
     * 网络数据加载完成回调
     */
    void onDataLoadSuccess();

    /**
     * 提交完成的回调
     * @param object
     */
    void onSubmitCompleted(Object object);

}
