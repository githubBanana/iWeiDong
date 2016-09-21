package com.zf.weisport.ui.callback;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-09-21 10:47
 * @email Xs.lin@foxmail.com
 */
public interface IBindPhoneCallback extends IBaseCallback {

    /**
     * 提交绑定手机号信息成功
     */
    void onCommitSuccess();

    /**
     * 获取验证码结果状态回调
     */
    void onGetVerifyCodesStaus(boolean isSuccess);

}
