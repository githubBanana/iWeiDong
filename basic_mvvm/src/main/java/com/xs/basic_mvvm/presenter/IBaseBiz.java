package com.xs.basic_mvvm.presenter;

import com.xs.basic_mvvm.ui.callback.ILifeCycle;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-08-17 16:08
 * @email Xs.lin@foxmail.com
 */
public interface IBaseBiz extends ILifeCycle {


    /**
     * 初始化数据业务逻辑方法
     */
      void loadInitData();
    /**
     * 加载数据,不要直接调用次方法
     */
//    void loadData();

    /**
     * 提交数据,不要直接调用次方法
     */
//    void submit();


}
