package com.xs.basic_mvvm.ui.callback;

/**
 * @version V1.0 <生命周期接口>
 * @author: Xs
 * @date: 2016-08-17 14:49
 * @email Xs.lin@foxmail.com
 */
public interface ILifeCycle {

    /**
     * 当界面可见时回调
     */
    void onStart();

    /**
     * 当界面不可见时回调
     */
    void onStop();

    /**
     * 界面销毁时回调
     */
    void onDestroy();

}
