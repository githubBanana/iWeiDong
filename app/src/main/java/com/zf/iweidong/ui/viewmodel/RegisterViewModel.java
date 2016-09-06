package com.zf.iweidong.ui.viewmodel;

import com.xs.basic_mvvm.presenter.BaseBiz;
import com.zf.iweidong.model.RegisterModel;
import com.zf.iweidong.presenter.IRegisterView;
import com.zf.iweidong.presenter.biz.IRegisterBiz;
import com.zf.iweidong.presenter.biz.impl.RegisterBizImpl;
import com.zf.iweidong.ui.callback.IRegisterCallback;

/**
 * @version V1.0 <描述当前版本功能>
 * @author: Xs
 * @date: 2016-09-06 16:23
 * @email Xs.lin@foxmail.com
 */
public class RegisterViewModel extends BaseViewModel<IRegisterCallback,RegisterModel> implements IRegisterView {

    public String mobileNumber;
    public String passWord;
    public String codes;
    public String codesCountDown;

    public IRegisterBiz biz;

    public RegisterViewModel(IRegisterCallback iRegisterCallback) {
        super(iRegisterCallback);
        biz = getBiz();
        biz.loadInitData();
    }

    /**
     * 注册
     */
    public void register() {
        biz.register();
    }

    /**
     * 获取验证码
     */
    public void getVerifyCodes() {
        biz.getVerifyCodes();
    }

    @Override
    protected BaseBiz createBiz() {
        return new RegisterBizImpl(this,this);
    }

    @Override
    public String getMobileNumber() {
        return this.mobileNumber;
    }

    @Override
    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    @Override
    public String getPassword() {
        return this.passWord;
    }

    @Override
    public void setPassword(String password) {
        this.passWord = password;
    }

    @Override
    public String getCodes() {
        return this.codes;
    }

    @Override
    public void setCodes(String codes) {
        this.codes = codes;
    }

    @Override
    public String getCodesCountDown() {
        return codesCountDown;
    }

    @Override
    public void setCodesCountDown(String codesCountDown) {
        this.codesCountDown = codesCountDown;
    }

    @Override
    public void onRegisterCompleted(RegisterModel registerModel) {
        getCallback().onRegisterSuccess(registerModel);
    }

    @Override
    public void onGetVerifyCodesStaus(boolean isSuccess) {
        getCallback().onGetVerifyCodesStaus(isSuccess);
    }


}
