package com.xs.basic_mvvm.ui.callback;

import android.support.annotation.StringRes;

/**
 * @version V1.0 <提示VIEW 接口（工具）>
 * @author: Xs
 * @date: 2016-08-17 14:34
 * @email Xs.lin@foxmail.com
 */
public interface ICallBck {

    /**
     * 提示消息
     * @param str
     */
    void showToast(String str);

    /**
     * 提示消息
     * @param resId
     */
    void showToast(@StringRes int resId);

    /**
     * 显示加载框
     * @param resId
     */
    void showLoadingView(@StringRes int resId);

    /**
     * 显示加载框
     */
    void showLoadingView();

    /**
     * 隐藏加载框
     */
    void dismissLoadingView();

    /**
     * 显示提示框
     * @param message
     */
    void showTipDialog(String message);


}
