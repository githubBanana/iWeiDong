package com.zf.iweidong.ui.callback;

import com.zf.iweidong.model.RegisterModel;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-09-06 16:24
 * @email Xs.lin@foxmail.com
 */
public interface IRegisterCallback extends IBaseCallback {

    /**
     * 注册成功回调
     * @param registerModel
     */
    void onRegisterSuccess(RegisterModel registerModel);

    /**
     * 获取验证码结果状态回调
     */
    void onGetVerifyCodesStaus(boolean isSuccess);
}
