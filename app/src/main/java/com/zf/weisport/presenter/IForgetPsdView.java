package com.zf.weisport.presenter;

import com.xs.basic_mvvm.presenter.IBaseView;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-09-07 11:00
 * @email Xs.lin@foxmail.com
 */
public interface IForgetPsdView extends IBaseView {

    /**
     * 获取手机号
     * @return
     */
    String getMobileNumber();

    /**
     * 设置手机号
     * @param mobileNumber
     */
    void setMobileNumber(String mobileNumber);

    /**
     * 获取验证码
     * @return
     */
    String getCodes();

    /**
     * 设置验证码
     * @param codes
     */
    void setCodes(String codes);

    /**
     * 获取验证码倒计时
     * @return
     */
    String getCodesCountDown();

    /**
     * 设置验证码倒计时
     * @param codesCountDown
     */
    void setCodesCountDown(String codesCountDown);

    /**
     * 获取验证码结果状态回调
     */
    void onGetVerifyCodesStaus(boolean isSuccess);
}
