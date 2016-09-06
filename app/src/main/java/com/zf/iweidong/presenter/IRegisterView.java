package com.zf.iweidong.presenter;

import com.xs.basic_mvvm.presenter.IBaseView;
import com.zf.iweidong.model.RegisterModel;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-09-06 16:33
 * @email Xs.lin@foxmail.com
 */
public interface IRegisterView extends IBaseView {

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
     * 获取密码
     * @return
     */
    String getPassword();

    /**
     * 设置密码
     * @param pssword
     */
    void setPassword(String pssword);

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
     * 注册完成回调
     * @param registerModel
     */
    void onRegisterCompleted(RegisterModel registerModel);

    /**
     * 获取验证码结果状态回调
     */
    void onGetVerifyCodesStaus(boolean isSuccess);
}
