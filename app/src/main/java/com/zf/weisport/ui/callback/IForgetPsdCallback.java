package com.zf.weisport.ui.callback;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-09-07 10:52
 * @email Xs.lin@foxmail.com
 */
public interface IForgetPsdCallback extends IBaseCallback{

    /**
     * 获取验证码结果状态回调
     */
    void onGetVerifyCodesStaus(boolean isSuccess);

}

