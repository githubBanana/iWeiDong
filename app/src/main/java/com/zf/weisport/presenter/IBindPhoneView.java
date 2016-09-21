package com.zf.weisport.presenter;

import com.xs.basic_mvvm.presenter.IBaseView;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-09-21 10:49
 * @email Xs.lin@foxmail.com
 */
public interface IBindPhoneView extends IBaseView {

    /**
     * 提交绑定手机号信息完成
     */
    void onCommitCompleted();


    /**
     * 获取验证码结果状态回调
     */
    void onGetVerifyCodesStaus(boolean isSuccess);

    /**
     * 设置手机号
     * @param phone
     */
    void setPhone(String phone);

    /**
     * 获取手机号
     * @return
     */
    String getPhone();

    /**
     * 设置验证码
     * @param smsCode
     */
    void setSMSCode(String smsCode);

    /**
     * 获取验证码
     * @return
     */
    String getSMSCode();

    /**
     * 设置密码
     * @param password
     */
    void setPassword(String password);

    /**
     * 获取密码
     * @return
     */
    String getPassword();
}
